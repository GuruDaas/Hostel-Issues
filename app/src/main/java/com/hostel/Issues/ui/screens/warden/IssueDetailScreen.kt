package com.hostel.Issues.ui.screens.warden

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import com.hostel.Issues.viewmodel.HostelIssuesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WardenIssueDetailScreen(
    navController: NavController,
    viewModel: HostelIssuesViewModel,
    issueId: String
) {
    val brandGreen = Color(0xFF2E7D32)
    val issue = viewModel.getIssueById(issueId)
    val student = issue?.let { viewModel.getStudentById(it.studentId) }

    var selectedStatus by remember { mutableStateOf(issue?.status ?: "Pending") }
    var remarks by remember { mutableStateOf(issue?.wardenRemarks ?: "") }
    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        "Issue Details",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 20.sp
                    ) 
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back", tint = Color.Black)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color.Black
                )
            )
        },
        containerColor = Color(0xFFF8F9FA)
    ) { padding ->
        if (issue == null) {
            Box(
                Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Outlined.ErrorOutline,
                        contentDescription = null,
                        tint = Color.Gray,
                        modifier = Modifier.size(64.dp)
                    )
                    Spacer(Modifier.height(16.dp))
                    Text("Issue not found", color = Color.Gray, fontSize = 16.sp)
                }
            }
        } else {
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .verticalScroll(rememberScrollState())
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Student Info Card
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(18.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(50.dp)
                                .clip(CircleShape)
                                .background(brandGreen),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                student?.name?.first()?.toString() ?: "U",
                                color = Color.White,
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Spacer(Modifier.width(14.dp))
                        Column(Modifier.weight(1f)) {
                            Text(
                                student?.name ?: "Unknown Student",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.Black
                            )
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Outlined.Room,
                                    contentDescription = null,
                                    tint = Color(0xFF757575),
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(Modifier.width(4.dp))
                                Text(
                                    "Room ${student?.roomNumber}, Block ${student?.block}",
                                    fontSize = 14.sp,
                                    color = Color(0xFF757575)
                                )
                            }
                        }
                        Icon(
                            imageVector = Icons.Outlined.ChevronRight,
                            contentDescription = null,
                            tint = Color(0xFF9CA3AF)
                        )
                    }
                }

                // Issue Details Card
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(Modifier.padding(18.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                val icon = when (issue.type) {
                                    "Electricity" -> Icons.Outlined.FlashOn
                                    "Water" -> Icons.Outlined.WaterDrop
                                    "Cleanliness" -> Icons.Outlined.CleaningServices
                                    "Internet" -> Icons.Outlined.Wifi
                                    "Maintenance" -> Icons.Outlined.Build
                                    "Security" -> Icons.Outlined.Security
                                    else -> Icons.Outlined.Assignment
                                }
                                Icon(
                                    imageVector = icon,
                                    contentDescription = null,
                                    tint = brandGreen,
                                    modifier = Modifier.size(28.dp)
                                )
                                Spacer(Modifier.width(12.dp))
                                Column {
                                    Text(
                                        issue.title,
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.Black
                                    )
                                    Text(
                                        issue.type,
                                        fontSize = 14.sp,
                                        color = Color(0xFF757575)
                                    )
                                }
                            }
                        }
                        
                        Spacer(Modifier.height(16.dp))
                        Divider(color = Color(0xFFE5E7EB))
                        Spacer(Modifier.height(16.dp))
                        
                        Text(
                            "Description",
                            fontSize = 13.sp,
                            color = Color(0xFF757575),
                            fontWeight = FontWeight.Medium
                        )
                        Spacer(Modifier.height(6.dp))
                        Text(
                            issue.description,
                            fontSize = 15.sp,
                            color = Color.Black,
                            lineHeight = 22.sp
                        )
                        
                        Spacer(Modifier.height(16.dp))
                        
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            InfoChip(
                                icon = Icons.Outlined.CalendarToday,
                                label = "Created",
                                value = issue.createdAt,
                                modifier = Modifier.weight(1f)
                            )
                            if (issue.resolvedAt != null) {
                                InfoChip(
                                    icon = Icons.Outlined.CheckCircle,
                                    label = "Resolved",
                                    value = issue.resolvedAt,
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }
                    }
                }

                // Status Update Card
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(Modifier.padding(18.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Outlined.Edit,
                                contentDescription = null,
                                tint = brandGreen,
                                modifier = Modifier.size(22.dp)
                            )
                            Spacer(Modifier.width(10.dp))
                            Text(
                                "Update Status",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.Black
                            )
                        }

                        Spacer(Modifier.height(16.dp))

                        // Status Buttons
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            listOf("Pending", "In Progress", "Resolved").forEach { status ->
                                FilterChip(
                                    selected = selectedStatus == status,
                                    onClick = { selectedStatus = status },
                                    label = {
                                        Text(
                                            status,
                                            fontSize = 13.sp,
                                            color = if (selectedStatus == status) Color.White else Color.Black
                                        )
                                    },
                                    leadingIcon = {
                                        val chipIcon = when (status) {
                                            "Pending" -> Icons.Outlined.PendingActions
                                            "In Progress" -> Icons.Outlined.HourglassEmpty
                                            "Resolved" -> Icons.Outlined.CheckCircle
                                            else -> Icons.Outlined.Circle
                                        }
                                        Icon(
                                            imageVector = chipIcon,
                                            contentDescription = null,
                                            modifier = Modifier.size(16.dp)
                                        )
                                    },
                                    colors = FilterChipDefaults.filterChipColors(
                                        selectedContainerColor = when (status) {
                                            "Pending" -> Color(0xFFFF6B35)
                                            "In Progress" -> Color(0xFF2196F3)
                                            "Resolved" -> brandGreen
                                            else -> Color.Gray
                                        },
                                        selectedLabelColor = Color.White
                                    ),
                                    shape = RoundedCornerShape(12.dp)
                                )
                            }
                        }

                        Spacer(Modifier.height(18.dp))

                        // Remarks Input
                        OutlinedTextField(
                            value = remarks,
                            onValueChange = { remarks = it },
                            label = { Text("Warden Remarks (Optional)") },
                            placeholder = { Text("Add your remarks here...", color = Color(0xFF9CA3AF)) },
                            minLines = 4,
                            maxLines = 6,
                            shape = RoundedCornerShape(14.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = brandGreen,
                                unfocusedBorderColor = Color(0xFFE5E7EB),
                                focusedLabelColor = brandGreen,
                                focusedTextColor = Color.Black,
                                unfocusedTextColor = Color.Black
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(Modifier.height(18.dp))

                        // Update Button
                        Button(
                            onClick = { showDialog = true },
                            shape = RoundedCornerShape(14.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = brandGreen),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(52.dp)
                        ) {
                            Icon(imageVector = Icons.Outlined.Send, contentDescription = null)
                            Spacer(Modifier.width(8.dp))
                            Text(
                                "Update Issue",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }
        }
    }

    // Confirmation Dialog
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            icon = {
                Icon(
                    imageVector = Icons.Outlined.Info,
                    contentDescription = null,
                    tint = brandGreen,
                    modifier = Modifier.size(28.dp)
                )
            },
            title = { 
                Text(
                    "Confirm Update",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                ) 
            },
            text = { 
                Text(
                    "Are you sure you want to update this issue status to \"$selectedStatus\"?",
                    fontSize = 15.sp,
                    color = Color(0xFF6B7280)
                ) 
            },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.updateIssueStatus(issueId, selectedStatus, remarks.ifBlank { null })
                        showDialog = false
                        navController.navigateUp()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = brandGreen),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text("Confirm", fontWeight = FontWeight.SemiBold)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showDialog = false },
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text("Cancel", color = Color(0xFF6B7280), fontWeight = FontWeight.Medium)
                }
            },
            shape = RoundedCornerShape(20.dp)
        )
    }
}

@Composable
private fun InfoChip(
    icon: ImageVector,
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        color = Color(0xFFF3F4F6),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color(0xFF6B7280),
                modifier = Modifier.size(18.dp)
            )
            Spacer(Modifier.width(8.dp))
            Column {
                Text(
                    label,
                    fontSize = 11.sp,
                    color = Color(0xFF9CA3AF),
                    fontWeight = FontWeight.Medium
                )
                Text(
                    value,
                    fontSize = 13.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}