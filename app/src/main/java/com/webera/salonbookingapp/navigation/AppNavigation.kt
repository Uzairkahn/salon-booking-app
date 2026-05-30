package com.webera.salonbookingapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.webera.salonbookingapp.ui.screens.auth.LoginScreen
import com.webera.salonbookingapp.ui.screens.auth.SignupScreen
import com.webera.salonbookingapp.ui.screens.home.HomeScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {

        composable(Screen.Login.route) {
            LoginScreen(
                onSignupClick = {
                    navController.navigate(Screen.Signup.route)
                },
                onAuthenticationSuccess = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(Screen.Signup.route) {
            SignupScreen(
                onLoginClick = {
                    navController.popBackStack()
                },
                onAuthenticationSuccess = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(Screen.Home.route) {
            HomeScreen(
                onLogoutClick = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}