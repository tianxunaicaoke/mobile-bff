package com.tian.darkhorse.presentation.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.tian.darkhorse.R
import com.tian.darkhorse.presentation.model.Ticket

@Composable
fun TicketsScreen(
    list: List<Ticket>,
    clickBuy: (Ticket) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxHeight()
    ) {
        items(list) { item ->
            TicketCard(item, clickBuy)
        }
    }
}

@Composable
fun TicketCard(ticket: Ticket, clickBuy: (Ticket) -> Unit) {
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = 4.dp,
        modifier = Modifier.padding(top = 16.dp)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            InfoView(ticket)
            StateView(ticket, clickBuy)
        }
    }
}

@Composable
fun InfoView(ticket: Ticket) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.weight(2f)) {
            Text(
                text = ticket.ticketId,
                style = TextStyle(fontSize = 24.sp),
                modifier = Modifier.padding(top = 12.dp, end = 4.dp)
            )
            Row(modifier = Modifier.padding(top = 8.dp)) {
                Text(text = ticket.from, style = TextStyle(fontSize = 16.sp))
                Text(text = "___到___", modifier = Modifier.padding(horizontal = 4.dp))
                Text(
                    text = ticket.to, style = TextStyle(fontSize = 16.sp),
                    modifier = Modifier.padding(start = 4.dp)
                )
            }
            Row(modifier = Modifier.padding(top = 8.dp)) {
                Text(
                    text = "始:${ticket.startTime}",
                    color = Color.Blue,
                    style = TextStyle(fontSize = 16.sp)
                )
                Text(
                    text = "终:${ticket.arrivalTime}",
                    color = Color.Blue,
                    style = TextStyle(fontSize = 16.sp),
                    modifier = Modifier.padding(start = 4.dp)
                )
            }
            Text(
                text = "票价：${ticket.price}",
                style = TextStyle(fontSize = 12.sp),
                modifier = Modifier.padding(end = 4.dp, top = 16.dp)
            )
        }
        Image(
            painter = painterResource(ticket.airline.toImage()),
            contentDescription = "",
            modifier = Modifier
                .size(120.dp)
                .clip(RoundedCornerShape(8.dp))
                .weight(1f),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun StateView(ticket: Ticket, clickBuy: (Ticket) -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Divider(
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth()
        )
        Row {
            val firstClassState = if (ticket.hasFirstClass) "有" else "无"
            Text(
                text = "头等舱",
                style = TextStyle(fontSize = 12.sp),
                modifier = Modifier.padding(end = 4.dp)
            )
            Text(
                text = firstClassState,
                color = Color.Blue,
                style = TextStyle(fontSize = 12.sp),
                modifier = Modifier.padding(end = 4.dp)
            )

            val economyClassState = if (ticket.hasEconomyClass) "有" else "无"
            Text(
                text = "头等舱",
                style = TextStyle(fontSize = 12.sp),
                modifier = Modifier.padding(end = 4.dp)
            )
            Text(
                text = economyClassState,
                color = Color.Blue,
                style = TextStyle(fontSize = 12.sp),
                modifier = Modifier.padding(end = 4.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = { clickBuy(ticket) },
                contentPadding = PaddingValues(0.dp, 0.dp),
                modifier = Modifier
                    .height(20.dp)
                    .padding(end = 10.dp),
                elevation = ButtonDefaults.elevation(defaultElevation = 0.dp ),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent)
            ) {
                Text(text = "点击购买", fontSize = 10.sp)
            }
        }
    }
}

fun String.toImage() = when (this) {
    "airline1" -> R.drawable.airline_1
    "airline2" -> R.drawable.airline_2
    "airline3" -> R.drawable.airline_3
    "airline4" -> R.drawable.airline_4
    "airline5" -> R.drawable.airline_5
    else -> R.drawable.airline_1
}

@Preview
@Composable
fun TicketsScreenPreView() {
    TicketsScreen(
        listOf(
            Ticket(
                "TC-4521",
                "1",
                "airline1",
                "12:35 pm",
                "18:20 pm",
                "+08:00",
                "BeiJing",
                "Sydney",
                "800$",
                false,
                true
            )
        ),
        {}
    )
}