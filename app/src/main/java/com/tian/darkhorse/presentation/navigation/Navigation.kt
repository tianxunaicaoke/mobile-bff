package com.tian.darkhorse.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.tian.darkhorse.presentation.model.Ticket
import com.tian.darkhorse.presentation.view.PaymentScreen
import com.tian.darkhorse.presentation.view.Screen
import com.tian.darkhorse.presentation.viewmodel.FlightReservationViewModel

@Composable
fun NavigationComponent(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable(route = "payment/{id}", arguments = listOf(
            navArgument("id") {
                type = Ticket.NavigationType
            }
        )) { backStackEntry ->
            val viewModel = hiltViewModel<FlightReservationViewModel>()
            PaymentScreen(backStackEntry.arguments?.getParcelable("id")!!, viewModel)
        }
        composable("home") {
            val viewModel = hiltViewModel<FlightReservationViewModel>()
            Screen(navController, viewModel)
        }
    }
}