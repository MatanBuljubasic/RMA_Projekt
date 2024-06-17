package com.example.rma_project.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth

class LoginViewModel {
    fun signIn(context: Context, email: String, password: String, onNavigateToMain: () -> Unit) {
        if(email.isEmpty() || password.isEmpty()){
            Toast.makeText(context, "Password or email are empty", Toast.LENGTH_SHORT).show()
            return
        }
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if(task.isSuccessful) {
                    Toast.makeText(context, "Logged in succesfully", Toast.LENGTH_SHORT).show()
                    onNavigateToMain.invoke()
                } else {
                    Toast.makeText(context, "Login unsuccessful", Toast.LENGTH_SHORT).show()
                }
            }
    }
}