package com.tian.darkhorse.presentation.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tian.darkhorse.presentation.model.AlertMessage

@Composable
fun PayAlertDialog(
    message: AlertMessage,
    dismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = dismiss,
        title = {
            Text(
                message.title,
                fontSize = 24.sp
            )
        },
        text = {
            Text(
                message.message,
                fontSize = 18.sp
            )
        },
        confirmButton = {
            Text(
                text = "OK",
                modifier = Modifier.size(40.dp).clickable {
                    dismiss()
                }
            )
        }
    )
}

@Stable
class DialogState(var displayState: MutableState<AlertMessage?>) {
    fun showDialog(data: AlertMessage) {
        displayState.value = data
    }

    fun dismiss() {
        displayState.value = null
    }
}

@Composable
fun rememberDialogState(
    dialogDataState: MutableState<AlertMessage?> = rememberSaveable {
        mutableStateOf(null)
    }
): DialogState {
    return DialogState(dialogDataState)
}

@Composable
fun DialogHost(
    dialogState: DialogState,
    dialog: @Composable (AlertMessage, onDismiss: () -> Unit) -> Unit = { data, onDismiss ->
        PayAlertDialog(data, onDismiss)
    }
) {
    dialogState.displayState.value?.let {
        dialog(it) {
            dialogState.dismiss()
        }
    }
}

