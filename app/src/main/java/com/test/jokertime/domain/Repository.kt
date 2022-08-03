package com.test.jokertime.domain

import com.test.jokertime.data.model.JokeModel

interface Repository {

    suspend fun getRandomJoke(): JokeModel

}
