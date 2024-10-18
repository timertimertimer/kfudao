package com.example.dao.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.dao.MainUI
import com.example.dao.R
import com.example.dao.models.Account
import com.example.dao.viewmodels.MainViewModel

@Composable
fun AuthScreen(mvm: MainViewModel) {
    var isLoginScreen by remember { mutableStateOf(true) }
    var email by remember { mutableStateOf<String?>(null) }
    val account by mvm.account.collectAsState()
    val institutes by mvm.institutes.collectAsState()

    LaunchedEffect(email) {
        if (email != null) mvm.updateLocalAccount(email!!, null)
    }

    if (email != null && account != null) {
        MainUI(mvm = mvm)
    } else if (isLoginScreen) {
        LoginScreen(
            { e, p, ol -> mvm.signIn(e, p, ol) },
            onSwitchToRegister = { isLoginScreen = false }) {
            email = it
        }
    } else {
        RegisterScreen(
            institutes = institutes,
            onSwitchToLogin = { isLoginScreen = true },
            registerNewUser = { a, p, e, s -> mvm.registerNewUser(a, p, e, s) },
            loadFaculties = { mvm.getFaculties(it) }
        ) {
            email = it
        }
    }
}

@Composable
fun LoginScreen(
    signIn: (String, String, (String) -> Unit) -> Unit,
    onSwitchToRegister: () -> Unit,
    onLogin: (String) -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    LoginScreenContent(
        email = email,
        onEmailChange = { email = it },
        password = password,
        onPasswordChange = { password = it },
        onSwitchToRegister = { onSwitchToRegister() }
    ) {
        signIn(email, password, onLogin)
        onLogin(email)
    }
}

@Composable
fun LoginScreenContent(
    email: String,
    onEmailChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    onSwitchToRegister: () -> Unit,
    onSignIn: () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = email,
            onValueChange = { onEmailChange(it) },
            label = { Text(stringResource(id = R.string.email)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = password,
            onValueChange = { onPasswordChange(it) },
            label = { Text(stringResource(id = R.string.password)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            onSignIn()
        }) { Text(stringResource(R.string.login)) }
        Spacer(modifier = Modifier.height(8.dp))
        TextButton(onClick = onSwitchToRegister) {
            Text(stringResource(R.string.create_account))
        }
    }
}

@Composable
fun RegisterScreen(
    institutes: HashMap<String, String>,
    onSwitchToLogin: () -> Unit,
    registerNewUser: (Account, String, (Exception?) -> Unit, (String) -> Unit) -> Unit,
    loadFaculties: (String) -> List<String>,
    onRegister: (String) -> Unit,
) {
    RegisterScreenContent(
        institutes = institutes,
        onSwitchToLogin = onSwitchToLogin,
        onRegister = onRegister,
        registerNewUser = registerNewUser,
        loadFaculties = loadFaculties
    )
}

@Composable
fun RegisterScreenContent(
    institutes: HashMap<String, String>,
    onSwitchToLogin: () -> Unit,
    onRegister: (String) -> Unit,
    registerNewUser: (Account, String, (Exception?) -> Unit, (String) -> Unit) -> Unit,
    loadFaculties: (String) -> List<String>
) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    var selectedInstitute by remember { mutableStateOf<String?>(null) }
    var selectedInstituteAbbreviation by remember { mutableStateOf<String?>(null) }
    var selectedFaculty by remember { mutableStateOf<String?>(null) }
    var faculties by remember { mutableStateOf<List<String>?>(null) }

    LaunchedEffect(selectedInstitute) {
        faculties = loadFaculties(selectedInstituteAbbreviation ?: "")
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = errorMessage, color = androidx.compose.ui.graphics.Color.Red)
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text(stringResource(id = R.string.email)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(stringResource(id = R.string.password)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(8.dp))

        CustomDropdownMenu(
            selectedItem = selectedInstitute,
            items = institutes.values.toList(),
            label = stringResource(id = R.string.select_institute),
            onItemSelected = { institute ->
                selectedInstitute = institute
                selectedInstituteAbbreviation =
                    institutes.entries.find { it.value == institute }?.key
            }
        )
        Spacer(modifier = Modifier.height(8.dp))
        if (selectedInstitute != null) {
            CustomDropdownMenu(
                selectedItem = selectedFaculty,
                items = faculties!!,
                label = stringResource(id = R.string.select_faculty),
                onItemSelected = { faculty ->
                    selectedFaculty = faculty
                }
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
        Button(onClick = {
            if (selectedInstitute != null && selectedFaculty != null) {
                registerNewUser(
                    Account(
                        email = email,
                        institute = selectedInstitute!!,
                        instituteAbbreviation = selectedInstituteAbbreviation!!,
                        faculty = selectedFaculty!!, null
                    ),
                    password,
                    { error -> errorMessage = error?.message ?: "Неизвестная ошибка" }
                ) {
                    onRegister(email)
                }
            } else {
                errorMessage = "Выберите институт и факультет"
            }
        }) {
            Text(stringResource(R.string.register))
        }
        Spacer(modifier = Modifier.height(8.dp))
        TextButton(onClick = onSwitchToLogin) {
            Text(stringResource(R.string.account_already_created))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDropdownMenu(
    selectedItem: String?,
    items: List<String>,
    label: String,
    onItemSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(selectedItem ?: label) }

    ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
        TextField(
            value = selectedText,
            onValueChange = {},
            readOnly = true,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = modifier
                .menuAnchor()
                .padding(horizontal = 40.dp),
            label = { Text(label) },
            singleLine = true
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            items.forEach { item ->
                DropdownMenuItem(
                    onClick = {
                        selectedText = item
                        onItemSelected(item)
                        expanded = false
                    },
                    text = { Text(text = item) }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreenContent(
        email = "",
        onEmailChange = {},
        password = "",
        onPasswordChange = {},
        onSwitchToRegister = {},
    ) {}
}

@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    RegisterScreenContent(
        institutes = hashMapOf(
            "ИВМИИТ" to "Институт вычислительной математики и информационных технологий"
        ),
        onSwitchToLogin = {},
        onRegister = {},
        registerNewUser = { _, _, _, _ -> }
    ) { listOf() }
}