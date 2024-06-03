package com.example.rma_project.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.rma_project.model.MeasurementsModel
import com.example.rma_project.viewmodel.MeasurementsViewModel

@Composable
fun MainScreen(measurementsViewModel: MeasurementsViewModel, navController: NavController) {
    var weight by remember { mutableFloatStateOf(0f) }
    var bmi by remember { mutableFloatStateOf(0f) }
    var fatPercentage by remember { mutableFloatStateOf(0f) }
    var waterIntake by remember { mutableIntStateOf(0) }
    var waterMeasuringUnit by remember {  mutableStateOf("")  }
    Box(modifier = Modifier.fillMaxSize()){
        BackgroundImage(modifier = Modifier)
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(30.dp),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                Modifier.border(BorderStroke(2.dp, SolidColor(Color.White)))
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Text(
                    text = "Save your current measurements",
                    color = Color.White
                )
                OutlinedTextField(
                    value = weight.toString(),
                    onValueChange = { weight = it.toFloatOrNull()!! },
                    label =  { Text(text = "Weight: ")}
                )
                OutlinedTextField(
                    value = bmi.toString(),
                    onValueChange = { bmi = it.toFloatOrNull()!! },
                    label =  { Text(text = "BMI: ")}
                )
                OutlinedTextField(
                    value = fatPercentage.toString(),
                    onValueChange = { fatPercentage = it.toFloatOrNull()!! },
                    label =  { Text(text = "Fat Percentage: ")}
                )
                Button(
                    onClick = { measurementsViewModel.saveData(MeasurementsModel(weight, bmi, fatPercentage)) }
                ) {
                    Modifier.width(200.dp)
                    Text(text = "Save measurements")
                }
            }
            Column {
                Row {
                    Button(onClick = { measurementsViewModel.addWaterIntake(waterIntake) }) {
                        Modifier.width(200.dp)
                        Text(text = "Add water intake")
                    }
                    OutlinedTextField(
                        value = waterIntake.toString(),
                        onValueChange = { waterIntake = it.toIntOrNull()!! }
                    )
                    Text(text = waterMeasuringUnit)
                }
            }
        }
    }

}