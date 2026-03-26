package com.example.myapplication

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myapplication.presentation.AppDetailsUiState
import com.example.myapplication.presentation.AppDetailsViewModel
import com.example.myapplication.presentation.AppListViewModel
import com.example.myapplication.ui.screen.AppDetailsScreen
import com.example.myapplication.ui.screen.AppListScreen

object Routes {
    const val LIST = "app_list"
    const val DETAILS = "app_details"
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.LIST
    ) {
        composable(Routes.LIST) {
            val viewModel: AppListViewModel = hiltViewModel()
            AppListScreen(
                viewModel = viewModel,
                onAppClick = { appId ->
                    navController.navigate("${Routes.DETAILS}/$appId")
                }
            )
        }

        composable(
            route = "${Routes.DETAILS}/{appId}",
            arguments = listOf(
                navArgument("appId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val viewModel: AppDetailsViewModel = hiltViewModel(backStackEntry)
            val uiState by viewModel.uiState.observeAsState(AppDetailsUiState())

            uiState.app?.let { app ->
                AppDetailsScreen(
                    app = app,
                    onBackClick = { navController.popBackStack() }
                )
            }
        }
    }
}