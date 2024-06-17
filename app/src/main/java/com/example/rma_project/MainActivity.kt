package com.example.rma_project

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.runtime.internal.composableLambda
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.rma_project.ui.theme.RMA_ProjectTheme
import com.example.rma_project.view.DataScreen
import com.example.rma_project.view.InstructionsScreen
import com.example.rma_project.view.LoginScreen
import com.example.rma_project.view.MainScreen
import com.example.rma_project.view.RegisterScreen
import com.example.rma_project.view.SettingsScreen
import com.example.rma_project.viewmodel.LoginViewModel
import com.example.rma_project.viewmodel.MeasurementsViewModel
import com.example.rma_project.viewmodel.RegisterViewModel

class MainActivity : ComponentActivity() {
    private val notificationAlarmScheduler by lazy { NotificationAlarmScheduler(this) }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        notificationAlarmScheduler.schedule()
        enableEdgeToEdge()
        setContent {
            RMA_ProjectTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "login_screen"){
                    composable("login_screen") {
                        LoginScreen(viewModel = LoginViewModel(), onNavigateToRegister = { navController.navigate("register_screen") }, onNavigateToMain = { navController.navigate("main_screen") })
                    }
                    composable("register_screen") {
                        RegisterScreen(viewModel = RegisterViewModel(), onNavigateToLogin = { navController.popBackStack() })
                    }
                    composable("main_screen") {
                        MainScreen(measurementsViewModel = MeasurementsViewModel(), onNavigateToInstructions = { navController.navigate("instructions_screen") }, onNavigateToMyData = { navController.navigate("data_screen") }, onNavigateToSettings = { navController.navigate("settings_screen") })
                    }
                    composable("instructions_screen") {
                        InstructionsScreen(onNavigateToMain = { navController.popBackStack() })
                    }
                    composable("settings_screen") {
                        SettingsScreen(measurementsViewModel = MeasurementsViewModel(), onNavigateToMain = {navController.popBackStack()})
                    }
                    composable("data_screen") {
                        DataScreen(onNavigateToMain = {navController.popBackStack()})
                    }
                }
            }
        }
    }
}