package com.kdan.randomjokes.utility

import androidx.compose.runtime.MutableState

object Utility {

    fun turnOnDialog(showSettingDialog: MutableState<Boolean>) =
        run { showSettingDialog.value = true }

    fun turnOffDialog(showSettingDialog: MutableState<Boolean>) =
        run { showSettingDialog.value = false }

}