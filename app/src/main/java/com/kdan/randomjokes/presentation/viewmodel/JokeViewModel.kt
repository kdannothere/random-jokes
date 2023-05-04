package com.kdan.randomjokes.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kdan.randomjokes.data.Joke
import com.kdan.randomjokes.data.JokeApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class JokeViewModel : ViewModel() {

    private val _jokes = MutableStateFlow(listOf<Joke>())
    val jokes = _jokes.asStateFlow()
    val showSettingDialog = mutableStateOf(false)
    val darkMode = mutableStateOf(false)

    init {
        getOneJoke()
    }

    fun getTenJokes() {
        viewModelScope.launch(Dispatchers.IO) {
            // clear list of jokes to show spinner
            _jokes.value = emptyList()
            // load new list of jokes
            try {
                _jokes.value = JokeApi.retrofitService.getTenJokes()
            } catch (e: Exception) {
                _jokes.value += Joke(
                    setup = "Please check your internet connection.",
                    punchline = "If it`s OK then web-site is not available, sorry."
                )
            }
        }
    }

    fun getOneJoke() {
        viewModelScope.launch(Dispatchers.IO) {
            // clear list of jokes to show spinner
            _jokes.value = emptyList()
            // load new list of jokes
            try {
                _jokes.value = listOf(JokeApi.retrofitService.getOneJoke())
            } catch (e: Exception) {
                _jokes.value += Joke(
                    setup = "Please check your internet connection.",
                    punchline = "If it`s OK then web-site is not available, sorry."
                )
            }
        }
    }
}