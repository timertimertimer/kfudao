package com.example.dao

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.dao.ui.theme.DaoTheme
import com.example.dao.viewmodels.MainViewModel
import com.example.dao.views.AuthScreen
import com.example.dao.views.WalletBar
import com.example.dao.views.CreateProposalDialog
import com.example.dao.views.LazyProposals
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DaoTheme {
                AuthScreen(mainViewModel)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainUI(mvm: MainViewModel, modifier: Modifier = Modifier) {
    val coroutineScope = rememberCoroutineScope()
    val proposals by mvm.proposals.collectAsState()
    val account by mvm.account.collectAsState()
    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(title = {
                WalletBar(mvm) {
                    coroutineScope.launch {
                        mvm.connect()
                    }
                }
            }, modifier)
        },
        floatingActionButton = {
            if (mvm.connected && account?.address == mvm.address) {
                FloatingActionButton(onClick = { showDialog = true }) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_add_circle_24),
                        contentDescription = stringResource(id = R.string.create_proposal),
                        tint = Color.LightGray
                    )
                }
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            LazyProposals(proposals.reversed(), mvm)
        }
    }

    if (showDialog) {
        CreateProposalDialog(mvm) { showDialog = false }
    }
}
