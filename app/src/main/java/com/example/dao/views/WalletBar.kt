package com.example.dao.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dao.R
import com.example.dao.models.Account
import com.example.dao.viewmodels.MainViewModel

@Composable
fun WalletBar(mvm: MainViewModel, connect: () -> Unit) {
    var showDialog by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    var account by remember(mvm.account) { mutableStateOf<Account?>(null) }

    LaunchedEffect(mvm.account) {
        mvm.account.collect { newAccount ->
            account = newAccount
        }
    }

    WalletBarRow(
        account = account,
        currentAddress = mvm.address,
        isConnected = mvm.connected,
        connect = { connect() },
        disconnect = { mvm.disconnect() },
        onShowDialogChanged = { showDialog = true }
    )

    if (showDialog) {
        val message = "Необходимо подвязать адрес ${mvm.address} к аккаунту"
        BindWallet(message, onDismiss = { showDialog = false }) {
            val accountState = mvm.updateAccountAddress()
            accountState.value?.let {updatedAccount ->
                mvm.saveUserDataToFirestore(updatedAccount ) { error ->
                    errorMessage = error?.message ?: "Неизвестная ошибка"
                }
            }
            showDialog = false
        }
    }
}

@Composable
fun WalletBarRow(
    account: Account?,
    currentAddress: String?,
    isConnected: Boolean,
    connect: () -> Unit,
    disconnect: () -> Unit,
    onShowDialogChanged: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.weight(1f)) {
            CustomText { "Адрес: ${if (isConnected) currentAddress else "не подключен"}" }
            if (isConnected && account?.address == null) {
                onShowDialogChanged()
            } else if (isConnected && account?.address != currentAddress) {
                CustomText(color = Color.Red) { stringResource(id = R.string.incorrect_wallet) }
            }
        }
        Spacer(modifier = Modifier.width(8.dp))
        Button(
            onClick = { if (isConnected) disconnect() else connect() },
            modifier = Modifier
                .width(70.dp)
                .height(48.dp)
                .padding(6.dp),
            contentPadding = PaddingValues(0.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_account_balance_wallet_24),
                contentDescription = stringResource(id = if (isConnected) R.string.disconnect else R.string.connect),
                tint = Color.LightGray
            )
        }
    }
}

@Composable
fun BindWallet(text: String, onDismiss: () -> Unit, onConfirm: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss, text = { Text(text) },
        confirmButton = { Button(onClick = onConfirm) { Text(text = stringResource(R.string.ok)) } }
    )
}

@Composable
fun CustomText(
    color: Color = Color.Unspecified,
    text: @Composable () -> String
) {
    Text(
        modifier = Modifier.padding(start = 8.dp),
        text = text(),
        overflow = TextOverflow.Ellipsis,
        maxLines = 1,
        color = color,
        fontSize = 16.sp
    )
}

@Preview(showBackground = true)
@Composable
fun WalletBarRowPreview() {
    WalletBarRow(
        account = Account(
            "test@gmail.com",
            "testInstitute",
            "testInstituteAbbreviation",
            "testFaculty",
            "0x54fd18a25591a716affed85356cbd89b050db7e2"
        ),
        currentAddress = "0x54fd18a25591a716affed85356cbd89b050db7e2",
        isConnected = true,
        connect = {  },
        disconnect = {  },
        onShowDialogChanged = {}
    )
}

@Preview(showBackground = true)
@Composable
fun WalletBarRowIncorrectPreview() {
    WalletBarRow(
        account = Account(
            "test@gmail.com",
            "testInstitute",
            "testInstituteAbbreviation",
            "testFaculty",
            "0x54fd18a25591a716affed85356cbd89b050db7e2"
        ),
        currentAddress = "0x54fd18a25591a716affed85356cbd89b050db7e",
        isConnected = true,
        connect = { },
        disconnect = { },
        onShowDialogChanged = {}
    )
}