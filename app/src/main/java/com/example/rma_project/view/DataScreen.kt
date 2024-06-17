package com.example.rma_project.view

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.rma_project.model.DataModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.firestore

@Composable
fun DataScreen(onNavigateToMain: () -> Unit){
    val context = LocalContext.current
    val email = Firebase.auth.currentUser?.email
    val db = Firebase.firestore
    var data = remember { mutableSetOf<DataModel>() }
    val userInfo = db.collection("users").document(email!!).collection("measurements")
    userInfo.get().addOnSuccessListener {
        documents ->
        for (document in documents){
            data.add(DataModel(document.id, document.get("bmi").toString(), document.get("weight").toString(), document.get("fatPercentage").toString()))
            Log.i("documents", data.toString())
        }
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
                Text(text = "My data", color = Color.White)
            }
            Divider(color = Color.White, thickness = 1.dp, modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 5.dp))
            LazyColumn {
                items(
                    data.toList()
                ){
                    item ->
                    Column {
                        Text(text = item.date, color = Color.White)
                        Text(text = "BMI: ${item.bmi}", color = Color.White)
                        Text(text = "Fat percentage: ${item.fatPercentage}%", color = Color.White)
                        Text(text = "Weight: ${item.weight}kg", color = Color.White)
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
        }
    }
}