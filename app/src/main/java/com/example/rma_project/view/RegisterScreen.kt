package com.example.rma_project.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.rma_project.viewmodel.RegisterViewModel

@Composable
fun RegisterScreen(viewModel: RegisterViewModel, onNavigateToLogin: () -> Unit) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current
    Box(modifier = Modifier.fillMaxSize()){
        BackgroundImage(modifier = Modifier)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(30.dp),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Welcome to Lifestyle Coach",
                fontSize = 60.sp,
                lineHeight = 60.sp,
                textAlign = TextAlign.Center,
                color = Color.White
            )
            Text(
                text = "Please register or login, if you have an account already",
                fontSize = 25.sp,
                lineHeight = 25.sp,
                textAlign = TextAlign.Center,
                color = Color.White
            )
            Column {
                OutlinedTextField(
                    value = email,
                    onValueChange = {email = it},
                    label = { Text(text = "Email:") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedLabelColor = Color.White,
                        unfocusedBorderColor = Color.White,
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = password,
                    onValueChange = {password = it},
                    label = { Text(text = "Password:") },
                    visualTransformation = PasswordVisualTransformation(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedLabelColor = Color.White,
                        unfocusedBorderColor = Color.White,
                    )
                )
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Button(onClick = { viewModel.register(context, email, password, onNavigateToLogin) },
                    Modifier.width(200.dp)) {
                    Text(text = "Register")
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = onNavigateToLogin,
                    Modifier.width(200.dp)) {
                    Text(text = "Go to login")
                }
            }
        }
    }
}