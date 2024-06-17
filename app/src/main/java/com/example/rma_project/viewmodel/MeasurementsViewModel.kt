package com.example.rma_project.viewmodel

import android.content.Context
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.runtime.LaunchedEffect
import com.example.rma_project.model.MeasurementsModel
import com.example.rma_project.model.StartingDataModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.time.LocalDate

class MeasurementsViewModel {
    @RequiresApi(Build.VERSION_CODES.O)
    fun saveData(context: Context, measurementsModel: MeasurementsModel, height: Float?){
        if(height == null || measurementsModel.weight == null || measurementsModel.fatPercentage == null){
            Toast.makeText(context, "One or more measurements are not a number", Toast.LENGTH_SHORT).show()
            return
        }
        val db = Firebase.firestore
        val user = Firebase.auth.currentUser
        measurementsModel.bmi = measurementsModel.weight!! / (height * height)
        if (user != null) {
            db.collection("users").document(user.email!!).collection("measurements").document(LocalDate.now().toString()).set(measurementsModel).addOnCompleteListener {
                task ->
                if(task.isSuccessful) {
                    Toast.makeText(context, "Successfully saved measurements", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun saveStartingData(context: Context, startingDataModel: StartingDataModel){
        if(startingDataModel.height == null || startingDataModel.goalWeight == null || startingDataModel.goalWaterIntake == null){
            Toast.makeText(context, "One or more measurements are not a number", Toast.LENGTH_SHORT).show()
            startingDataModel.firstTime = true
            return
        }
        val db = Firebase.firestore
        val user = Firebase.auth.currentUser
        if (user != null) {
            db.collection("users").document(user.email!!).set(startingDataModel).addOnCompleteListener {
                    task ->
                if(task.isSuccessful) {
                    Toast.makeText(context, "Successfully saved data", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun addWaterIntake(context: Context, currentIntake:Int?, todaysIntake:Int?): Int{
        if (currentIntake == null) {
            Toast.makeText(context, "Value is not a number", Toast.LENGTH_SHORT).show()
            return 0
        }
        val db = Firebase.firestore
        val user = Firebase.auth.currentUser
        val totalIntake = hashMapOf("totalIntake" to currentIntake + todaysIntake!!)
        if (user != null) {
            db.collection("users").document(user.email!!).collection("waterIntake").document(LocalDate.now().toString()).set(totalIntake).addOnCompleteListener {
                    task ->
                if(task.isSuccessful) {
                    Toast.makeText(context, "Successfully saved data", Toast.LENGTH_SHORT).show()
                }
            }
        }
        return totalIntake["totalIntake"]!!.toInt()
    }
}