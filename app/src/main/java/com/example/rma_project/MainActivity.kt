package com.example.rma_project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.rma_project.ui.theme.RMA_ProjectTheme
import com.example.rma_project.view.LoginScreen
import com.example.rma_project.view.MainScreen
import com.example.rma_project.view.RegisterScreen
import com.example.rma_project.viewmodel.LoginViewModel
import com.example.rma_project.viewmodel.MeasurementsViewModel
import com.example.rma_project.viewmodel.RegisterViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
                        MainScreen(measurementsViewModel = MeasurementsViewModel(), navController = navController)
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    RMA_ProjectTheme {
        Greeting("Android")
    }
}