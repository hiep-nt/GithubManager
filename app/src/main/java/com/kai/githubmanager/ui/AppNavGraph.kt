package com.kai.githubmanager.ui

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.kai.githubmanager.ui.screens.userdetail.UserDetailScreen
import com.kai.githubmanager.ui.screens.userlist.UserListScreen

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "user_list") {
        composable("user_list") {
            UserListScreen(
                viewModel = hiltViewModel(),
                onUserClick = { userName ->
                    navController.navigate("user_detail/$userName")
                }
            )
        }
        composable(
            route = "user_detail/{userName}",
            arguments = listOf(navArgument("userName") { type = NavType.StringType })
        ) { backStackEntry ->
            val userName = backStackEntry.arguments?.getString("userName") ?: ""
            UserDetailScreen(
                userName = userName,
                onBackClicked = { navController.popBackStack() }
            )
        }
    }
}