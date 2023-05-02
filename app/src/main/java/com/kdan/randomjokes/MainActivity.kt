package com.kdan.randomjokes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kdan.randomjokes.ui.dialogs.SettingsDialog
import com.kdan.randomjokes.ui.images.LoadingImage
import com.kdan.randomjokes.ui.images.SettingsImage
import com.kdan.randomjokes.ui.theme.RandomJokesTheme
import kotlinx.coroutines.launch

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
                        getTenJokes = { viewModel.getTenJokes() },
                        getOneJoke = { viewModel.getOneJoke() },
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FragmentJokes(
    texts: List<String>,
    showSettingDialog: MutableState<Boolean>,
    getTenJokes: () -> Unit,
    getOneJoke: () -> Unit,
) {
    val lazyListState = rememberLazyListState()
    val snackbarHostState = remember { SnackbarHostState() }
    Scaffold(
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        },
        content = { paddingValues ->
            LazyColumn(
                state = lazyListState,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                item {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            val modifier = Modifier
                            Button(
                                modifier = modifier.align(Alignment.TopCenter),
                                onClick = { getTenJokes() },
                            ) {
                                Text(text = "Show ten random jokes")
                            }
                            SettingsImage(
                                modifier.align(Alignment.TopEnd),
                                showSettingDialog
                            )
                        }
                        Button(
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            onClick = { getOneJoke() },
                        ) {
                            Text(text = "Show one random joke")
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    ) {
                        if (texts.isEmpty()) {
                            LoadingImage()
                        } else {
                            texts.forEach { text ->
                                ShowJoke(
                                    text = text,
                                    snackbarHostState = snackbarHostState
                                )
                            }
                        }
                    }
                }
            }
        }
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ShowJoke(
    text: String,
    snackbarHostState: SnackbarHostState,
) {
    val clipboardManager: ClipboardManager = LocalClipboardManager.current
    val coroutineScope = rememberCoroutineScope()
    Text(
        text = text,
        fontSize = 20.sp,
        modifier = Modifier.combinedClickable(
            onClick = {
                clipboardManager.setText(AnnotatedString(text))
                coroutineScope.launch {
                    snackbarHostState.showSnackbar(
                        message = "Copied:\n$text",
                        duration = SnackbarDuration.Short
                    )
                }
            }
        )
    )
    Spacer(modifier = Modifier.height(16.dp))
}