package com.example.rma_project.view

import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rma_project.model.MeasurementsModel
import com.example.rma_project.model.StartingDataModel
import com.example.rma_project.ui.theme.RMA_ProjectTheme
import com.example.rma_project.viewmodel.MeasurementsViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import java.io.Console
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(measurementsViewModel: MeasurementsViewModel, onNavigateToSettings: () -> Unit, onNavigateToInstructions: () -> Unit, onNavigateToMyData: () -> Unit) {
    var weight by remember { mutableStateOf("0") }
    var fatPercentage by remember { mutableStateOf("0") }
    var waterIntake by remember { mutableStateOf("0") }
    var todaysWaterIntake by remember { mutableStateOf("0") }
    var goalWeight by remember { mutableStateOf("0") }
    var goalWaterIntake by remember { mutableStateOf("0") }
    var height by remember { mutableStateOf("0") }

    val context = LocalContext.current

    val email = Firebase.auth.currentUser?.email
    val db = Firebase.firestore
    var firstTime by remember { mutableStateOf(false) }
    val userInfo = db.collection("users").document(email!!)
    val waterIntakeInfo = db.collection("users").document(email).collection("waterIntake").document(LocalDate.now().toString())
    userInfo.get().addOnSuccessListener {
            document ->
        firstTime = document.get("firstTime") as Boolean
        goalWeight = (document.get("goalWeight") as Double).toString()
        height = (document.get("height") as Double).toString()
        goalWaterIntake = (document.get("goalWaterIntake") as Long).toString()
    }
    waterIntakeInfo.get().addOnSuccessListener {
            document ->
        if(document.get("totalIntake") == null){
            todaysWaterIntake = "0"
        } else {
            todaysWaterIntake = (document.get("totalIntake") as Long).toString()
        }

    }
    if(firstTime){
        AlertDialog(
            onDismissRequest = {}) {
            Surface(
                color = Color(132, 62, 62),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column {
                    Text(text = "First time setup", color = Color.White, fontSize = 40.sp)
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(text = "It appears this is your first time using this app. Would you please fill out some starting information for us?", color = Color.White)
                    Spacer(modifier = Modifier.height(20.dp))
                    OutlinedTextField(
                        value = goalWeight,
                        onValueChange = {goalWeight = it},
                        label =  { Text(text = "Goal weight: ")},
                        suffix = { Text(text = "kg", color = Color.White)},
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedLabelColor = Color.White,
                            unfocusedBorderColor = Color.White,
                        )
                    )
                    OutlinedTextField(
                        value = height,
                        onValueChange = {height = it},
                        label =  { Text(text = "Height: ")},
                        suffix = { Text(text = "m", color = Color.White)},
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedLabelColor = Color.White,
                            unfocusedBorderColor = Color.White,
                        )
                    )
                    OutlinedTextField(
                        value = goalWaterIntake,
                        onValueChange = {goalWaterIntake = it},
                        label =  { Text(text = "Goal water intake: ")},
                        suffix = { Text(text = "cups", color = Color.White)},
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedLabelColor = Color.White,
                            unfocusedBorderColor = Color.White,
                        )
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Button(onClick = { firstTime = false; measurementsViewModel.saveStartingData(context, StartingDataModel(goalWeight.toFloatOrNull(), height.toFloatOrNull(), goalWaterIntake.toIntOrNull(), firstTime))}) {
                        Text(text = "Save")
                    }
                }
            }
        }
    }
    Box(modifier = Modifier.fillMaxSize()){
        BackgroundImage(modifier = Modifier)
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 30.dp),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                Modifier,
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ){
                Text(
                    text = "Save your current measurements",
                    color = Color.White
                )
                OutlinedTextField(
                    value = weight,
                    onValueChange = { weight = it },
                    label =  { Text(text = "Weight: ")},
                    suffix = { Text(text = "kg", color = Color.White) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedLabelColor = Color.White,
                        unfocusedBorderColor = Color.White,
                    )
                )
                OutlinedTextField(
                    value = fatPercentage,
                    onValueChange = { fatPercentage = it },
                    label =  { Text(text = "Fat Percentage: ")},
                    suffix = { Text(text = "%", color = Color.White)},
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedLabelColor = Color.White,
                        unfocusedBorderColor = Color.White,
                    )
                )
                Spacer(modifier = Modifier.height(20.dp))
                Button(
                    onClick = { measurementsViewModel.saveData(context, MeasurementsModel(weight.toFloatOrNull(), 0f, fatPercentage.toFloatOrNull()), height.toFloatOrNull()) }
                ) {
                    Modifier.width(200.dp)
                    Text(text = "Save measurements")
                }
            }
            Divider(color = Color.White, thickness = 1.dp, modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 5.dp))
            Spacer(modifier = Modifier.height(20.dp))
            Column {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(onClick = { todaysWaterIntake = measurementsViewModel.addWaterIntake(context, waterIntake.toIntOrNull(), todaysWaterIntake.toIntOrNull()).toString() }) {
                        Modifier.width(50.dp)
                        Text(text = "Add water")
                    }
                    Spacer(modifier = Modifier.width(50.dp))
                    OutlinedTextField(
                        value = waterIntake,
                        onValueChange = { waterIntake = it },
                        modifier = Modifier.width(120.dp),
                        suffix = { Text(text = "cups", color = Color.White)},
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedLabelColor = Color.White,
                            unfocusedBorderColor = Color.White,
                        )
                    )
                }
                Spacer(modifier = Modifier.height(50.dp))
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Today's water intake: ", color = Color.White)
                    Spacer(modifier = Modifier.width(20.dp))
                    Text(text = "$todaysWaterIntake cups", color = Color.White)
                }
                Spacer(modifier = Modifier.height(50.dp))
                var remainingWaterIntake: String = ""
                if(!firstTime){
                    remainingWaterIntake = if (goalWaterIntake.toIntOrNull()
                            ?.minus(todaysWaterIntake.toIntOrNull()!!)!! < 0
                    ) {
                        "0"
                    } else {
                        goalWaterIntake.toIntOrNull()?.minus(todaysWaterIntake.toIntOrNull()!!)!!
                            .toString()
                    }
                }
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Remaining water intake for today: ", color = Color.White)
                    Spacer(modifier = Modifier.width(20.dp))
                    Text(text = "$remainingWaterIntake cups", color = Color.White)
                }
            }
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 5.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(onClick = onNavigateToSettings) {
                    Text(text = "Settings")
                }
                Button(onClick = onNavigateToMyData) {
                    Text(text = "My Data")
                }
                Button(onClick = onNavigateToInstructions) {
                    Text(text = "Instructions")
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    RMA_ProjectTheme {
        MainScreen(measurementsViewModel = MeasurementsViewModel(), onNavigateToSettings = {}, onNavigateToInstructions = {}, onNavigateToMyData = {})
    }
}