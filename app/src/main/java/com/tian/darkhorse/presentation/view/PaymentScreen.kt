package com.tian.darkhorse.presentation.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Checkbox
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tian.darkhorse.presentation.model.Ticket
import com.tian.darkhorse.presentation.viewmodel.FlightReservationViewModel
import com.tian.darkhorse.presentation.viewmodel.FlightReservationViewModel.Action.RequestPay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
fun PaymentScreen(
    ticket: Ticket,
    viewModel: FlightReservationViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopBar("支付页面")
        Card(
            modifier = Modifier.padding(16.dp),
            elevation = 4.dp
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                TimeInfo(ticket)
                Divider(
                    modifier = Modifier
                        .padding(horizontal = 4.dp, vertical = 4.dp)
                        .fillMaxWidth()
                )
                PayInfo(ticket.price)
            }
        }
        Card(modifier = Modifier.padding(horizontal = 16.dp), elevation = 4.dp) {
            Text(text = "乘客：田勋    身份证号：61232219911028xxxx", modifier = Modifier.padding(16.dp))
        }
        Button(
            onClick = { viewModel.action(RequestPay(ticket.id)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent)
        ) {
            Text(text = "确认支付")
        }
    }
    val dialogState = rememberDialogState()
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = Unit, block = {
        viewModel.uiEvent.onEach {
            when (it) {
                is FlightReservationViewModel.Event.PopupMessage -> {
                    dialogState.showDialog(it.message)
                }
            }
        }.launchIn(scope)
    })
    DialogHost(dialogState)
}

@Composable
fun TimeSlot(time: String, destination: String) {
    Column {
        Text(text = time, fontSize = 24.sp)
        Text(text = destination, fontSize = 24.sp, modifier = Modifier.padding(top = 4.dp))
    }
}

@Composable
fun TimeInfo(ticket: Ticket) {
    Row {
        TimeSlot(ticket.startTime, ticket.from)
        Spacer(modifier = Modifier.width(10.dp))
        TimeSlot(ticket.arrivalTime, ticket.to)
    }
}

@Composable
fun PayInfo(price: String) {
    val checkbox1 = remember {
        mutableStateOf(true)
    }
    val checkbox2 = remember {
        mutableStateOf(false)
    }
    Row {
        Text(text = "票价：${price}", fontSize = 16.sp, modifier = Modifier.padding(end = 16.dp))
        Column {
            Row {
                Checkbox(checked = checkbox1.value, onCheckedChange = {
                    checkbox1.value = it
                    checkbox2.value = !it
                }, modifier = Modifier.padding(end = 4.dp))
                Text(text = "微信支付", modifier = Modifier.padding(end = 16.dp))

            }
            Row(modifier = Modifier.padding(top = 8.dp)) {
                Checkbox(checked = checkbox2.value, onCheckedChange = {
                    checkbox2.value = it
                    checkbox1.value = !it
                }, modifier = Modifier.padding(end = 4.dp))
                Text(text = "阿里支付")
            }

        }
    }
}

@Preview
@Composable
fun PaymentScreenPreview() {
    TimeSlot("12:30", "ss")
}