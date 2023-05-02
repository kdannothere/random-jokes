package com.kdan.randomjokes.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.kdan.randomjokes.presentation.adapters.JokeAdapter
import com.kdan.randomjokes.presentation.dialogs.SettingsDialog
import com.kdan.randomjokes.presentation.fragments.FragmentJokes
import com.kdan.randomjokes.presentation.theme.RandomJokesTheme
import com.kdan.randomjokes.presentation.viewmodel.JokeViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: JokeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RandomJokesTheme(darkTheme = viewModel.darkMode.value) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val jokes = viewModel.jokes.collectAsState()
                    val texts = JokeAdapter.jokesToText(jokes.value)
                    if (viewModel.showSettingDialog.value) {
                        SettingsDialog(
                            darkMode = viewModel.darkMode,
                            showSettingDialog = viewModel.showSettingDialog
                        )
                    }
                    FragmentJokes(
                        texts = texts,
                        showSettingDialog = viewModel.showSettingDialog,
                        darkMode = viewModel.darkMode,
                        getTenJokes = { viewModel.getTenJokes() },
                        getOneJoke = { viewModel.getOneJoke() },
                    )
                }
            }
        }
    }
}