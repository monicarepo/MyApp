package com.ms.ecommerceapp.viewModel


import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ms.ecommerceapp.activity.SignInActivity
import com.ms.ecommerceapp.activity.SignUpActivity

class AuthViewModel: ViewModel() {
    val auth = Firebase.auth

    fun signIn(email: String, password: String, activity: SignInActivity?, context: Context) {
        println("Email: $email, Password: $password")
        activity?.navigateToHome()
            auth.signInWithEmailAndPassword(email,password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    //navigate to home screen.
                    activity?.navigateToHome()
                } else {
                    val exception = task.exception.toString()
                    Toast.makeText(
                        context,
                        exception,
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
    }

    fun createAccount(email: String, password: String, activity: SignUpActivity?, context: Context) {
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val user = auth.currentUser
                //navigate to home screen.
                showToast("Account created successfully ${user?.email}",context)
                val resultIntent = Intent()
                resultIntent.putExtra("email", user?.email)
                resultIntent.putExtra("password", password)
                activity?.setResult(RESULT_OK, resultIntent)
                activity?.onBackPressedDispatcher?.onBackPressed()
            } else {
                val exception = task.exception.toString()
                showToast(exception,context)
            }
        }
    }
}

fun showToast(message: String, context: Context) {
    println("Toast Message: $message")
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}