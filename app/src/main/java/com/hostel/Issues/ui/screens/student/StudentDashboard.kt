package com.hostel.Issues.ui.screens.student

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.hostel.Issues.viewmodel.HostelIssuesViewModel
import com.hostel.Issues.data.model.Student
import com.hostel.Issues.data.model.Issue
import com.hostel.Issues.ui.theme.brandGreen
import kotlinx.coroutines.launch

data class IssueCategoryUi(
    val title: String,
    val type: String,
    val icon: ImageVector,
    val color: Color
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentDashboard(navController: NavController, viewModel: HostelIssuesViewModel) {
    val student = viewModel.loggedInStudent
    val allIssues = viewModel.issues.filter { it.studentId == student?.id }
    val openCount = allIssues.count { it.status == "Pending" }
    val inProgressCount = allIssues.count { it.status == "In Progress" }
    val resolvedCount = allIssues.count { it.status == "Resolved" }

    val categoryList = listOf(
        IssueCategoryUi("Electricity", "Electricity", Icons.Outlined.FlashOn, Color(0xFFFFF3E0)),
        IssueCategoryUi("Water", "Water", Icons.Outlined.WaterDrop, Color(0xFFE1F5FE)),
        IssueCategoryUi("Cleanliness", "Cleanliness", Icons.Outlined.CleaningServices, Color(0xFFE8F5E9)),
        IssueCategoryUi("Internet", "Internet", Icons.Outlined.Wifi, Color(0xFFF3E5F5)),
        IssueCategoryUi("Maintenance", "Maintenance", Icons.Outlined.Build, Color(0xFFFFEBEE)),
        IssueCategoryUi("Security", "Security", Icons.Outlined.Security, Color(0xFFFCE4EC)),
    // use a safe type string (no '/') to avoid navigation path issues
    IssueCategoryUi("Mess/Food", "Mess-Food", Icons.Outlined.Restaurant, Color(0xFFE0F7FA)),
        IssueCategoryUi("Other", "Other", Icons.Outlined.ErrorOutline, Color(0xFFF5F5F5))
    )
    var selectedTab by remember { mutableIntStateOf(0) }
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
                    icon = { Icon(imageVector = Icons.Outlined.AddCircle, contentDescription = null) },
                    label = { Text("Create", fontSize = 15.sp, color = Color.Black) }
                )
                NavigationBarItem(
                    selected = selectedTab == 2,
                    onClick = { selectedTab = 2 },
                    icon = { Icon(imageVector = Icons.Outlined.AccountCircle, contentDescription = null) },
                    label = { Text("Profile", fontSize = 15.sp, color = Color.Black) }
                )
            }
        }
    ) { padding ->
        when (selectedTab) {
            0 -> HomeTabLarge(
                student = student,
                openCount = openCount,
                inProgressCount = inProgressCount,
                resolvedCount = resolvedCount,
                issues = allIssues,
                selectedFilter = selectedFilter,
                onSelectFilter = { filter -> selectedFilter = filter },
                navController = navController,
                categoryList = categoryList,
                viewModel = viewModel,
                modifier = Modifier.padding(padding)
            )
            1 -> CreateIssueTabEnhanced(
                navController, categoryList, Modifier.padding(padding)
            )
            2 -> ProfileTab(
                student = student,
                onLogout = {
                    viewModel.logout()
                    navController.navigate("role_select") { popUpTo(0) { inclusive = true } }
                },
                modifier = Modifier.padding(padding)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeTabLarge(
    student: Student?,
    openCount: Int,
    inProgressCount: Int,
    resolvedCount: Int,
    issues: List<Issue>,
    selectedFilter: String,
    onSelectFilter: (String) -> Unit,
    navController: NavController,
    viewModel: HostelIssuesViewModel,
    categoryList: List<IssueCategoryUi>,
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberScrollState()
    Column(
        modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .background(Color.White)
            .padding(20.dp)
    ) {
        // Header section
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
                    Text(
                        student?.name?.first()?.toString() ?: "U",
                        color = Color.White,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(Modifier.width(15.dp))
                Column {
                    Text("Welcome Back!", fontSize = 16.sp, color = Color(0xFF757575))
                    Text(
                        student?.name ?: "User",
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

        Spacer(Modifier.height(18.dp))

        // Category Cards
        Text(
            "Raise a new Issue",
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Black
        )
        LazyRow(horizontalArrangement = Arrangement.spacedBy(14.dp)) {
            items(categoryList) { cat ->
                AnimatedVisibility(
                    visible = true,
                    enter = fadeIn(animationSpec = tween(600)) + slideInVertically(
                        initialOffsetY = { it / 2 },
                        animationSpec = tween(600)
                    )
                ) {
                    Card(
                        modifier = Modifier
                            .width(160.dp)
                            .height(120.dp)
                            .clickable { navController.navigate("raise_issue/${android.net.Uri.encode(cat.type)}") },
                        colors = CardDefaults.cardColors(containerColor = cat.color),
                        shape = RoundedCornerShape(18.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
                    ) {
                        Column(
                            Modifier.padding(18.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = cat.icon,
                                contentDescription = cat.title,
                                modifier = Modifier.size(40.dp),
                                tint = brandGreen
                            )
                            Spacer(Modifier.height(10.dp))
                            Text(
                                cat.title,
                                color = Color.Black,
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            )
                        }
                    }
                }
            }
        }

        Spacer(Modifier.height(24.dp))

        // Status Cards
        Row(
            Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            HomeStatCardLarge(openCount, "Open", Color(0xFF1565C0), Modifier.weight(1f))
            HomeStatCardLarge(inProgressCount, "Progress", Color(0xFF00BFA5), Modifier.weight(1f))
            HomeStatCardLarge(resolvedCount, "Resolved", Color(0xFFD32F2F), Modifier.weight(1f))
        }

        Spacer(Modifier.height(22.dp))

        // Quick Actions
        Text("Quick Actions", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color.Black)
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(18.dp)) {
            QuickActionCardLarge("New Issue", Icons.Outlined.AddCircle, brandGreen, {
                navController.navigate("raise_issue/Electricity")
            }, Modifier.weight(1f))
            QuickActionCardLarge("My Issues", Icons.Outlined.Assignment, Color(0xFF00BFA5), {
                coroutineScope.launch { scrollState.animateScrollTo(scrollState.maxValue) }
            }, Modifier.weight(1f))
            QuickActionCardLarge(
                "Emergency",
                Icons.Outlined.Warning,
                Color(0xFFFF5252),
                { /* Emergency Action */ },
                Modifier.weight(1f)
            )
        }

        Spacer(Modifier.height(24.dp))

        // Filter Chips
        Text("Filter by Category", fontWeight = FontWeight.Medium, fontSize = 17.sp, color = Color.Black)
        LazyRow(horizontalArrangement = Arrangement.spacedBy(11.dp)) {
            items(listOf("All") + categoryList.map { it.title }) { filter ->
                FilterChip(
                    selected = filter == selectedFilter,
                    onClick = { onSelectFilter(filter) },
                    label = { Text(filter, fontSize = 15.sp, color = if (filter == selectedFilter) Color.White else Color.Black) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = brandGreen,
                        selectedLabelColor = Color.White
                    ),
                    shape = RoundedCornerShape(22.dp)
                )
            }
        }

        Spacer(Modifier.height(16.dp))

        // Recent Issues
        Text("Recent Issues", fontWeight = FontWeight.Bold, fontSize = 19.sp, color = Color.Black)
        val filterList = if (selectedFilter == "All") issues else issues.filter { issue -> issue.type == selectedFilter }
        if (filterList.isEmpty()) {
            Text(
                "No issues found.",
                color = Color.Gray,
                fontSize = 15.sp,
                modifier = Modifier
                    .padding(vertical = 30.dp)
                    .align(Alignment.CenterHorizontally)
            )
        } else {
            filterList.take(5).forEach { issue ->
                IssueCardLarge(
                    issue = issue,
                    onClick = { navController.navigate("student_issue_detail/${issue.id}") },
                    viewModel = viewModel
                )
                Spacer(Modifier.height(13.dp))
            }
        }
    }
}

@Composable
private fun HomeStatCardLarge(count: Int, title: String, color: Color, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.height(100.dp),
        colors = CardDefaults.cardColors(containerColor = color),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(18.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                count.toString(),
                fontSize = 32.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
            Text(title, fontSize = 18.sp, color = Color.White, fontWeight = FontWeight.Medium)
        }
    }
}

@Composable
private fun QuickActionCardLarge(
    title: String,
    icon: ImageVector,
    iconColor: Color,
    onClick: () -> Unit,
    modifier: Modifier
) {
    Card(
        modifier = modifier
            .height(100.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(15.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconColor,
                modifier = Modifier.size(35.dp)
            )
            Spacer(Modifier.height(10.dp))
            Text(title, color = Color.Black, fontSize = 17.sp, fontWeight = FontWeight.SemiBold)
        }
    }
}

@Composable
private fun IssueCardLarge(issue: Issue, onClick: () -> Unit, viewModel: HostelIssuesViewModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(90.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(15.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            Modifier
                .padding(18.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Outlined.Assignment,
                contentDescription = null,
                tint = Color(0xFF2962FF),
                modifier = Modifier.size(32.dp)
            )
            Spacer(Modifier.width(17.dp))
            Column(Modifier.weight(1f)) {
                Text(issue.title, fontSize = 20.sp, fontWeight = FontWeight.SemiBold, color = Color.Black)
                Text(issue.type, fontSize = 16.sp, color = Color(0xFF757575))
            }
            Surface(
                color = when (issue.status) {
                    "Pending" -> Color(0xFFFF8A65)
                    "In Progress" -> Color(0xFF42A5F5)
                    "Resolved" -> Color(0xFF66BB6A)
                    else -> Color(0xFFE0E0E0)
                }, shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    issue.status,
                    color = Color.White,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                )
            }

            Spacer(Modifier.width(8.dp))
            // Delete button for the student's own issue
            IconButton(onClick = { viewModel.deleteIssue(issue.id) }) {
                Icon(Icons.Outlined.Delete, contentDescription = "Delete Issue", tint = Color(0xFFEF4444))
            }
        }
    }
}

@Composable
private fun CreateIssueTabEnhanced(
    navController: NavController,
    categories: List<IssueCategoryUi>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(Color.White)
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Text(
            "Report an Issue",
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp,
            color = Color.Black
        )
        Spacer(Modifier.height(6.dp))
        Text(
            "Choose a category to continue. Attachments optional, all text on white is always dark.",
            color = Color(0xFF222222),
            fontSize = 13.sp
        )
        Spacer(Modifier.height(14.dp))
        categories.forEach { cat ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(83.dp)
                    .clickable { navController.navigate("raise_issue/${cat.type}") }
                    .padding(bottom = 7.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(15.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Row(
                    Modifier
                        .fillMaxSize()
                        .padding(start = 14.dp, end = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        Modifier.size(45.dp)
                            .clip(CircleShape)
                            .background(cat.color),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = cat.icon,
                            contentDescription = null,
                            tint = Color.Black,
                            modifier = Modifier.size(26.dp)
                        )
                    }
                    Spacer(Modifier.width(18.dp))
                    Column(
                        Modifier.weight(1f)
                    ) {
                        Text(
                            cat.title,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 16.sp,
                            color = Color.Black
                        )
                        Spacer(Modifier.height(3.dp))
                        Text(
                            text = "${cat.type} issue, tap to report",
                            fontSize = 12.sp,
                            color = Color(0xFF525355),
                            maxLines = 1
                        )
                    }
                    Spacer(Modifier.width(10.dp))
                    Surface(
                        color = brandGreen,
                        shape = RoundedCornerShape(50),
                    ) {
                        Text(
                            "+ New",
                            color = Color.White,
                            fontWeight = FontWeight.Medium,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                        )
                    }
                }
            }
        }
        Spacer(Modifier.height(12.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(Icons.Outlined.AttachFile, contentDescription = null, tint = brandGreen)
            Spacer(Modifier.width(6.dp))
            Text(
                "Attach File (optional)",
                color = brandGreen,
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.clickable { }
            )
        }
    }
}

@Composable
fun ProfileTab(
    student: Student?,
    onLogout: () -> Unit,
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
            Text(
                student?.name?.first()?.toString() ?: "U",
                color = Color.White,
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(Modifier.height(20.dp))
        Text(
            student?.name ?: "Student",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF212121)
        )
        Text(
            "Room ${student?.roomNumber}, Block ${student?.block}",
            fontSize = 14.sp,
            color = Color(0xFF757575),
        )
        Spacer(Modifier.height(32.dp))
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
