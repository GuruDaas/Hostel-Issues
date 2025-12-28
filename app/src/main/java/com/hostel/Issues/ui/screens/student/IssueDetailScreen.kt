package com.hostel.Issues.ui.screens.student

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.hostel.Issues.viewmodel.HostelIssuesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentIssueDetailScreen(
    navController: NavController,
    viewModel: HostelIssuesViewModel,
    issueId: String
) {
    val issue = viewModel.getIssueById(issueId)
    if (issue == null) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Issue not found.", color = Color.Red)
        }
        return
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Issue Details") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Outlined.ArrowBack, "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            Text(issue.title, fontSize = 22.sp, fontWeight = FontWeight.Bold)
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = when {
                        issue.type.equals("Electricity", true) -> Icons.Outlined.FlashOn
                        issue.type.equals("Water", true) -> Icons.Outlined.WaterDrop
                        issue.type.equals("Cleanliness", true) -> Icons.Outlined.CleaningServices
                        issue.type.equals("Internet", true) -> Icons.Outlined.Wifi
                        issue.type.equals("Maintenance", true) -> Icons.Outlined.Build
                        issue.type.equals("Security", true) -> Icons.Outlined.Security
                        // accept multiple variants for mess/food
                        issue.type.equals("Mess/Food", true) || issue.type.equals("Mess-Food", true) || issue.type.contains("mess", true) || issue.type.contains("food", true) -> Icons.Outlined.Restaurant
                        else -> Icons.Outlined.Assignment
                    }, contentDescription = null, tint = Color(0xFF2962FF)
                )
                Spacer(Modifier.width(8.dp))
                Text(issue.type, fontSize = 15.sp, color = Color(0xFF757575))
            }
            Divider(color = Color(0xFFE0E0E0))
            Text("Description", fontWeight = FontWeight.Medium)
            Text(issue.description)
            Divider(color = Color(0xFFE0E0E0))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Outlined.AccessTime, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(18.dp))
                Spacer(Modifier.width(4.dp))
                Text(issue.createdAt, fontSize = 13.sp, color = Color.Gray)
            }
            IssueStatusBadge(issue.status)
        }
    }
}

@Composable
fun IssueStatusBadge(status: String) {
    val (bg, txt) = when (status) {
        "Pending" -> Color(0xFFFFF3E0) to Color(0xFFF57C00)
        "In Progress" -> Color(0xFFE3F2FD) to Color(0xFF1976D2)
        "Resolved" -> Color(0xFFE8F5E9) to Color(0xFF388E3C)
        else -> Color(0xFFF5F5F5) to Color(0xFF616161)
    }
    Surface(
        color = bg,
        shape = RoundedCornerShape(6.dp)
    ) {
        Text(
            status,
            color = txt,
            fontWeight = FontWeight.Bold,
            fontSize = 13.sp,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
        )
    }
}
