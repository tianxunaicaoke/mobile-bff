package com.tian.darkhorse.presentation.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TopBar(title:String) {
    Column(modifier = Modifier
        .height(80.dp)
        .fillMaxWidth(),
    horizontalAlignment = Alignment.CenterHorizontally){
        Text(text = title, fontSize = 28.sp, modifier = Modifier.padding(8.dp))
        Divider(modifier = Modifier.fillMaxWidth(),thickness = 2.dp)
    }
}