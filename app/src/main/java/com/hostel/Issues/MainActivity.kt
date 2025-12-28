package com.hostel.Issues

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.hostel.Issues.navigation.NavGraph
import com.hostel.Issues.viewmodel.HostelIssuesViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val hostelIssuesViewModel: HostelIssuesViewModel = viewModel()
            NavGraph(
                navController = navController,
                viewModel = hostelIssuesViewModel
            )
        }
    }
}
