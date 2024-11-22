package com.example.ecowise.screens

import EcoDicasListScreen
import androidx.compose.runtime.Composable
import androidx.navigation.compose.*
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "list") {
        composable("list") {
            EcoDicasListScreen(
                viewModel = hiltViewModel(),
                onNavigateToAdd = { navController.navigate("add") }
            )
        }
        composable("add") {
            AddEcoDicaScreen(
                viewModel = hiltViewModel(),
                onBack = { navController.popBackStack() }
            )
        }
        composable("identification") {
            IdentificationScreen(onBack = { navController.popBackStack() })
        }
    }
}