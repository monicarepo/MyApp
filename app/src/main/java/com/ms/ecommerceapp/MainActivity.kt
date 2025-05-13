package com.ms.ecommerceapp

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.ms.apptheme.ui.theme.AppTheme
import com.ms.ecommerceapp.receivers.MyBroadcastReceiver
import java.util.regex.Pattern

//Implemented SMS broadcast receiver to receive OTP from SMS
class MainActivity : ComponentActivity() {
    private val broadcastReceiver = MyBroadcastReceiver()
    private lateinit var consentLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        startSmartUserConsent()

        consentLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val data: Intent? = result.data
                val message = data?.getStringExtra(SmsRetriever.EXTRA_SMS_MESSAGE)
                Log.d("SMS", "Retrieved message: $message")
                Toast.makeText(this, "OTP Received: $message", Toast.LENGTH_LONG).show()
                getOtpFromMessage(message)
            }
        }
//        registerReceiver(broadcastReceiver, IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED))
        setContent {
            AppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }

    private fun startSmartUserConsent() {
        val client = SmsRetriever.getClient(this)
        client.startSmsUserConsent(null)
    }

    private fun getOtpFromMessage(message: String?) {
        val otpPatter = Pattern.compile("(|^)\\d{6}")
        val matcher = otpPatter.matcher(message)
        if (matcher.find()){
           println("OTP: ${matcher.group(0)}")
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun registerBroadcastReceiver(){
        broadcastReceiver.smsListener = object  : MyBroadcastReceiver.SmsListener{
            override fun onSuccess(intent: Intent) {
                consentLauncher.launch(intent)
            }

            override fun onFailure() {
                Toast.makeText(this@MainActivity, "SMS Listener failed", Toast.LENGTH_SHORT).show()
            }
        }
        val intentFilter = IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION)
        registerReceiver(broadcastReceiver,intentFilter, Context.RECEIVER_EXPORTED)
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onStart() {
        super.onStart()
        registerBroadcastReceiver()
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(broadcastReceiver)
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AppTheme {
        Greeting("Android")
    }
}