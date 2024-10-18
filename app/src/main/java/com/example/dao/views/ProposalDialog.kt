package com.example.dao.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.dao.R
import com.example.dao.models.Account
import com.example.dao.models.Proposal
import com.example.dao.models.Token
import com.example.dao.models.VoteDecision
import com.example.dao.viewmodels.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.math.BigInteger
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun ProposalDialog(
    proposal: Proposal,
    mvm: MainViewModel,
    hasVoted: Boolean,
    onDismiss: () -> Unit
) {
    val account by mvm.account.collectAsState()
    ProposalDialogContent(
        proposal = proposal,
        account = account,
        currentAddress = mvm.address,
        isConnected = mvm.connected,
        onDismiss = onDismiss,
        hasVoted = hasVoted
    ) { voteDecision ->
        CoroutineScope(Dispatchers.IO).launch {
            voteDecision.let {
                mvm.castVote(proposal.id ?: BigInteger.ZERO, voteDecision)
            }
        }
        onDismiss()
    }
}

@Composable
fun ProposalDialogContent(
    proposal: Proposal,
    account: Account?,
    currentAddress: String?,
    isConnected: Boolean,
    hasVoted: Boolean,
    onDismiss: () -> Unit,
    onCastVote: (VoteDecision) -> Unit
) {
    var selectedVote by remember { mutableStateOf<VoteDecision?>(null) }
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = stringResource(id = R.string.proposal_details)) },
        text = {
            Column {
                ProposalDetail(
                    stringResource(R.string.proposer),
                    proposal.proposer ?: stringResource(R.string.not_available)
                )
                ProposalDetail(
                    stringResource(R.string.description),
                    proposal.description ?: stringResource(R.string.not_available)
                )
                ProposalDetail(
                    stringResource(R.string.start_date),
                    proposal.voteStartBlockTimestamp?.let { formatDateTime(it) }
                        ?: stringResource(R.string.not_available)
                )
                ProposalDetail(
                    stringResource(R.string.end_date),
                    proposal.voteEndBlockTimestamp?.let { formatDateTime(it) }
                        ?: stringResource(R.string.not_available)
                )
                ProposalDetail(
                    stringResource(R.string.for_),
                    proposal.votesFor?.toString() ?: stringResource(R.string.not_available)
                )
                ProposalDetail(
                    stringResource(R.string.against),
                    proposal.votesAgainst?.toString() ?: stringResource(R.string.not_available)
                )
                ProposalDetail(
                    stringResource(R.string.abstain),
                    proposal.votesAbstain?.toString() ?: stringResource(R.string.not_available)
                )
                if (isConnected && account?.address == currentAddress && !hasVoted && proposal.voteEndBlockTimestamp!!.time > System.currentTimeMillis()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        for (vote in VoteDecision.entries) {
                            VoteButton(text = vote.toString(), isSelected = selectedVote == vote) {
                                selectedVote = vote
                            }
                        }
                    }
                }
            }
        },
        confirmButton = {
            if (isConnected && account?.address == currentAddress && !hasVoted && proposal.voteEndBlockTimestamp!!.time > System.currentTimeMillis()) {
                Button(
                    onClick = { onCastVote(selectedVote!!) }
                ) { Text(stringResource(id = R.string.cast_vote)) }
            }
        },
        dismissButton = { Button(onClick = onDismiss) { Text(stringResource(R.string.cancel)) } }
    )
}

@Composable
private fun ProposalDetail(label: String, value: String) {
    Row(modifier = Modifier.padding(vertical = 4.dp)) {
        Text(
            text = label,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.width(110.dp)
        )
        Text(text = value)
    }
}

fun formatDateTime(dateTime: Timestamp): String {
    val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.UK)
    return formatter.format(dateTime)
}

@Composable
fun VoteButton(text: String, isSelected: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) Color.Green else Color.Gray,
            contentColor = Color.White
        ),
        modifier = Modifier
            .padding(1.dp)
            .height(36.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(text)
    }
}

@Preview(showBackground = true)
@Composable
fun ProposalDialogPreview() {
    val voteStartBlock: BigInteger = BigInteger.ZERO
    val voteEndBlock: BigInteger = BigInteger.TEN
    val currentTime = System.currentTimeMillis()
    ProposalDialogContent(
        proposal = Proposal(
            id = BigInteger.ONE,
            proposer = "0x54fd18a25591a716affed85356cbd89b050db7e2",
            description = "Считаете ли вы важным введение дополнительных мер по поддержке здоровья студентов?",
            voteStartBlock = voteStartBlock,
            voteEndBlock = voteEndBlock,
            voteStartBlockTimestamp = Timestamp(currentTime),
            voteEndBlockTimestamp = Timestamp(
                currentTime + (voteEndBlock - voteStartBlock).multiply(
                    BigInteger("100")
                ).toLong()
            ),
            votesFor = Token(BigInteger.ONE),
            votesAbstain = Token(BigInteger.ZERO),
            votesAgainst = Token(BigInteger.ZERO)
        ),
        account = Account(
            "test@gmail.com",
            "testInstitute",
            "testInstituteAbbreviation",
            "testFaculty",
            "0x54fd18a25591a716affed85356cbd89b050db7e2"
        ),
        currentAddress = "0x54fd18a25591a716affed85356cbd89b050db7e2",
        isConnected = true,
        hasVoted = false,
        onDismiss = {}
    ) {

    }
}