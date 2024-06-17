package com.example.rma_project.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.rma_project.model.StartingDataModel
import com.example.rma_project.viewmodel.MeasurementsViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore

@Composable
fun SettingsScreen(measurementsViewModel: MeasurementsViewModel, onNavigateToMain: () -> Unit){
    var goalWeight by remember { mutableStateOf("") }
    var goalWaterIntake by remember { mutableStateOf("") }
    var height by remember { mutableStateOf("") }
    var newGoalWeight by remember { mutableStateOf("") }
    var newGoalWaterIntake by remember { mutableStateOf("") }
    var newHeight by remember { mutableStateOf("") }
    val context = LocalContext.current
    val email = Firebase.auth.currentUser?.email
    val db = Firebase.firestore
    val userInfo = db.collection("users").document(email!!)
    userInfo.get().addOnSuccessListener {
            document ->
        goalWeight = (document.get("goalWeight") as Double).toString()
        height = (document.get("height") as Double).toString()
        goalWaterIntake = (document.get("goalWaterIntake") as Long).toString()
    }
    Box(modifier = Modifier.fillMaxSize()){
        BackgroundImage(modifier = Modifier)
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 30.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Start) {
                IconButton(onClick = onNavigateToMain) {
                    Icon(imageVector = Icons.Filled.KeyboardArrowLeft, contentDescription = "Back button", tint = Color.White)
                }
                Text(text = "Instructions", color = Color.White)
            }
            Divider(color = Color.White, thickness = 1.dp, modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 5.dp))
            Column(
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                OutlinedTextField(
                    value = newGoalWeight,
                    onValueChange = { newGoalWeight = it },
                    placeholder = { Text(text = goalWeight, color = Color.White)},
                    label = { Text(text = "Goal weight: ") },
                    suffix = { Text(text = "kg", color = Color.White) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedLabelColor = Color.White,
                        unfocusedBorderColor = Color.White,
                    )
                )
                OutlinedTextField(
                    value = newHeight,
                    onValueChange = { newHeight = it },
                    placeholder = { Text(text = height, color = Color.White)},
                    label = { Text(text = "Height: ") },
                    suffix = { Text(text = "m", color = Color.White) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedLabelColor = Color.White,
                        unfocusedBorderColor = Color.White,
                    )
                )
                OutlinedTextField(
                    value = newGoalWaterIntake,
                    onValueChange = { newGoalWaterIntake = it },
                    placeholder = { Text(text = goalWaterIntake, color = Color.White)},
                    label = { Text(text = "Goal water intake: ") },
                    suffix = { Text(text = "cups", color = Color.White) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedLabelColor = Color.White,
                        unfocusedBorderColor = Color.White,
                    )
                )
                Button(onClick = { measurementsViewModel.saveStartingData(context, StartingDataModel(newGoalWeight.toFloatOrNull(), newHeight.toFloatOrNull(), newGoalWaterIntake.toIntOrNull(), false))}) {
                    Text(text = "Save")
                }
            }
        }
    }
}