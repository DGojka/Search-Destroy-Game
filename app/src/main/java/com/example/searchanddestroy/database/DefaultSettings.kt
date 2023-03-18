package com.example.searchanddestroy.database

class DefaultSettings {
    companion object{
        const val DEFAULT_PLANTING_PASSWORD_LENGTH = 1
        const val DEFAULT_DEFUSING_PASSWORD_LENGTH = 10
        const val DEFAULT_TIME_TO_EXPLODE = 180
        const val DEFAULT_WRONG_PASSWORD_PENALTY_LENGTH = 30

        const val MAX_PASSWORD_LENGTH = 360
        const val MAX_TIME_TO_EXPLODE = 3600
        const val MIN_TIME_TO_EXPLODE = 15
    }
}