@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.hostel.Issues.ui.screens.warden

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.hostel.Issues.viewmodel.HostelIssuesViewModel
import com.hostel.Issues.data.model.Issue

data class WardenCategoryUi(
    val title: String,
    val type: String,
    val icon: ImageVector,
    val color: Color
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WardenDashboard(navController: NavController, viewModel: HostelIssuesViewModel) {
    val brandGreen = Color(0xFF2E7D32)
    val wardenEmail = "aman58@gmail.com"
    val wardenPass = "aman58"
    
    val allIssues = viewModel.issues
    val pendingCount = allIssues.count { it.status == "Pending" }
    val inProgressCount = allIssues.count { it.status == "In Progress" }
    val resolvedCount = allIssues.count { it.status == "Resolved" }

    val categoryList = listOf(
        WardenCategoryUi("All Issues", "All", Icons.Outlined.Menu, Color(0xFFE3F2FD)),
        WardenCategoryUi("Electricity", "Electricity", Icons.Outlined.FlashOn, Color(0xFFFFF3E0)),
        WardenCategoryUi("Water", "Water", Icons.Outlined.WaterDrop, Color(0xFFE1F5FE)),
        WardenCategoryUi("Cleanliness", "Cleanliness", Icons.Outlined.CleaningServices, Color(0xFFE8F5E9)),
        WardenCategoryUi("Internet", "Internet", Icons.Outlined.Wifi, Color(0xFFF3E5F5)),
        WardenCategoryUi("Maintenance", "Maintenance", Icons.Outlined.Build, Color(0xFFFFEBEE)),
        WardenCategoryUi("Security", "Security", Icons.Outlined.Security, Color(0xFFFCE4EC)),
        WardenCategoryUi("Mess/Food", "Mess-Food", Icons.Outlined.Restaurant, Color(0xFFE0F7FA)),
        WardenCategoryUi("Other", "Other", Icons.Outlined.ErrorOutline, Color(0xFFF5F5F5))
    )

    var selectedTab by remember { mutableIntStateOf(0) }
    var selectedCategory by remember { mutableStateOf("All") }
    var selectedFilter by remember { mutableStateOf("All") }

    Scaffold(
        containerColor = Color.White,
        bottomBar = {
            NavigationBar(containerColor = Color.White) {
                NavigationBarItem(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    icon = { Icon(imageVector = Icons.Outlined.Home, contentDescription = null) },
                    label = { Text("Home", fontSize = 15.sp, color = Color.Black) }
                )
                NavigationBarItem(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    icon = { Icon(imageVector = Icons.Outlined.Assessment, contentDescription = null) },
                    label = { Text("Issues", fontSize = 15.sp, color = Color.Black) }
                )
                NavigationBarItem(
                    selected = selectedTab == 2,
                    onClick = { selectedTab = 2 },
                    icon = { Icon(imageVector = Icons.Outlined.Person, contentDescription = null) },
                    label = { Text("Profile", fontSize = 15.sp, color = Color.Black) }
                )
            }
        }
    ) { padding ->
        when (selectedTab) {
            0 -> WardenHomeTab(
                pendingCount = pendingCount,
                inProgressCount = inProgressCount,
                resolvedCount = resolvedCount,
                issues = allIssues,
                categoryList = categoryList,
                selectedCategory = selectedCategory,
                onCategorySelect = { selectedCategory = it },
                onTabSelect = { selectedTab = it },
                selectedFilter = selectedFilter,
                onFilterSelect = { selectedFilter = it },
                navController = navController,
                viewModel = viewModel,
                brandGreen = brandGreen,
                modifier = Modifier.padding(padding)
            )
            1 -> WardenIssuesTab(
                issues = allIssues,
                categoryList = categoryList,
                selectedCategory = selectedCategory,
                onCategorySelect = { selectedCategory = it },
                selectedFilter = selectedFilter,
                onFilterSelect = { selectedFilter = it },
                navController = navController,
                viewModel = viewModel,
                brandGreen = brandGreen,
                modifier = Modifier.padding(padding)
            )
            2 -> WardenProfileTab(
                email = wardenEmail,
                pass = wardenPass,
                onLogout = {
                    viewModel.isWardenLoggedIn = false
                    navController.navigate("role_select") { popUpTo(0) { inclusive = true } }
                },
                brandGreen = brandGreen,
                modifier = Modifier.padding(padding)
            )
        }
    }
}

@Composable
private fun WardenHomeTab(
    pendingCount: Int,
    inProgressCount: Int,
    resolvedCount: Int,
    issues: List<Issue>,
    categoryList: List<WardenCategoryUi>,
    selectedCategory: String,
    onCategorySelect: (String) -> Unit,
    onTabSelect: (Int) -> Unit,
    selectedFilter: String,
    onFilterSelect: (String) -> Unit,
    navController: NavController,
    viewModel: HostelIssuesViewModel,
    brandGreen: Color,
    modifier: Modifier = Modifier
) {
    Column(
        modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(Color.White)
            .padding(20.dp)
    ) {
        // Header Section
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                        .background(brandGreen),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.Shield,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(32.dp)
                    )
                }
                Spacer(Modifier.width(15.dp))
                Column {
                    Text("Welcome Warden!", fontSize = 16.sp, color = Color(0xFF757575))
                    Text(
                        "Hostel Management",
                        fontSize = 21.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }
            }
            IconButton(onClick = { }, modifier = Modifier.size(44.dp)) {
                Icon(
                    imageVector = Icons.Outlined.Notifications,
                    contentDescription = null,
                    tint = brandGreen,
                    modifier = Modifier.size(28.dp)
                )
            }
        }

        Spacer(Modifier.height(24.dp))

        // Stats Cards
        Text(
            "Issue Overview",
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Black
        )
        Spacer(Modifier.height(12.dp))
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            WardenStatCard(
                count = pendingCount,
                title = "Pending",
                color = Color(0xFFFF6B35),
                icon = Icons.Outlined.PendingActions,
                modifier = Modifier.weight(1f)
            )
            WardenStatCard(
                count = inProgressCount,
                title = "Progress",
                color = Color(0xFF2196F3),
                icon = Icons.Outlined.HourglassEmpty,
                modifier = Modifier.weight(1f)
            )
            WardenStatCard(
                count = resolvedCount,
                title = "Resolved",
                color = brandGreen,
                icon = Icons.Outlined.CheckCircle,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(Modifier.height(24.dp))

        // Category Cards
        Text(
            "Browse by Category",
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Black
        )
        Spacer(Modifier.height(12.dp))
        LazyRow(horizontalArrangement = Arrangement.spacedBy(14.dp)) {
            items(categoryList) { cat ->
                Card(
                    modifier = Modifier
                        .width(140.dp)
                        .height(110.dp)
                        .clickable { onCategorySelect(cat.type) },
                    colors = CardDefaults.cardColors(
                        containerColor = if (selectedCategory == cat.type) brandGreen else cat.color
                    ),
                    shape = RoundedCornerShape(18.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
                ) {
                    Column(
                        Modifier
                            .fillMaxSize()
                            .padding(14.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = cat.icon,
                            contentDescription = cat.title,
                            modifier = Modifier.size(36.dp),
                            tint = if (selectedCategory == cat.type) Color.White else brandGreen
                        )
                        Spacer(Modifier.height(8.dp))
                        Text(
                            cat.title,
                            color = if (selectedCategory == cat.type) Color.White else Color.Black,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }

        Spacer(Modifier.height(24.dp))

        // Quick Actions
        Text("Quick Actions", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color.Black)
        Spacer(Modifier.height(12.dp))
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            QuickActionCard(
                title = "All Issues",
                icon = Icons.Outlined.Assignment,
                iconColor = brandGreen,
                onClick = { onTabSelect(1) },
                modifier = Modifier.weight(1f)
            )
            QuickActionCard(
                title = "Urgent",
                icon = Icons.Outlined.Warning,
                iconColor = Color(0xFFFF5252),
                onClick = { onFilterSelect("Pending") },
                modifier = Modifier.weight(1f)
            )
            QuickActionCard(
                title = "Reports",
                icon = Icons.Outlined.Assessment,
                iconColor = Color(0xFF2196F3),
                onClick = { /* Generate Report */ },
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(Modifier.height(24.dp))

        // Filter Chips
        Text("Filter by Status", fontWeight = FontWeight.Medium, fontSize = 17.sp, color = Color.Black)
        Spacer(Modifier.height(12.dp))
        LazyRow(horizontalArrangement = Arrangement.spacedBy(11.dp)) {
            items(listOf("All", "Pending", "In Progress", "Resolved")) { filter ->
                FilterChip(
                    selected = filter == selectedFilter,
                    onClick = { onFilterSelect(filter) },
                    label = {
                        Text(
                            filter,
                            fontSize = 15.sp,
                            color = if (filter == selectedFilter) Color.White else Color.Black
                        )
                    },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = brandGreen,
                        selectedLabelColor = Color.White
                    ),
                    shape = RoundedCornerShape(22.dp)
                )
            }
        }

        Spacer(Modifier.height(20.dp))

        // Recent Issues
        Text("Recent Issues", fontWeight = FontWeight.Bold, fontSize = 19.sp, color = Color.Black)
        Spacer(Modifier.height(12.dp))
        
        val filteredIssues = when {
            selectedCategory == "All" && selectedFilter == "All" -> issues
            selectedCategory == "All" -> issues.filter { it.status == selectedFilter }
            selectedFilter == "All" -> issues.filter { it.type == selectedCategory }
            else -> issues.filter { it.type == selectedCategory && it.status == selectedFilter }
        }

        if (filteredIssues.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 40.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        Icons.Outlined.CheckCircle,
                        contentDescription = null,
                        tint = brandGreen,
                        modifier = Modifier.size(64.dp)
                    )
                    Spacer(Modifier.height(16.dp))
                    Text(
                        "No issues found",
                        color = Color.Gray,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        } else {
            filteredIssues.take(5).forEach { issue ->
                WardenIssueCard(
                    issue = issue,
                    onClick = { navController.navigate("warden_issue_detail/${issue.id}") },
                    viewModel = viewModel,
                    brandGreen = brandGreen
                )
                Spacer(Modifier.height(13.dp))
            }
        }
    }
}

@Composable
private fun WardenIssuesTab(
    issues: List<Issue>,
    categoryList: List<WardenCategoryUi>,
    selectedCategory: String,
    onCategorySelect: (String) -> Unit,
    selectedFilter: String,
    onFilterSelect: (String) -> Unit,
    navController: NavController,
    viewModel: HostelIssuesViewModel,
    brandGreen: Color,
    modifier: Modifier = Modifier
) {
    Column(
        modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Header with filters
        Column(
            Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(20.dp)
        ) {
            Text(
                "All Issues",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(Modifier.height(16.dp))
            
            // Category Filter
            LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                items(categoryList) { cat ->
                    FilterChip(
                        selected = selectedCategory == cat.type,
                        onClick = { onCategorySelect(cat.type) },
                        leadingIcon = {
                            Icon(
                                cat.icon,
                                contentDescription = cat.title,
                                modifier = Modifier.size(18.dp)
                            )
                        },
                        label = {
                            Text(
                                cat.title,
                                fontSize = 13.sp,
                                color = if (selectedCategory == cat.type) Color.White else Color.Black
                            )
                        },
                        shape = RoundedCornerShape(20.dp),
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = brandGreen,
                            selectedLabelColor = Color.White
                        )
                    )
                }
            }
            
            Spacer(Modifier.height(12.dp))
            
            // Status Filter
            LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                items(listOf("All", "Pending", "In Progress", "Resolved")) { filter ->
                    FilterChip(
                        selected = filter == selectedFilter,
                        onClick = { onFilterSelect(filter) },
                        label = {
                            Text(
                                filter,
                                fontSize = 13.sp,
                                color = if (filter == selectedFilter) Color.White else Color.Black
                            )
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = brandGreen,
                            selectedLabelColor = Color.White
                        ),
                        shape = RoundedCornerShape(20.dp)
                    )
                }
            }
        }

        Divider(color = Color(0xFFE5E7EB))

        // Issues List
        val filteredIssues = when {
            selectedCategory == "All" && selectedFilter == "All" -> issues
            selectedCategory == "All" -> issues.filter { it.status == selectedFilter }
            selectedFilter == "All" -> issues.filter { it.type == selectedCategory }
            else -> issues.filter { it.type == selectedCategory && it.status == selectedFilter }
        }

        if (filteredIssues.isEmpty()) {
            Box(
                Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        Icons.Outlined.CheckCircle,
                        contentDescription = null,
                        tint = brandGreen,
                        modifier = Modifier.size(64.dp)
                    )
                    Spacer(Modifier.height(16.dp))
                    Text(
                        "No issues found",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Gray
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(filteredIssues) { issue ->
                    WardenIssueCard(
                        issue = issue,
                        onClick = { navController.navigate("warden_issue_detail/${issue.id}") },
                        viewModel = viewModel,
                        brandGreen = brandGreen
                    )
                }
            }
        }
    }
}

@Composable
private fun WardenStatCard(
    count: Int,
    title: String,
    color: Color,
    icon: ImageVector,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.height(120.dp),
        colors = CardDefaults.cardColors(containerColor = color),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(28.dp)
            )
            Column {
                Text(
                    count.toString(),
                    fontSize = 32.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    title,
                    fontSize = 14.sp,
                    color = Color.White.copy(alpha = 0.9f),
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Composable
private fun QuickActionCard(
    title: String,
    icon: ImageVector,
    iconColor: Color,
    onClick: () -> Unit,
    modifier: Modifier
) {
    Card(
        modifier = modifier
            .height(90.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(14.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconColor,
                modifier = Modifier.size(32.dp)
            )
            Spacer(Modifier.height(8.dp))
            Text(
                title,
                color = Color.Black,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
private fun WardenIssueCard(
    issue: Issue,
    onClick: () -> Unit,
    viewModel: HostelIssuesViewModel,
    brandGreen: Color
) {
    val student = viewModel.getStudentById(issue.studentId)
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(15.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
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
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(Modifier.width(12.dp))
                    Column(Modifier.weight(1f)) {
                        Text(
                            issue.title,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.Black,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            issue.type,
                            fontSize = 13.sp,
                            color = Color(0xFF757575)
                        )
                    }
                }
                
                Surface(
                    color = when (issue.status) {
                        "Pending" -> Color(0xFFFF8A65)
                        "In Progress" -> Color(0xFF42A5F5)
                        "Resolved" -> Color(0xFF66BB6A)
                        else -> Color(0xFFE0E0E0)
                    },
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        issue.status,
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                    )
                }
            }
            
            Spacer(Modifier.height(12.dp))
            
            Text(
                issue.description,
                fontSize = 14.sp,
                color = Color(0xFF6B7280),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            
            Spacer(Modifier.height(12.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Outlined.Person,
                        contentDescription = null,
                        tint = Color(0xFF757575),
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(Modifier.width(4.dp))
                    Text(
                        student?.name ?: "Unknown",
                        fontSize = 13.sp,
                        color = Color(0xFF757575)
                    )
                    Spacer(Modifier.width(12.dp))
                    Icon(
                        Icons.Outlined.Room,
                        contentDescription = null,
                        tint = Color(0xFF757575),
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(Modifier.width(4.dp))
                    Text(
                        "Room ${student?.roomNumber}",
                        fontSize = 13.sp,
                        color = Color(0xFF757575)
                    )
                }
                
                Text(
                    issue.createdAt,
                    fontSize = 12.sp,
                    color = Color(0xFF9CA3AF)
                )
            }
        }
    }
}

@Composable
private fun WardenProfileTab(
    email: String,
    pass: String,
    onLogout: () -> Unit,
    brandGreen: Color,
    modifier: Modifier = Modifier
) {
    Column(
        modifier
            .fillMaxSize()
            .background(Color(0xFFF8F9FA))
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(40.dp))
        
        Box(
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .background(brandGreen),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Filled.Shield,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(50.dp)
            )
        }
        
        Spacer(Modifier.height(20.dp))
        
        Text(
            "Warden",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF212121)
        )
        
        Text(
            email,
            fontSize = 14.sp,
            color = brandGreen,
        )
        
        Text(
            "Password: $pass",
            fontSize = 14.sp,
            color = Color(0xFF757575),
        )
        
        Text(
            "Hostel Issue Management System",
            fontSize = 13.sp,
            color = Color(0xFF9CA3AF),
            modifier = Modifier.padding(top = 4.dp)
        )
        
        Spacer(Modifier.height(40.dp))
        
        // Profile Options
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(Modifier.padding(16.dp)) {
                ProfileOption(
                    icon = Icons.Outlined.Settings,
                    title = "Settings",
                    onClick = { }
                )
                Divider(modifier = Modifier.padding(vertical = 12.dp))
                ProfileOption(
                    icon = Icons.Outlined.Help,
                    title = "Help & Support",
                    onClick = { }
                )
                Divider(modifier = Modifier.padding(vertical = 12.dp))
                ProfileOption(
                    icon = Icons.Outlined.Info,
                    title = "About",
                    onClick = { }
                )
            }
        }
        
        Spacer(Modifier.height(24.dp))
        
        Button(
            onClick = onLogout,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF5252)),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
        ) {
            Icon(Icons.Outlined.Logout, contentDescription = null)
            Spacer(Modifier.width(8.dp))
            Text("Logout", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
        }
    }
}

@Composable
private fun ProfileOption(
    icon: ImageVector,
    title: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color(0xFF6B7280),
            modifier = Modifier.size(24.dp)
        )
        Spacer(Modifier.width(16.dp))
        Text(
            title,
            fontSize = 16.sp,
            color = Color.Black,
            modifier = Modifier.weight(1f)
        )
        Icon(
            imageVector = Icons.Outlined.ChevronRight,
            contentDescription = null,
            tint = Color(0xFF9CA3AF),
            modifier = Modifier.size(20.dp)
        )
    }
}