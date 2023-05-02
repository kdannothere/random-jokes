package com.kdan.randomjokes.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kdan.randomjokes.data.Joke
import com.kdan.randomjokes.data.JokeApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class JokeViewModel: ViewModel() {

    private val _jokes = MutableStateFlow(listOf<Joke>())
    val jokes = _jokes.asStateFlow()
    val showSettingDialog = mutableStateOf(false)
    val darkMode = mutableStateOf(false)

    init {
        getOneJoke()
    }

    fun getTenJokes() {
        viewModelScope.launch {
            try {
                _jokes.value = JokeApi.retrofitService.getTenJokes()
                Log.d("kdan_log", "getTenJokes()")
            } catch (e: Exception) {
                _jokes.value = emptyList()
                Log.d("kdan_log", "2 - ${e.message}")
            }
        }
    }

    fun getOneJoke() {
        viewModelScope.launch {
            try {
                _jokes.value = listOf(JokeApi.retrofitService.getOneJoke())
                Log.d("kdan_log", "getOneJoke()")
            } catch (e: Exception) {
                _jokes.value = emptyList()
                Log.d("kdan_log", "4 - ${e.message}")
            }
        }
    }
}