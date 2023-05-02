package com.kdan.randomjokes.presentation.adapters

import com.kdan.randomjokes.data.Joke

object JokeAdapter {
    fun jokesToText(jokes: List<Joke>): List<String> {
        val list = mutableListOf<String>()
        jokes.forEach { joke ->
            list.add("${joke.setup} ${joke.punchline}")
        }
        return list
    }
}