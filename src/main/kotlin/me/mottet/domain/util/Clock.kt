package me.mottet.domain.util

import java.time.LocalDateTime

class Clock {

    fun now(): LocalDateTime {
        return LocalDateTime.now()
    }
}