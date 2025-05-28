package com.ms.ecommerceapp


import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.LaunchedEffect
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
import com.ms.ecommerceapp.dependencyInjection.DatabaseAdapter
import com.ms.ecommerceapp.dependencyInjection.DatabaseService
import com.ms.ecommerceapp.network.NetworkManager
import com.ms.ecommerceapp.receivers.WifiBroadcastReceiver
import com.ms.ecommerceapp.viewModel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SignInActivity : ComponentActivity() {
    lateinit var auth: FirebaseAuth
    var onEmailResult: ((String) -> Unit)? = null
    var onPasswordResult: ((String) -> Unit)? = null
    private val broadcastReceiver = WifiBroadcastReceiver()
    //Hilt Implementation
    @Inject lateinit var databaseAdapter: DatabaseAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        NetworkManager.initialize(this)
        databaseAdapter.log("Hello Hilt")
        enableEdgeToEdge()
        auth = Firebase.auth
        setContent {
            AppTheme {
                Scaffold(modifier = Modifier.fillMaxSize(), containerColor = primaryLight) { innerPadding ->
                    SignInScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }

    @Inject
    fun directToDatabase(databaseService: DatabaseService) {
        databaseService.log("Hello Hilt Method Injection Service")
    }


    override fun onStart() {
        super.onStart()
//        registerReceiver(broadcastReceiver, IntentFilter("android.net.wifi.WIFI_STATE_CHANGED"))
    }

    override fun onDestroy() {
        super.onDestroy()
//        unregisterReceiver(broadcastReceiver)
        NetworkManager.unRegisterNetworkCallback()
    }

    fun navigateToSignUp() {
        val isOnline = NetworkManager.getConnectionStatus(this)
        Log.d("NetworkManager", "Network Available: $isOnline")
        val intent = Intent(this, SignUpActivity::class.java)
        signInLauncher.launch(intent)
    }

    fun navigateToHome() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
    }

    private val signInLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        result ->
        if (result.resultCode == RESULT_OK) {
            val data = result.data
            val email = data?.getStringExtra("email")
            val password = data?.getStringExtra("password")
            println("Email: $email, Password: $password")
            email?.let {
                onEmailResult?.invoke(it)
            }
            password?.let {
                onPasswordResult?.invoke(it)
            }
        }
    }
}

@Composable
fun SignInScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp, 24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SignInContent()
    }
}

@Composable
fun SignInContent(viewModel: AuthViewModel = AuthViewModel()) {
    var email by remember { mutableStateOf("moni@gmail.com") }
    var password by remember { mutableStateOf("123456") }
    var isEmailFocused by remember { mutableStateOf(false) }
    var isPasswordFocused by remember { mutableStateOf(false) }
    var isEmailError by remember { mutableStateOf(false) }
    var isPasswordError by remember { mutableStateOf(false) }
    var emailErrorText by remember { mutableStateOf("Email cannot be empty") }
    var passwordErrorText by remember { mutableStateOf("Password cannot be empty") }
    val emailRegex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")
    val context = LocalContext.current
    val activity = context as? SignInActivity

    LaunchedEffect(Unit) {
        activity?.onEmailResult = { resultEmail ->
            email = resultEmail
        }
        activity?.onPasswordResult = { resultPassword ->
            password = resultPassword
        }
    }

    Card(
//        colors = CardDefaults.cardColors(
//            containerColor = onPrimaryContainerLight,
//        ),
//        modifier = Modifier
//            .border(2.dp, Color.White, shape = MaterialTheme.shapes.medium)
    ) {
        Text(
            text = stringResource(R.string.sign_in),
            modifier = Modifier
                .padding(vertical = 24.dp)
                .align(Alignment.CenterHorizontally),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = stringResource(R.string.email),
            modifier = Modifier
                .padding(horizontal = 16.dp)
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
//            colors = OutlinedTextFieldDefaults.colors(
//                focusedTextColor = Color.Black,
//                unfocusedTextColor = Color.Black,
//                cursorColor = if (isEmailFocused) Color.Blue else Color.Black,
//                focusedBorderColor = if (isEmailFocused) Color.Black else Color.Gray,
//                unfocusedBorderColor = if (isEmailFocused) Color.Black else Color.Gray
//            ),
            shape = RoundedCornerShape(8.dp)
        )
        if (isEmailError) {
            Text(
                text = emailErrorText,
                color = Color.Red,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

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

        )
        if (isPasswordError) {
            Text(
                text = passwordErrorText,
                color = Color.Red,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { /* Handle sign in */
                if (email.isEmpty() && password.isEmpty()) {
                    isEmailError = true
                    isPasswordError = true
                } else {
                    if (!isEmailError && !isPasswordError) {
                        viewModel.signIn(email, password, activity, context)
                    }
                }
            },
            modifier = Modifier
                .padding(bottom = 16.dp)
                .align(Alignment.CenterHorizontally),
//            colors = ButtonDefaults.buttonColors(
//                containerColor = primaryDark,
//                contentColor = onPrimaryContainerLight
//            )
        ) {
            Text(stringResource(R.string.sign_in))
        }

        Text(
            text = stringResource(R.string.new_account),
            modifier = Modifier
                .padding(16.dp)
                .padding(bottom = 24.dp)
                .align(Alignment.CenterHorizontally)
                .clickable {
                    activity?.navigateToSignUp()
                },
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SignInScreenPreview() {
    AppTheme {
        SignInScreen()
    }
}