package com.kdan.randomjokes.ui.dialogs

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.kdan.randomjokes.utility.Utility

@Composable
fun SettingsDialog(
    darkMode: MutableState<Boolean>,
    showSettingDialog: MutableState<Boolean>,
) {
    Log.d("kdan_log", "SettingsDialog()")
    Dialog(onDismissRequest = { Utility.turnOffDialog(showSettingDialog) }) {
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Dark mode",
                    fontSize = 25.sp
                )
                Spacer(modifier = Modifier.width(30.dp))
                Switch(
                    checked = darkMode.value,
                    onCheckedChange = { darkMode.value = !darkMode.value }
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { Utility.turnOffDialog(showSettingDialog) }) {
                Text(text = "Close Settings")
            }
        }
    }
}