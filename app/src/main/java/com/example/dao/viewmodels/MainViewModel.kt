package com.example.dao.viewmodels

import KFUDAOGovernor
import KFUDAOGovernor.PROPOSALCREATED_EVENT
import KFUDAOToken
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dao.BuildConfig
import com.example.dao.models.Account
import com.example.dao.models.Proposal
import com.example.dao.models.Token
import com.example.dao.models.VoteDecision
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import io.metamask.androidsdk.EthereumFlow
import io.metamask.androidsdk.EthereumMethod
import io.metamask.androidsdk.EthereumRequest
import io.metamask.androidsdk.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import org.web3j.abi.EventEncoder
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.DefaultBlockParameter
import org.web3j.protocol.core.methods.request.EthFilter
import org.web3j.protocol.core.methods.response.EthBlockNumber
import org.web3j.protocol.http.HttpService
import org.web3j.tx.ClientTransactionManager
import org.web3j.tx.gas.DefaultGasProvider
import java.math.BigInteger
import java.sql.Timestamp
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val ethereum: EthereumFlow) : ViewModel() {
    private val TAG: String = "MainViewModel"
    private val _delay: Long = 1000

    private val web3 = Web3j.build(HttpService(BuildConfig.RPC_URL))
    private var txManager = ClientTransactionManager(web3, "")
    private val gasProvider = DefaultGasProvider()
    private val _currentBlock = MutableStateFlow<EthBlockNumber?>(null)
    val currentBlock: StateFlow<EthBlockNumber?> = _currentBlock

    var connected: Boolean by mutableStateOf(false)
    var address: String? by mutableStateOf(null)

    private val governorAddress = BuildConfig.GOVERNOR_CONTRACT_ADDRESS
    private val governorContract =
        KFUDAOGovernor.load(governorAddress, web3, txManager, gasProvider)
    private val _proposals = MutableStateFlow<List<Proposal>>(emptyList())
    val proposals: StateFlow<List<Proposal>> = _proposals

    private val tokenAddress = BuildConfig.TOKEN_CONTRACT_ADDRESS
    private val tokenContract = KFUDAOToken.load(tokenAddress, web3, txManager, gasProvider)
    private val symbol = tokenContract.symbol().sendAsync().get()
    private val _account: MutableStateFlow<Account?> = MutableStateFlow(null)
    val account: StateFlow<Account?> = _account

    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    private val _institutes = MutableStateFlow<HashMap<String, String>>(hashMapOf())
    val institutes: StateFlow<HashMap<String, String>> = _institutes

    private val _faculties = MutableStateFlow<List<String>>(emptyList())
    val faculties: StateFlow<List<String>> = _faculties

    init {
        startFetchingPeriodically()
        startUpdatingCurrentBlock()
        fetchInstitutes()
    }

    fun updateAccountAddress(): StateFlow<Account?> {
        _account.value!!.address = address
        return account
    }

    suspend fun connect(): Result {
        val result = ethereum.connect()
        if (result is Result.Success) {
            connected = true
            address = ethereum.selectedAddress
        }
        return result
    }

    fun disconnect(clearSession: Boolean = false) {
        connected = false
        ethereum.disconnect(clearSession)
    }

    private fun fetchAllProposals() {
        val filter = EthFilter(
            DefaultBlockParameter.valueOf(BigInteger.ZERO),
            DefaultBlockParameter.valueOf("latest"),
            listOf(governorContract.contractAddress)
        )
        filter.addSingleTopic(EventEncoder.encode(PROPOSALCREATED_EVENT))
        val proposalFlowable = governorContract.proposalCreatedEventFlowable(filter)
        viewModelScope.launch(Dispatchers.IO) {
            proposalFlowable.subscribe(
                { event ->
                    val voteStartBlock = web3.ethGetBlockByNumber(
                        DefaultBlockParameter.valueOf(event.voteStart),
                        false
                    ).sendAsync().get()
                    val voteEndBlock = web3.ethGetBlockByNumber(
                        DefaultBlockParameter.valueOf(event.voteEnd),
                        false
                    ).sendAsync().get()
                    val voteStartBlockTimestamp = voteStartBlock.block.timestamp
                    val voteEndBlockTimestamp = voteEndBlock.block?.timestamp
                        ?: (voteStartBlockTimestamp + (event.voteEnd - voteStartBlock.block.number) * BigInteger(
                            BuildConfig.BLOCK_TIME
                        ))
                    val votes = governorContract.proposalVotes(event.proposalId).sendAsync().get()
                    val votesFor = Token(votes.component1(), symbol)
                    val votesAgainst = Token(votes.component2(), symbol)
                    val votesAbstain = Token(votes.component3(), symbol)
                    val proposal = Proposal(
                        id = event.proposalId, proposer = event.proposer,
                        description = event.description,
                        voteStartBlock = event.voteStart, voteEndBlock = event.voteEnd,
                        voteStartBlockTimestamp = Timestamp(voteStartBlockTimestamp.toLong() * 1000),
                        voteEndBlockTimestamp = Timestamp(voteEndBlockTimestamp.toLong() * 1000),
                        votesFor = votesFor,
                        votesAgainst = votesAgainst,
                        votesAbstain = votesAbstain
                    )
                    val currentProposals = _proposals.value.toMutableList()
                    val index = currentProposals.indexOfFirst { it.id == proposal.id }
                    if (index >= 0) {
                        currentProposals[index] = proposal
                    } else {
                        currentProposals.add(proposal)
                    }
                    _proposals.value = currentProposals
                },
                { error ->
                    Log.e(TAG, "Error fetching proposals", error)
                }
            )
        }
    }

    private fun startFetchingPeriodically() {
        viewModelScope.launch(Dispatchers.IO) {
            while (true) {
                Log.d(TAG, "Fetching proposals")
                fetchAllProposals()
                delay(_delay * 30)
            }
        }
    }

    private fun startUpdatingCurrentBlock() {
        viewModelScope.launch(Dispatchers.IO) {
            while (true) {
                try {
                    val blockNumber = web3.ethBlockNumber().sendAsync().get()
                    _currentBlock.value = blockNumber
                    Log.d(TAG, "Current block updated: ${blockNumber.blockNumber}")
                } catch (e: Exception) {
                    Log.e(TAG, "Error fetching current block", e)
                }
                delay(_delay * BuildConfig.BLOCK_TIME.toInt())
            }
        }
    }

    suspend fun createProposal(description: String): Result {
        val proposalCreationData = governorContract.propose(
            listOf(ethereum.selectedAddress),
            listOf(BigInteger.ZERO),
            listOf(ByteArray(1)),
            description
        ).encodeFunctionCall()
        Log.d(TAG, proposalCreationData)
        val result = sendDataToGovernor(proposalCreationData)
        if (result is Result.Success.Item) {
            Log.d(TAG, result.value)
        }
        return result
    }

    suspend fun castVote(proposalId: BigInteger, decision: VoteDecision): Result {
        val castVoteData = withContext(Dispatchers.IO) {
            governorContract.castVote(proposalId, BigInteger.valueOf(decision.value.toLong()))
                .encodeFunctionCall()
        }
        Log.d(TAG, castVoteData)
        return sendDataToGovernor(castVoteData)
    }

    private suspend fun sendDataToGovernor(data: String): Result {
        val params: Map<String, Any> = mutableMapOf(
            "from" to ethereum.selectedAddress,
            "to" to governorContract.contractAddress,
            "data" to data
        )
        val transactionRequest = EthereumRequest(
            method = EthereumMethod.ETH_SEND_TRANSACTION.value,
            params = listOf(params)
        )
        return ethereum.sendRequest(transactionRequest)
    }

    suspend fun hasVoted(proposalId: BigInteger): Boolean = withContext(Dispatchers.IO) {
        governorContract.hasVoted(proposalId, ethereum.selectedAddress).sendAsync().get()
    }

    private fun fetchInstitutes() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val institutes = getInstitutes()
                _institutes.value = institutes
            } catch (e: Exception) {
                Log.e(TAG, "Error fetching institutes", e)
            }
        }
    }

    private suspend fun getInstitutes() = withContext(Dispatchers.IO) {
        val institutes: HashMap<String, String> = hashMapOf()
        val snapshot = firestore.collection("institutes").get().await()
        for (document in snapshot.documents) {
            institutes[document.id] = document.getString("name") ?: ""
        }
        institutes
    }

    fun getFaculties(instituteAbbreviation: String): List<String> {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val faculties = fetchFaculties(instituteAbbreviation)
                _faculties.value = faculties
            } catch (e: Exception) {
                Log.e(TAG, "Error loading faculties", e)
            }
        }
        return _faculties.value
    }

    private suspend fun fetchFaculties(instituteAbbreviation: String): List<String> =
        withContext(Dispatchers.IO) {
            val faculties: MutableList<String> = mutableListOf()
            val snapshot =
                firestore.collection("institutes").document(instituteAbbreviation).get().await()
            val facultyList = snapshot.get("faculties") as? List<String>
            facultyList?.let {
                faculties.addAll(it)
            }
            faculties
        }


    fun saveUserDataToFirestore(account: Account, onError: (Exception?) -> Unit) {
        val usersCollection = firestore.collection("users")
        usersCollection.document(account.email).set(
            hashMapOf(
                "email" to account.email,
                "institute" to account.institute,
                "instituteAbbreviation" to account.instituteAbbreviation,
                "faculty" to account.faculty,
                "address" to account.address
            )
        ).addOnSuccessListener {
            Log.d("saveUserDataToFirestore", "User data saved successfully.")
        }.addOnFailureListener { e ->
            onError(e)
        }
    }

    fun registerNewUser(
        account: Account,
        password: String,
        onError: (Exception?) -> Unit,
        onRegister: (String) -> Unit
    ) {
        if (account.email.isBlank() || password.isBlank()) {
            onError(Exception("Почта или пароль не могут быть пустыми"))
        }
        auth.createUserWithEmailAndPassword(account.email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    saveUserDataToFirestore(account) { onError(it) }
                    onRegister(account.email)
                } else {
                    onError(task.exception)
                }
            }
            .addOnFailureListener { e ->
                onError(e)
            }
    }

    fun signIn(email: String, password: String, onLogin: (String) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onLogin(auth.currentUser?.email ?: "")
                } else {
                    Log.e("LoginScreen", "Login failed ${task.exception}")
                }
            }
            .addOnFailureListener { e -> Log.e("LoginScreen", "Login failed ${e.message ?: ""}") }
    }

    fun updateLocalAccount(email: String, address: String?) {
        Log.d("updateAccount", "Fetching document for email: $email")
        firestore.collection("users").document(email).get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    Log.d("updateAccount", "Document data: ${document.data}")
                    val account = Account(
                        email = email,
                        institute = document.getString("institute") ?: "",
                        instituteAbbreviation = document.getString("instituteAbbreviation") ?: "",
                        faculty = document.getString("faculty") ?: "",
                        address = document.getString("address") ?: address
                    )
                    _account.value = account
                } else {
                    Log.e("updateAccount", "Document does not exist")
                }
            }
            .addOnFailureListener { e ->
                Log.e("updateAccount", "Error fetching document: ${e.message}")
            }
            .addOnCanceledListener {
                Log.d("updateAccount", "Fetch canceled")
            }
    }

}
