package com.ms.ecommerceapp.activity

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ms.apptheme.ui.theme.AppTheme
import com.ms.apptheme.ui.theme.primaryLight
import com.ms.ecommerceapp.R
import com.ms.ecommerceapp.viewModel.AuthViewModel

class SignUpActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        auth = Firebase.auth
        setContent {
            AppTheme {
                Scaffold(modifier = Modifier.fillMaxSize(), containerColor = primaryLight) { innerPadding ->
                    SignUpScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Preview
@Composable
fun SignUpScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp, 24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SignUpContent()
    }
}

@Composable
fun SignUpContent(viewModel: AuthViewModel = AuthViewModel()) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var isNameFocused by remember { mutableStateOf(false) }
    var isEmailFocused by remember { mutableStateOf(false) }
    var isPasswordFocused by remember { mutableStateOf(false) }
    var isConfirmPasswordFocused by remember { mutableStateOf(false) }
    var isNameError by remember { mutableStateOf(false) }
    var isEmailError by remember { mutableStateOf(false) }
    var isPasswordError by remember { mutableStateOf(false) }
    var isConfirmPasswordError by remember { mutableStateOf(false) }
    var nameErrorText by remember { mutableStateOf("Name cannot be empty") }
    var emailErrorText by remember { mutableStateOf("Email cannot be empty") }
    var passwordErrorText by remember { mutableStateOf("Password cannot be empty") }
    var confirmPasswordErrorText by remember { mutableStateOf("Confirm Password cannot be empty") }
    val emailRegex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")
    val context = LocalContext.current
    val activity = context as? SignUpActivity

    Card(
//        colors = CardDefaults.cardColors(
//            containerColor = onPrimaryContainerLight,
//        ),
//        modifier = Modifier
//            .border(2.dp, Color.White, shape = MaterialTheme.shapes.medium)
    ) {
        Text(
            text = stringResource(R.string.sign_up),
            modifier = Modifier
                .padding(vertical = 24.dp)
                .align(Alignment.CenterHorizontally),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = stringResource(R.string.name),
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = name,
            onValueChange = { name = it
                            isNameError = when {
                                name.isEmpty() -> {
                                    nameErrorText = "Name cannot be empty"
                                    true
                                }
                                else -> {
                                    nameErrorText = ""
                                    false
                                }
                            }
                            },
            placeholder = { Text(stringResource(R.string.enter_name)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .onFocusChanged { focusState ->
                    isNameFocused = focusState.isFocused
                },
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                cursorColor = if (isNameFocused) Color.Blue else Color.Black,
                focusedBorderColor = if (isNameFocused) Color.Black else Color.Gray,
                unfocusedBorderColor = if (isNameFocused) Color.Black else Color.Gray
            ),
            shape = RoundedCornerShape(8.dp),
            isError = isNameError
        )

        if (isNameError) {
            Text(
                text = nameErrorText,
                color = Color.Red,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = stringResource(R.string.email),
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 8.dp)
                .padding(bottom = 8.dp)
                .align(Alignment.Start),
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it
                            isEmailError = when {
                                email.isEmpty() -> {
                                    emailErrorText = "Email cannot be empty"
                                    true
                                }
                                !email.matches(emailRegex) -> {
                                    emailErrorText = "Invalid email format"
                                    true
                                }
                                else -> {
                                    emailErrorText = ""
                                    false
                                }
                            }
                            },
            placeholder = { Text(stringResource(R.string.enter_email)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .onFocusChanged { focusState ->
                    isEmailFocused = focusState.isFocused
                },
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                cursorColor = if (isEmailFocused) Color.Blue else Color.Black,
                focusedBorderColor = if (isEmailFocused) Color.Black else Color.Gray,
                unfocusedBorderColor = if (isEmailFocused) Color.Black else Color.Gray
            ),
            shape = RoundedCornerShape(8.dp),
            isError = isEmailError
        )
        if (isEmailError) {
            Text(
                text = emailErrorText,
                color = Color.Red,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            stringResource(R.string.password),
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 8.dp)
                .padding(bottom = 8.dp)
                .align(Alignment.Start)
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it
                            isPasswordError = when {
                                password.isEmpty() -> {
                                    passwordErrorText = "Password cannot be empty"
                                    true
                                }
                                password.length < 6 -> {
                                    passwordErrorText = "Password must be at least 6 characters"
                                    true
                                }
                                else -> {
                                    passwordErrorText = ""
                                    false
                                }
                            }

                            },
            placeholder = { Text(stringResource(R.string.enter_pass)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .onFocusChanged { focusState ->
                    isPasswordFocused = focusState.isFocused
                },
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                cursorColor = if (isPasswordFocused) Color.Blue else Color.Black,
                focusedBorderColor = if (isPasswordFocused) Color.Black else Color.Gray,
                unfocusedBorderColor = if (isPasswordFocused) Color.Black else Color.Gray
            ),
            shape = RoundedCornerShape(8.dp),
            visualTransformation = PasswordVisualTransformation(),
            isError = isPasswordError
            )
            if (isPasswordError) {
                Text(
                    text = passwordErrorText,
                    color = Color.Red,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            stringResource(R.string.confirmPass),
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 8.dp)
                .padding(bottom = 8.dp)
                .align(Alignment.Start)
        )

        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it
                isConfirmPasswordError = when {
                    it.isEmpty() -> {
                        confirmPasswordErrorText = "Confirm password cannot be empty"
                        true
                    }
                    it != password -> {
                        confirmPasswordErrorText = "Password and confirm password do not match"
                        true
                    }
                    else -> {
                        confirmPasswordErrorText = ""
                        false
                    }
                }
                            },
            placeholder = { Text(stringResource(R.string.confirmPass)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .onFocusChanged { focusState ->
                    isConfirmPasswordFocused = focusState.isFocused
                },
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                cursorColor = if (isConfirmPasswordFocused) Color.Blue else Color.Black,
                focusedBorderColor = if (isConfirmPasswordFocused) Color.Black else Color.Gray,
                unfocusedBorderColor = if (isConfirmPasswordFocused) Color.Black else Color.Gray
            ),
            shape = RoundedCornerShape(8.dp),
            visualTransformation = PasswordVisualTransformation(),
            isError = isConfirmPasswordError

            )

            if (isConfirmPasswordError) {
                Text(
                    text = confirmPasswordErrorText,
                    color = Color.Red,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { /* Handle sign in
            Add validation for email and password here */
                if(name.isEmpty() && email.isEmpty() && password.isEmpty() && confirmPassword.isEmpty()){
                    isNameError = true
                    isEmailError = true
                    isPasswordError = true
                    isConfirmPasswordError = true
                }
                else if (name.isEmpty()) {
                    isNameError = true
                } else if (email.isEmpty()) {
                    isEmailError = true
                } else if (password.isEmpty() || password.length < 6) {
                    isPasswordError = true
                } else if (confirmPassword.isEmpty() || confirmPassword != password) {
                    isConfirmPasswordError = true
                    confirmPasswordErrorText = "Password and confirm password does not match"
                } else {
                    onSignUpClick(email, password, activity, context, viewModel)
                }
            },
            modifier = Modifier
                .padding(top = 8.dp)
                .padding(bottom = 24.dp)
                .align(Alignment.CenterHorizontally),
//            colors = ButtonDefaults.buttonColors(
//                containerColor = primary,
//                contentColor = white
//            )
        ) {
            Text(stringResource(R.string.sign_up))
        }
    }
}

fun onSignUpClick(
    email: String,
    password: String,
    activity: SignUpActivity?,
    context: Context,
    viewModel: AuthViewModel
) {
    println("Email: $email, Password: $password")
    viewModel.createAccount(email, password, activity, context)
}