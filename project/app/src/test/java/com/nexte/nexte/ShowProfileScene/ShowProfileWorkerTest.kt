package com.nexte.nexte.ShowProfileScene

import com.nexte.nexte.Player
import com.nexte.nexte.UserSingleton
import org.junit.After
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test
import kotlin.concurrent.thread

class ShowProfileWorkerTest {

    private var worker: ShowProfileWorker? = null

    @Before
    fun setUp() {
        this.worker = ShowProfileWorker()
    }

    @Test
    fun testGetUserProfileEmptyUser(){
        //prepare
        val request = ShowProfileModel.Request(username = "gabrielalbino", tokenID = "")
        val player = Player("",
                -1,
                "",
                "",
                "",
                "",
                -1,
                "")

        //call
        this.worker?.getUserProfile(request = request, completion = { response ->
            //assert
            assertEquals(response.user?.name, player.name)
            assertEquals(response.user?.rankingPosition, player.rankingPosition)
            assertEquals(response.user?.pictureAdress, player.pictureAdress)
            assertEquals(response.user?.email, player.email)
            assertEquals(response.user?.gender, player.gender)
            assertEquals(response.user?.club, player.club)
            assertEquals(response.user?.age, player.age)
            assertEquals(response.user?.password, player.password)
        })
    }

    @Test
    fun testGetUserProfileSuccess(){
        //prepare
        val request = ShowProfileModel.Request(username = "gabrielalbino", tokenID = "kjbdjh213")
        val player = Player("gabrielalbino",
                2,
                "imgur.com/nudh486d4",
                "enggabriel@gmail.com",
                "masculino",
                "ASCAD",
                19,
                "feioso")

        //call
        this.worker?.getUserProfile(request = request, completion = { response ->
            //assert
            assertEquals(response.user?.name, player.name)
            assertEquals(response.user?.rankingPosition, player.rankingPosition)
            assertEquals(response.user?.pictureAdress, player.pictureAdress)
            assertEquals(response.user?.email, player.email)
            assertEquals(response.user?.gender, player.gender)
            assertEquals(response.user?.club, player.club)
            assertEquals(response.user?.age, player.age)
        })
    }

    @After
    fun tearDown() {
        this.worker = null
    }
}