package me.mottet.infra

import java.time.LocalDateTime

class Clock {

    fun now(): LocalDateTime {
        return LocalDateTime.now()
    }
}