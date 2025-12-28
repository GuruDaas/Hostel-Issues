package com.hostel.Issues.ui.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.hostel.Issues.viewmodel.HostelIssuesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WardenLoginScreen(
    navController: NavController,
    viewModel: HostelIssuesViewModel
) {
    val brandGreen = Color(0xFF2E7D32)
    val textMain = Color(0xFF0C1833)
    val textGray = Color(0xFF748091)
    val borderGrey = Color(0xFFE5E7EB)

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF7F9FC)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            "Warden Login",
            fontWeight = FontWeight.Bold,
            fontSize = 28.sp,
            color = textMain,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            "Login to Warden Portal",
            fontSize = 15.sp,
            color = textGray,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        OutlinedTextField(
            value = username,
            onValueChange = { username = it; errorMessage = "" },
            label = { Text("Username") },
            placeholder = { Text("Enter username", color = textGray) },
            leadingIcon = {
                Icon(Icons.Outlined.Person, null, tint = brandGreen)
            },
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
            placeholder = { Text("Enter password", color = textGray) },
            leadingIcon = {
                Icon(Icons.Outlined.Lock, null, tint = brandGreen)
            },
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
                if (viewModel.wardenLogin(username, password)) {
                    navController.navigate("warden_dashboard") {
                        popUpTo("warden_login") { inclusive = true }
                    }
                } else {
                    errorMessage = "Invalid credentials"
                }
            },
            enabled = username.isNotBlank() && password.isNotBlank(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp)
                .height(52.dp),
            shape = RoundedCornerShape(15.dp),
            colors = ButtonDefaults.buttonColors(containerColor = brandGreen)
        ) {
            Text("Login", fontWeight = FontWeight.Bold, fontSize = 17.sp)
        }
    }
}
