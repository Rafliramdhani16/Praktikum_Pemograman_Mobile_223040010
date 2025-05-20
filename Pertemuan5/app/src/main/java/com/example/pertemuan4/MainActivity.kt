package com.example.pertemuan4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pertemuan4.ui.screen.LoginScreen
import com.example.pertemuan4.ui.screen.NoteScreen
import com.example.pertemuan4.ui.screen.RegisterScreen
import com.example.pertemuan4.ui.theme.Pertemuan4Theme
import com.example.pertemuan4.viewmodel.AuthViewModel
import com.example.pertemuan4.viewmodel.NoteViewModel
import kotlinx.coroutines.flow.StateFlow

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Pertemuan4Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold { innerPadding ->
                        // Use the MVVM architecture with ViewModels
                        AppNavigationMVVM(modifier = Modifier.padding(innerPadding))
                    }
                }
            }
        }
    }
}

@Composable
fun AppNavigationMVVM(
    authViewModel: AuthViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val currentScreen by authViewModel.currentScreen.collectAsState()

    when (currentScreen) {
        "login" -> LoginScreen(
            onLogin = { username, password -> 
                authViewModel.login(username, password)
            },
            onNavigateToRegister = {
                authViewModel.navigateTo("register")
            },
            modifier = modifier
        )
        
        "register" -> RegisterScreen(
            onRegister = { name, username, phone, email, address, password ->
                authViewModel.register(name, username, phone, email, address, password)
            },
            onNavigateToLogin = {
                authViewModel.navigateTo("login")
            },
            modifier = modifier
        )
        
        "notes" -> {
            val noteViewModel: NoteViewModel = viewModel()
            NoteScreen(
                viewModel = noteViewModel,
                authViewModel = authViewModel,
                modifier = modifier
            )
        }
        
        else -> LoginScreen(
            onLogin = { username, password -> 
                authViewModel.login(username, password)
            },
            onNavigateToRegister = {
                authViewModel.navigateTo("register")
            },
            modifier = modifier
        )
    }
}
