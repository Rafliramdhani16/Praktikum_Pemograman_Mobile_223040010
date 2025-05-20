package com.example.pertemuan4.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AuthViewModel : ViewModel() {
    private val _currentScreen = MutableStateFlow("login")
    val currentScreen: StateFlow<String> = _currentScreen.asStateFlow()

    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn.asStateFlow()

    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser.asStateFlow()

    // Simple in-memory storage of users
    private val users = mutableListOf<User>()

    fun navigateTo(screen: String) {
        _currentScreen.value = screen
    }

    fun login(username: String, password: String) {
        val user = users.find { it.username == username && it.password == password }
        if (user != null) {
            _currentUser.value = user
            _isLoggedIn.value = true
            navigateTo("notes")
        }
    }

    fun register(name: String, username: String, phone: String, email: String, address: String, password: String) {
        val newUser = User(
            name = name,
            username = username,
            phone = phone,
            email = email,
            address = address,
            password = password
        )
        users.add(newUser)
        navigateTo("login")
    }

    fun logout() {
        _isLoggedIn.value = false
        _currentUser.value = null
        navigateTo("login")
    }
}

data class User(
    val name: String,
    val username: String,
    val phone: String,
    val email: String,
    val address: String,
    val password: String
)
