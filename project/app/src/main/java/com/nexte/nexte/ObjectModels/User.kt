package com.nexte.nexte.ObjectModels

import java.util.*

// ChallengeSended and ChallengeReceived have Challenge ID if are done or nil if don't
data class User(val id: String,
                val name: String,
                val profilePicture: String?,
                val nickname: String,
                val birthDate: Date,
                val rankPosition: Int,
                val email: String,
                val phone: String,
                val wins: Int,
                val loses: Int,
                val gender: Gender,
                val category: Category?,
                val status: Status,
                val challengeSended: String?,
                val challengeReceived: String?,
                val latestGames: List<String>?) {

    val matches: Int
        get() = this.wins + this.loses


    // Enums
    enum class Gender(val value: String) {
        MALE("Male"),
        FEMALE("Female")
    }

    enum class Status(val value: String) {
        AVAILABLE("Available"),
        INJURED("Injured"),
        UNAVAILABLE("Unavailable")
    }

    data class Category(val id: Int = 0,
                        val name: String = "")
}

