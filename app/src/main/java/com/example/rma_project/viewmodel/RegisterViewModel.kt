package com.example.rma_project.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.navigation.NavController
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class RegisterViewModel {
    fun register(context: Context, email: String, password: String, navigation: () -> Unit) {
        if(email.isEmpty() || password.isEmpty()){
            Toast.makeText(context, "Password or email are empty", Toast.LENGTH_SHORT).show()
            return
        }
        runBlocking {
            launch {
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            runBlocking {
                                launch {
                                    val db = Firebase.firestore
                                    val defaultSettings = hashMapOf(
                                        "firstTime" to true,
                                        "goalWaterIntake" to 2,
                                        "goalWeight" to 0f,
                                        "height" to 0f
                                    )
                                    db.collection("users").document(email).set(defaultSettings)
                                }
                            }
                            Toast.makeText(context, "Registered successfully", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(context, "Registration failed", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }
        navigation.invoke()
    }
}