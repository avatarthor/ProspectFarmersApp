package com.android.goodnatureagro.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.android.goodnatureagro.ui.auth.SignInScreen
import com.android.goodnatureagro.ui.farmers.add.AddFarmerScreen
import com.android.goodnatureagro.ui.farmers.edit.EditFarmerScreen
import com.android.goodnatureagro.ui.farmers.list.FarmerListScreen
import com.android.goodnatureagro.util.Constants.Route

@Composable
fun NavGraph(
    navController: NavHostController,
    startDestination: String = Route.SIGN_IN
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        // Auth
        composable(Route.SIGN_IN) {
            SignInScreen(
                onSignInSuccess = {
                    navController.navigate(Route.FARMER_LIST) {
                        popUpTo(Route.SIGN_IN) { inclusive = true }
                    }
                }
            )
        }

        // Farmer List
        composable(Route.FARMER_LIST) {
            FarmerListScreen(
                onAddFarmer = {
                    navController.navigate(Route.ADD_FARMER)
                },
                onEditFarmer = { farmerId ->
                    navController.navigate(Route.editFarmer(farmerId))
                }
            )
        }

        // Add Farmer
        composable(Route.ADD_FARMER) {
            AddFarmerScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        // Edit Farmer
        composable(
            route = Route.EDIT_FARMER,
            arguments = listOf(
                navArgument("farmerId") {
                    type = NavType.StringType
                }
            )
        ) {
            EditFarmerScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}