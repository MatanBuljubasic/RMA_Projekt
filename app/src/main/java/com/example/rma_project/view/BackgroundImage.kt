package com.example.rma_project.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.rma_project.R

@Composable
fun BackgroundImage(modifier: Modifier){

    Box(modifier.background(Color(132, 62, 62))
        .fillMaxSize()) {
        Image(
            painter = painterResource(R.drawable.background),
            modifier = Modifier.fillMaxSize(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            alpha = 0.1F,
        )
    }
}