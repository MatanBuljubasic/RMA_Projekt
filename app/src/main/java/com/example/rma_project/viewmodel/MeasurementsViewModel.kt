package com.example.rma_project.viewmodel

import com.example.rma_project.model.MeasurementsModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class MeasurementsViewModel {
    fun saveData(measurementsModel: MeasurementsModel){
        val db = Firebase.firestore
    }

    fun addWaterIntake(intake:Int){

    }
}