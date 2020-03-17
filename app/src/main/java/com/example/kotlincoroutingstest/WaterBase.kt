package com.example.kotlincoroutingstest

import kotlinx.coroutines.delay
import kotlin.random.Random

class WaterBase {

    private val resultTypes = arrayOf("Exist", "Not exist", "Exception")

    suspend fun checkState(): String {
        delay(6000)
        return resultTypes[Random.nextInt(3)]
    }
}