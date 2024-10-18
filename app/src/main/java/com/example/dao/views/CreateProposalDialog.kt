package com.example.dao.views

import android.util.Log
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.dao.R
import com.example.dao.viewmodels.MainViewModel
import kotlinx.coroutines.launch

@Composable
fun CreateProposalDialog(mvm: MainViewModel, onDismiss: () -> Unit) {
    var content by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()
    CreateProposalDialogContent(
        onDismiss = onDismiss,
        content = content,
        onContentChange = { content = it },
    ) {
        coroutineScope.launch {
            try {
                mvm.createProposal(content)
            } catch (e: Exception) {
                Log.e("MainActivity", e.message.toString())
            }
        }
    }
}

@Composable
fun CreateProposalDialogContent(
    onDismiss: () -> Unit,
    content: String,
    onContentChange: (String) -> Unit,
    createProposal: (String) -> Unit,
) {
    val isButtonEnabled = content.isNotEmpty()

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = stringResource(id = R.string.create_proposal)) },
        text = { TextField(value = content, onValueChange = onContentChange) },
        confirmButton = {
            Button(
                onClick = {
                    createProposal(content)
                    onDismiss()
                },
                enabled = isButtonEnabled
            ) {
                Text(text = stringResource(R.string.ok))
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun CreateProposalDialogPreview() {
    CreateProposalDialogContent(
        onDismiss = { }, content =
        "Считаете ли вы важным введение дополнительных мер по поддержке здоровья студентов?",
        onContentChange = {}
    ) { }
}