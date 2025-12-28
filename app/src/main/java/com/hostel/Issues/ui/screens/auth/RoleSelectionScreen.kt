package com.hostel.Issues.ui.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Shield
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoleSelectionScreen(navController: NavController) {
    val cardShape = RoundedCornerShape(22.dp)
    val iconBg = Color(0xFFE6F3ED)
    val iconColor = Color(0xFF43A047)
    val textMain = Color(0xFF0C1833)
    val textSub = Color(0xFF748091)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF7F9FC)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(Modifier.height(36.dp))
        Text(
            "Hostel Issues System",
            fontWeight = FontWeight.Bold,
            fontSize = 26.sp,
            color = textMain,
        )
        Spacer(Modifier.height(5.dp))
        Text(
            "Select your role to continue",
            color = textSub,
            fontSize = 14.sp
        )
        Spacer(Modifier.height(42.dp))

        // Student Button
        Button(
            onClick = { navController.navigate("student_login") },
            shape = cardShape,
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White,
                contentColor = iconColor
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 23.dp, vertical = 7.dp)
                .height(100.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    Modifier
                        .size(48.dp)
                        .background(iconBg, shape = CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Outlined.Person,
                        contentDescription = "Student Icon",
                        tint = iconColor,
                        modifier = Modifier.size(28.dp)
                    )
                }
                Spacer(Modifier.width(17.dp))
                Column {
                    Text("Student", color = textMain, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Spacer(Modifier.height(2.dp))
                    Text("Raise and track issues", color = textSub, fontSize = 13.sp)
                }
            }
        }

        // Warden Button
        Button(
            onClick = { navController.navigate("warden_login") },
            shape = cardShape,
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White,
                contentColor = iconColor
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 23.dp, vertical = 7.dp)
                .height(100.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    Modifier
                        .size(48.dp)
                        .background(iconBg, shape = CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Outlined.Shield,
                        contentDescription = "Warden Icon",
                        tint = iconColor,
                        modifier = Modifier.size(26.dp)
                    )
                }
                Spacer(Modifier.width(17.dp))
                Column {
                    Text("Warden", color = textMain, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Spacer(Modifier.height(2.dp))
                    Text("Manage student issues", color = textSub, fontSize = 13.sp)
                }
            }
        }
    }
}
