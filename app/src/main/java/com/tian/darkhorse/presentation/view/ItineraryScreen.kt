package com.tian.darkhorse.presentation.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tian.darkhorse.presentation.model.Itinerary

@Composable
fun ItineraryScreen(
    list: List<Itinerary>
) {
    LazyColumn(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxHeight()
    ) {
        items(list) { item ->
            ItineraryCard(item)
        }
    }
}

@Composable
fun ItineraryCard(ticket: Itinerary) {
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = 4.dp,
        modifier = Modifier.padding(top = 16.dp)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            ItineraryInfoView(ticket)
            ItineraryStateView(ticket)
        }
    }
}

@Composable
fun ItineraryInfoView(ticket: Itinerary) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.weight(2f)) {
            Text(
                text = ticket.ticketId,
                style = TextStyle(fontSize = 24.sp),
                modifier = Modifier.padding(top=12.dp, end = 4.dp)
            )
            Row(modifier = Modifier.padding(top = 8.dp)) {
                Text(text = ticket.from, style = TextStyle(fontSize = 16.sp))
                Text(text = "___到___", modifier = Modifier.padding(horizontal = 4.dp))
                Text(text = ticket.to, style = TextStyle(fontSize = 16.sp),
                    modifier = Modifier.padding(start = 4.dp))
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
fun ItineraryStateView(ticket: Itinerary) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Divider(
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth()
        )
        Row {

        }
    }
}

@Preview
@Composable
fun ItineraryScreenPreView() {
    ItineraryScreen(
        listOf(
            Itinerary(
                "TC-4521",
                "airline1",
                "18:20 pm",
                "3 小时 20 分",
                "BeiJing",
                "Sydney",
                "800$"
            )
        )
    )
}
