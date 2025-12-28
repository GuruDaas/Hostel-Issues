package com.hostel.Issues.ui.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.hostel.Issues.api.RetrofitClient
import com.hostel.Issues.models.request.RegisterRequest
import kotlinx.coroutines.launch
import androidx.compose.ui.platform.LocalContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentRegisterScreen(
    navController: NavController,
    onRegisterSuccess: () -> Unit
) {
    val brandGreen = Color(0xFF2E7D32)
    val textMain = Color(0xFF0C1833)
    val textGray = Color(0xFF748091)
    val borderGrey = Color(0xFFE5E7EB)

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var roomNumber by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF7F9FC)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            "Student Register",
            fontWeight = FontWeight.Bold,
            fontSize = 28.sp,
            color = textMain,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            "Open your student portal account",
            fontSize = 15.sp,
            color = textGray,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        OutlinedTextField(
            value = name,
            onValueChange = { name = it; errorMessage = "" },
            label = { Text("Name") },
            placeholder = { Text("Enter name", color = textGray) },
            singleLine = true,
            shape = RoundedCornerShape(15.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = brandGreen,
                unfocusedBorderColor = borderGrey,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp)
        )
        Spacer(Modifier.height(18.dp))
        OutlinedTextField(
            value = email,
            onValueChange = { email = it; errorMessage = "" },
            label = { Text("Email") },
            placeholder = { Text("Enter email", color = textGray) },
            singleLine = true,
            shape = RoundedCornerShape(15.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = brandGreen,
                unfocusedBorderColor = borderGrey,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp)
        )
        Spacer(Modifier.height(18.dp))
        OutlinedTextField(
            value = password,
            onValueChange = { password = it; errorMessage = "" },
            label = { Text("Password") },
            placeholder = { Text("Set password", color = textGray) },
            visualTransformation = PasswordVisualTransformation(),
            singleLine = true,
            shape = RoundedCornerShape(15.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = brandGreen,
                unfocusedBorderColor = borderGrey,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp)
        )
        Spacer(Modifier.height(18.dp))
        OutlinedTextField(
            value = roomNumber,
            onValueChange = { roomNumber = it; errorMessage = "" },
            label = { Text("Room Number") },
            placeholder = { Text("Enter room no.", color = textGray) },
            singleLine = true,
            shape = RoundedCornerShape(15.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = brandGreen,
                unfocusedBorderColor = borderGrey,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp)
        )
        Spacer(Modifier.height(18.dp))
        OutlinedTextField(
            value = phoneNumber,
            onValueChange = { phoneNumber = it; errorMessage = "" },
            label = { Text("Phone Number") },
            placeholder = { Text("Enter phone no.", color = textGray) },
            singleLine = true,
            shape = RoundedCornerShape(15.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = brandGreen,
                unfocusedBorderColor = borderGrey,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp)
        )

        if (errorMessage.isNotEmpty()) {
            Spacer(Modifier.height(8.dp))
            Text(
                errorMessage,
                color = Color(0xFFEF4444),
                fontSize = 13.sp
            )
        }

        Spacer(Modifier.height(32.dp))

        Button(
            onClick = {
                scope.launch {
                    try {
                        val response = RetrofitClient.api.register(
                            RegisterRequest(
                                name = name,
                                email = email,
                                password = password,
                                roomNumber = roomNumber,
                                phoneNumber = phoneNumber
                            )
                        )
                        if (response.isSuccessful && response.body()?.success == true) {
                            errorMessage = ""
                            onRegisterSuccess()
                        } else {
                            errorMessage = response.body()?.message ?: "Registration failed"
                        }
                    } catch (e: Exception) {
                        errorMessage = "Error: ${e.message}"
                    }
                }
            },
            enabled = name.isNotBlank() && email.isNotBlank() && password.isNotBlank() && roomNumber.isNotBlank() && phoneNumber.isNotBlank(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp)
                .height(52.dp),
            shape = RoundedCornerShape(15.dp),
            colors = ButtonDefaults.buttonColors(containerColor = brandGreen)
        ) {
            Text("Register", fontWeight = FontWeight.Bold, fontSize = 17.sp)
        }

        Spacer(Modifier.height(20.dp))
        Text(
            text = "Already have an account? Login",
            color = brandGreen,
            fontWeight = FontWeight.Medium,
            fontSize = 15.sp,
            textDecoration = TextDecoration.Underline,
            modifier = Modifier
                .clickable { navController.navigate("student_login") }
                .padding(vertical = 8.dp)
        )
    }
}
