package com.example.pertemuan4

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pertemuan4.ui.theme.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Pertemuan4Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AppNavigation(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun AppNavigation(modifier: Modifier = Modifier) {
    var currentScreen by remember { mutableStateOf("login") }
    var currentUser by remember { mutableStateOf("") }

    when (currentScreen) {
        "login" -> FormLogin(
            onLoginSuccess = { username ->
                currentUser = username
                currentScreen = "home"
            },
            onNavigateToRegister = {
                currentScreen = "register"
            },
            modifier = modifier
        )
        "register" -> FormRegister(
            onRegisterSuccess = { name ->
                currentUser = name
                currentScreen = "home"
            },
            onNavigateToLogin = {
                currentScreen = "login"
            },
            modifier = modifier
        )
        "home" -> HomeScreen(
            userName = currentUser,
            onLogout = {
                currentScreen = "login"
                // Reset current user when logging out
                currentUser = ""
            },
            modifier = modifier
        )
    }
}

@Composable
fun FormLogin(
    onLoginSuccess: (String) -> Unit,
    onNavigateToRegister: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val username = remember { mutableStateOf(TextFieldValue("")) }
    val password = remember { mutableStateOf(TextFieldValue("")) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        DarkBackground,
                        DarkSurface
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 32.dp)
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Selamat Datang Kembali",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = "Silahkan masuk ke akun Anda",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                colors = CardDefaults.cardColors(containerColor = DarkSurface),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp)
                ) {
                    OutlinedTextField(
                        value = username.value,
                        onValueChange = { username.value = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = DarkPrimary,
                            unfocusedBorderColor = BlueGrey40,
                            focusedTextColor = DarkText,
                            unfocusedTextColor = DarkText,
                            cursorColor = DarkPrimary
                        ),
                        singleLine = true,
                        label = { Text("Username", color = DarkSubText) },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = "Username",
                                tint = DarkPrimary
                            )
                        }
                    )

                    OutlinedTextField(
                        value = password.value,
                        onValueChange = { password.value = it },
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 24.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = DarkPrimary,
                            unfocusedBorderColor = BlueGrey40,
                            focusedTextColor = DarkText,
                            unfocusedTextColor = DarkText,
                            cursorColor = DarkPrimary
                        ),
                        singleLine = true,
                        label = { Text("Password", color = DarkSubText) },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Lock,
                                contentDescription = "Password",
                                tint = DarkPrimary
                            )
                        }
                    )

                    Button(
                        onClick = {
                            if(username.value.text.isNotEmpty() && password.value.text.isNotEmpty()) {
                                if(username.value.text == "admin" && password.value.text == "admin") {
                                    Toast.makeText(context, "Login Sukses", Toast.LENGTH_SHORT).show()
                                    onLoginSuccess(username.value.text)
                                } else {
                                    Toast.makeText(context, "Login Gagal", Toast.LENGTH_SHORT).show()
                                }
                            } else {
                                Toast.makeText(context, "Username dan password harus diisi", Toast.LENGTH_SHORT).show()
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = DarkPrimary
                        ),
                        elevation = ButtonDefaults.buttonElevation(
                            defaultElevation = 4.dp
                        )
                    ) {
                        Text(
                            text = "LOGIN",
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = DarkText
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    TextButton(
                        onClick = {
                            username.value = TextFieldValue("");
                            password.value = TextFieldValue("")
                        },
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    ) {
                        Text(
                            text = "Reset",
                            style = TextStyle(
                                fontSize = 14.sp,
                                color = DarkSecondary
                            )
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Belum punya akun?",
                    style = TextStyle(
                        fontSize = 14.sp,
                        color = DarkSubText
                    )
                )

                TextButton(
                    onClick = onNavigateToRegister
                ) {
                    Text(
                        text = "Daftar disini",
                        style = TextStyle(
                            fontSize = 14.sp,
                            color = DarkPrimary,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun FormRegister(
    onRegisterSuccess: (String) -> Unit,
    onNavigateToLogin: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    val nama = remember { mutableStateOf(TextFieldValue("")) }
    val username = remember { mutableStateOf(TextFieldValue("")) }
    val nomorTelepon = remember { mutableStateOf(TextFieldValue("")) }
    val email = remember { mutableStateOf(TextFieldValue("")) }
    val alamat = remember { mutableStateOf(TextFieldValue("")) }
    val password = remember { mutableStateOf(TextFieldValue("")) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        DarkBackground,
                        DarkSurface
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(scrollState)
                .padding(horizontal = 24.dp, vertical = 32.dp)
        ) {
            // Top logo/icon placeholder

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Buat Akun Baru",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 8.dp).align(Alignment.CenterHorizontally),
                textAlign = TextAlign.Center
            )

            Text(
                text = "Lengkapi data diri Anda",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 24.dp).align(Alignment.CenterHorizontally),
                textAlign = TextAlign.Center
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                colors = CardDefaults.cardColors(containerColor = DarkSurface),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp)
                ) {
                    OutlinedTextField(
                        value = nama.value,
                        onValueChange = { nama.value = it },
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.Words,
                            keyboardType = KeyboardType.Text
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = DarkPrimary,
                            unfocusedBorderColor = BlueGrey40,
                            focusedTextColor = DarkText,
                            unfocusedTextColor = DarkText,
                            cursorColor = DarkPrimary
                        ),
                        singleLine = true,
                        label = { Text("Nama Lengkap", color = DarkSubText) },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = "Nama",
                                tint = DarkPrimary
                            )
                        }
                    )

                    OutlinedTextField(
                        value = username.value,
                        onValueChange = { username.value = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = DarkPrimary,
                            unfocusedBorderColor = BlueGrey40,
                            focusedTextColor = DarkText,
                            unfocusedTextColor = DarkText,
                            cursorColor = DarkPrimary
                        ),
                        singleLine = true,
                        label = { Text("Username", color = DarkSubText) },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = "Username",
                                tint = DarkPrimary
                            )
                        }
                    )

                    OutlinedTextField(
                        value = nomorTelepon.value,
                        onValueChange = { nomorTelepon.value = it },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = DarkPrimary,
                            unfocusedBorderColor = BlueGrey40,
                            focusedTextColor = DarkText,
                            unfocusedTextColor = DarkText,
                            cursorColor = DarkPrimary
                        ),
                        singleLine = true,
                        label = { Text("Nomor Telepon", color = DarkSubText) },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Phone,
                                contentDescription = "Nomor Telepon",
                                tint = DarkPrimary
                            )
                        }
                    )

                    OutlinedTextField(
                        value = email.value,
                        onValueChange = { email.value = it },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = DarkPrimary,
                            unfocusedBorderColor = BlueGrey40,
                            focusedTextColor = DarkText,
                            unfocusedTextColor = DarkText,
                            cursorColor = DarkPrimary
                        ),
                        singleLine = true,
                        label = { Text("Email", color = DarkSubText) },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Email,
                                contentDescription = "Email",
                                tint = DarkPrimary
                            )
                        }
                    )

                    OutlinedTextField(
                        value = alamat.value,
                        onValueChange = { alamat.value = it },
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.Sentences,
                            keyboardType = KeyboardType.Text
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp)
                            .height(100.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = DarkPrimary,
                            unfocusedBorderColor = BlueGrey40,
                            focusedTextColor = DarkText,
                            unfocusedTextColor = DarkText,
                            cursorColor = DarkPrimary
                        ),
                        maxLines = 3,
                        label = { Text("Alamat", color = DarkSubText) }
                    )

                    OutlinedTextField(
                        value = password.value,
                        onValueChange = { password.value = it },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        visualTransformation = PasswordVisualTransformation(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 24.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = DarkPrimary,
                            unfocusedBorderColor = BlueGrey40,
                            focusedTextColor = DarkText,
                            unfocusedTextColor = DarkText,
                            cursorColor = DarkPrimary
                        ),
                        singleLine = true,
                        label = { Text("Password", color = DarkSubText) },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Lock,
                                contentDescription = "Password",
                                tint = DarkPrimary
                            )
                        }
                    )

                    // Button row with equal width buttons
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Button(
                            onClick = {
                                if (
                                    nama.value.text.isNotEmpty() &&
                                    username.value.text.isNotEmpty() &&
                                    nomorTelepon.value.text.isNotEmpty() &&
                                    email.value.text.isNotEmpty() &&
                                    alamat.value.text.isNotEmpty() &&
                                    password.value.text.isNotEmpty()
                                ) {
                                    Toast.makeText(
                                        context,
                                        "Halo, ${nama.value.text}! Registrasi berhasil.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    onRegisterSuccess(nama.value.text)
                                } else {
                                    Toast.makeText(
                                        context,
                                        "Semua inputan harus diisi",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            },
                            modifier = Modifier
                                .weight(1f)
                                .height(56.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = DarkPrimary
                            ),
                            elevation = ButtonDefaults.buttonElevation(
                                defaultElevation = 4.dp
                            )
                        ) {
                            Text(
                                text = "SIMPAN",
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = DarkText
                                )
                            )
                        }

                        Button(
                            onClick = {
                                nama.value = TextFieldValue("")
                                username.value = TextFieldValue("")
                                nomorTelepon.value = TextFieldValue("")
                                email.value = TextFieldValue("")
                                alamat.value = TextFieldValue("")
                                password.value = TextFieldValue("")
                            },
                            modifier = Modifier
                                .weight(1f)
                                .height(56.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = BlueGrey40
                            ),
                            elevation = ButtonDefaults.buttonElevation(
                                defaultElevation = 4.dp
                            )
                        ) {
                            Text(
                                text = "RESET",
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = DarkText
                                )
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Sudah punya akun?",
                    style = TextStyle(
                        fontSize = 14.sp,
                        color = DarkSubText
                    )
                )

                TextButton(
                    onClick = onNavigateToLogin
                ) {
                    Text(
                        text = "Masuk disini",
                        style = TextStyle(
                            fontSize = 14.sp,
                            color = DarkPrimary,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun HomeScreen(
    userName: String,
    onLogout: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        DarkBackground,
                        DarkSurface
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Avatar circle

            Spacer(modifier = Modifier.height(32.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                colors = CardDefaults.cardColors(containerColor = DarkSurface),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Selamat Datang",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    Text(
                        text = userName,
                        style = TextStyle(
                            fontSize = 28.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = DarkPrimary
                        ),
                        modifier = Modifier.padding(bottom = 24.dp)
                    )

                    Text(
                        text = "Kamu sudah berhasil login ke aplikasi.",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(bottom = 32.dp),
                        textAlign = TextAlign.Center
                    )

                    Button(
                        onClick = {
                            Toast.makeText(context, "Logout berhasil", Toast.LENGTH_SHORT).show()
                            onLogout()
                        },
                        modifier = Modifier
                            .width(200.dp)
                            .height(56.dp),
                        shape = RoundedCornerShape(28.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = DarkPrimary
                        ),
                        elevation = ButtonDefaults.buttonElevation(
                            defaultElevation = 4.dp
                        )
                    ) {
                        Text(
                            text = "LOGOUT",
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = DarkText
                            )
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    Pertemuan4Theme {
        FormLogin(
            onLoginSuccess = {},
            onNavigateToRegister = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterPreview() {
    Pertemuan4Theme {
        FormRegister(
            onRegisterSuccess = {},
            onNavigateToLogin = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    Pertemuan4Theme {
        HomeScreen(
            userName = "Admin",
            onLogout = {}
        )
    }
}