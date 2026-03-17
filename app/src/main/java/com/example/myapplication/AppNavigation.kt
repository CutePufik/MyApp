package com.example.myapplication

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myapplication.data.AppRepository
import com.example.myapplication.ui.screen.AppDetailsScreen
import com.example.myapplication.ui.screen.AppListScreen
import com.example.myapplication.ui.screen.AppListViewModel

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
            val viewModel: AppListViewModel = viewModel()
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
                navArgument("appId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val appId = backStackEntry.arguments?.getInt("appId") ?: -1
            val app = AppRepository.getAppById(appId)

            if (app != null) {
                AppDetailsScreen(
                    app = app,
                    onBackClick = { navController.popBackStack() }
                )
            }
        }
    }
}