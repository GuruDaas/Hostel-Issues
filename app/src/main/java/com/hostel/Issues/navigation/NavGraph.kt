package com.hostel.Issues.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.NavType
import com.hostel.Issues.ui.screens.auth.RoleSelectionScreen
import com.hostel.Issues.ui.screens.auth.StudentLoginScreen
import com.hostel.Issues.ui.screens.auth.WardenLoginScreen
import com.hostel.Issues.ui.auth.StudentRegisterScreen
import com.hostel.Issues.ui.screens.student.StudentDashboard
import com.hostel.Issues.ui.screens.student.RaiseIssueScreen
import com.hostel.Issues.ui.screens.student.StudentIssueDetailScreen
import com.hostel.Issues.ui.screens.warden.WardenDashboard
import com.hostel.Issues.ui.screens.warden.WardenIssueDetailScreen
import com.hostel.Issues.viewmodel.HostelIssuesViewModel

@Composable
fun NavGraph(
    navController: NavHostController,
    viewModel: HostelIssuesViewModel
) {
    NavHost(
        navController = navController,
        startDestination = "role_select"
    ) {
        composable("role_select") {
            RoleSelectionScreen(navController = navController)
        }
        composable("student_login") {
            StudentLoginScreen(navController = navController, viewModel = viewModel)
        }
        composable("warden_login") {
            WardenLoginScreen(navController = navController, viewModel = viewModel)
        }
        // Student Register — lambda only, no viewModel
        composable("student_register") {
            StudentRegisterScreen(navController = navController) {
                navController.popBackStack()
                navController.navigate("student_login")
            }
        }
        composable("student_dashboard") {
            StudentDashboard(navController = navController, viewModel = viewModel)
        }
        composable("warden_dashboard") {
            WardenDashboard(navController = navController, viewModel = viewModel)
        }
        composable(
            route = "student_issue_detail/{issueId}",
            arguments = listOf(navArgument("issueId") { type = NavType.StringType })
        ) { backStackEntry ->
            val issueId = backStackEntry.arguments?.getString("issueId") ?: ""
            StudentIssueDetailScreen(navController = navController, viewModel = viewModel, issueId = issueId)
        }
        composable(
            route = "warden_issue_detail/{issueId}",
            arguments = listOf(navArgument("issueId") { type = NavType.StringType })
        ) { backStackEntry ->
            val issueId = backStackEntry.arguments?.getString("issueId") ?: ""
            WardenIssueDetailScreen(navController = navController, viewModel = viewModel, issueId = issueId)
        }
        composable(
            route = "raise_issue/{issueType}",
            arguments = listOf(navArgument("issueType") { type = NavType.StringType })
        ) { backStackEntry ->
            val issueType = backStackEntry.arguments?.getString("issueType") ?: "General"
            RaiseIssueScreen(issueType = issueType, onSubmit = { title, description, _location, _urgency, _timePref ->
                // create issue in viewModel and return to dashboard
                viewModel.raiseIssue(title = title, description = description, type = issueType)
                // pop back to student dashboard (or navigate there)
                navController.navigate("student_dashboard") {
                    popUpTo("student_dashboard") { inclusive = true }
                }
            })
        }
    }
}
