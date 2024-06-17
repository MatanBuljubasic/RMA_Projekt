package com.example.rma_project.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun InstructionsScreen(onNavigateToMain: () -> Unit){
    Box(modifier = Modifier.fillMaxSize()){
        BackgroundImage(modifier = Modifier)
        Column(modifier = Modifier.fillMaxSize().padding(vertical = 30.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Start) {
                IconButton(onClick = onNavigateToMain) {
                    Icon(imageVector = Icons.Filled.KeyboardArrowLeft, contentDescription = "Back button", tint = Color.White)
                }
                Text(text = "Instructions", color = Color.White)
            }
            Divider(color = Color.White, thickness = 1.dp, modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 5.dp))
            Text(text = "You can change your starting information in settings. Save your current measurements by inputting your weight and fat percentage and pressing 'Save'. Also input your daily intake in cups and press 'Save'. You can see your measurements by day in the 'My Data' screen.", color = Color.White)
        }
    }
}