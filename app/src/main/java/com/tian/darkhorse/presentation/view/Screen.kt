package com.tian.darkhorse.presentation.view

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.primarySurface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.gson.Gson
import com.tian.darkhorse.presentation.viewmodel.FlightReservationViewModel
import com.tian.darkhorse.presentation.viewmodel.FlightReservationViewModel.Action
import com.tian.darkhorse.presentation.viewmodel.FlightReservationViewModel.State

@Composable
fun Screen(
    navController: NavHostController,
    viewModel: FlightReservationViewModel
) {
    val uiState = viewModel.uiState.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier
                    .height(80.dp),
                elevation = 0.dp
            ) {
                Spacer(modifier = Modifier.width(8.dp))
                val selectText = remember {
                    mutableStateOf("机票")
                }
                Item(text = "机票", selectText.value == "机票", click = {
                    selectText.value = it
                    viewModel.action(Action.RequestTicket)
                })
                Item(text = "行程单", selectText.value == "行程单", click = {
                    selectText.value = it
                    viewModel.action(Action.RequestItinerary)
                })
            }
        }
    ) {
        Column(Modifier.fillMaxHeight()) {
            when (val result = uiState.value.result) {
                is State.Result.Welcome -> {
                    Text("welcome")
                }
                is State.Result.ItinerarySuccess -> {
                    ItineraryScreen(list = result.success)
                }
                is State.Result.TicketSuccess -> {
                    TicketsScreen(
                        list = result.success,
                        clickBuy = {
                            val productJson = Uri.encode(Gson().toJson(it))
                            navController.navigate("payment/$productJson")
                        }
                    )
                }
                is State.Result.Error -> {
                    Text(result.error)
                }
            }
        }
    }
}

@Composable
fun Item(
    text: String,
    isSelect: Boolean,
    click: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .padding(top = 12.dp)
            .height(70.dp)
            .width(100.dp)
            .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
            .background(
                when {
                    isSelect -> Color.White
                    else -> MaterialTheme.colors.primarySurface
                }
            )
            .clickable {
                click(text)
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            color = when {
                isSelect -> Color.Black
                else -> Color.White
            },
            text = text,
            fontSize = 22.sp
        )
    }
}