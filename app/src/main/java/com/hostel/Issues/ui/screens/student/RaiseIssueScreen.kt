package com.hostel.Issues.ui.screens.student

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RaiseIssueScreen(
    issueType: String = "General",
    onSubmit: (title: String, description: String, location: String, urgency: String, timePref: String) -> Unit = { _, _, _, _, _ -> }
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var urgency by remember { mutableStateOf("Normal") }
    var timePref by remember { mutableStateOf("Anytime") }
    var locationExpanded by remember { mutableStateOf(false) }
    var timeExpanded by remember { mutableStateOf(false) }

    val isSubmitEnabled = title.isNotBlank() && description.isNotBlank() && location.isNotBlank() && urgency.isNotBlank()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // SCROLLABLE column filling available space (avoid centering content vertically)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 18.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // IssueType badge at top
            // show a friendly badge for the issue type (replace dashes/encoded slashes with '/')
            val displayType = issueType.replace("%2F", "/").replace('-', '/')
            Surface(
                color = Color(0xFF008AFF),
                shape = RoundedCornerShape(22.dp),
                modifier = Modifier.padding(bottom = 18.dp)
            ) {
                Text(
                    text = displayType.uppercase(),
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(horizontal = 25.dp, vertical = 13.dp)
                )
            }

            Text(
                "Report New Issue",
                color = Color.Black,
                fontSize = 23.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(bottom = 20.dp)
                    .align(Alignment.CenterHorizontally)
            )

            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                placeholder = { Text("Title (Eg: Fan not working)", color = Color(0xFF888888)) },
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, RoundedCornerShape(20.dp)),
                singleLine = true,
                textStyle = androidx.compose.ui.text.TextStyle(color = Color.Black),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedBorderColor = Color(0xFF008AFF),
                    unfocusedBorderColor = Color(0xFFB0BEC5),
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black
                )
            )

            Spacer(Modifier.height(20.dp))

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                placeholder = { Text("Description (Describe the problem)", color = Color(0xFF888888)) },
                maxLines = 5,
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(110.dp)
                    .background(Color.White, RoundedCornerShape(20.dp)),
                textStyle = androidx.compose.ui.text.TextStyle(color = Color.Black),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedBorderColor = Color(0xFF008AFF),
                    unfocusedBorderColor = Color(0xFFB0BEC5),
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black
                )
            )

            Spacer(Modifier.height(20.dp))

            ExposedDropdownMenuBox(
                expanded = locationExpanded,
                onExpandedChange = { locationExpanded = !locationExpanded }
            ) {
                OutlinedTextField(
                    readOnly = true,
                    value = location,
                    onValueChange = {},
                    placeholder = { Text("Location (Room, Mess, etc.)", color = Color(0xFF888888)) },
                    shape = RoundedCornerShape(20.dp),
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = locationExpanded) },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                        .background(Color.White, RoundedCornerShape(20.dp)),
                    textStyle = androidx.compose.ui.text.TextStyle(color = Color.Black),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedBorderColor = Color(0xFF008AFF),
                        unfocusedBorderColor = Color(0xFFB0BEC5),
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black
                    )
                )
                ExposedDropdownMenu(
                    expanded = locationExpanded,
                    onDismissRequest = { locationExpanded = false }
                ) {
                    listOf("Room", "Mess", "Block", "Washroom", "Hostel Ground", "Other").forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option) },
                            onClick = {
                                location = option
                                locationExpanded = false
                            }
                        )
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(18.dp), modifier = Modifier.padding(vertical = 4.dp)) {
                listOf("Normal", "Urgent").forEach { level ->
                    FilterChip(
                        selected = urgency == level,
                        onClick = { urgency = level },
                        label = { Text(level, color = Color.Black) },
                        shape = RoundedCornerShape(18.dp)
                    )
                }
            }

            Spacer(Modifier.height(12.dp))

            ExposedDropdownMenuBox(
                expanded = timeExpanded,
                onExpandedChange = { timeExpanded = !timeExpanded }
            ) {
                OutlinedTextField(
                    readOnly = true,
                    value = timePref,
                    onValueChange = {},
                    placeholder = { Text("Preferred Response Time (Anytime)", color = Color(0xFF888888)) },
                    shape = RoundedCornerShape(20.dp),
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = timeExpanded) },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                        .background(Color.White, RoundedCornerShape(20.dp)),
                    textStyle = androidx.compose.ui.text.TextStyle(color = Color.Black),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedBorderColor = Color(0xFF008AFF),
                        unfocusedBorderColor = Color(0xFFB0BEC5),
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black
                    )
                )
                ExposedDropdownMenu(
                    expanded = timeExpanded,
                    onDismissRequest = { timeExpanded = false }
                ) {
                    listOf("Anytime", "Morning (8-12)", "Afternoon (12-6)", "Evening (6-10)").forEach { t ->
                        DropdownMenuItem(
                            text = { Text(t) },
                            onClick = {
                                timePref = t
                                timeExpanded = false
                            }
                        )
                    }
                }
            }

            Spacer(Modifier.height(26.dp))
            Button(
                onClick = { onSubmit(title, description, location, urgency, timePref) },
                enabled = isSubmitEnabled,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(18.dp)
            ) {
                Icon(Icons.Outlined.Send, null)
                Spacer(Modifier.width(8.dp))
                Text("Submit", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }
        }
    }
}

