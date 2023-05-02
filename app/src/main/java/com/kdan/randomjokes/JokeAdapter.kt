package com.kdan.randomjokes

import com.kdan.randomjokes.network.Joke

object JokeAdapter {
    fun jokesToText(jokes: List<Joke>): List<String> {
        val list = mutableListOf<String>()
        jokes.forEach { joke ->
            list.add("${joke.setup} ${joke.punchline}")
        }
        return list
    }
}