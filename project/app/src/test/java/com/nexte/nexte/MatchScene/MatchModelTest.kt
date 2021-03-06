package com.nexte.nexte.MatchScene

import com.fasterxml.jackson.databind.deser.DataFormatReaders
import com.nexte.nexte.R
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class MatchModelTest {

    @Before
    fun setUp(){
    }

    @Test
    fun testMatchModel() {
        //prepare
        //call
        val testClass = MatchModel()

        //assert
        assertNotNull(testClass)
    }

    @Test
    fun testInitScene() {
        //prepare
        //call
        val testInit = MatchModel.InitScene()

        //assert
        assertNotNull(testInit)
    }

    @Test
    fun testRequest() {
        //prepare
        val matchData  = MatchModel.MatchData(
        MatchModel.MatchPlayer("larissa", 1),
        MatchModel.MatchPlayer("larissa2", 1), "1")

        //call
        val request = MatchModel.InitScene.Request(matchData)
        request.match = matchData

        //assert
        assertEquals(request.match, matchData)
    }

    @Test
    fun testResponse() {
        //prepare
        val challengerResponse = MatchModel.MatchPlayer("Fiona", R.mipmap.ic_launcher)
        val challengedResponse = MatchModel.MatchPlayer("Marquinhos", R.mipmap.ic_launcher)
        val matchDataResponse = MatchModel.MatchData(challengedResponse, challengerResponse, "1")
        //call
        val matchTest = MatchModel.InitScene.Response(matchDataResponse)
        matchTest.match = matchDataResponse

        //assert
        assertEquals(challengedResponse.name, matchTest.match.challenged.name )
        assertEquals(challengedResponse.photo, matchTest.match.challenged.photo)
        assertEquals(challengerResponse.name, matchTest.match.challenger.name)
        assertEquals(challengerResponse.photo, matchTest.match.challenger.photo)
    }

    @Test
    fun sucessMatchData() {
        //prepare
        val challenger = MatchModel.MatchPlayer(name = "Letícia", photo = R.mipmap.ic_launcher)
        val challenged = MatchModel.MatchPlayer(name = "Alexandre", photo = R.mipmap.ic_launcher)

        //call
        val matchData = MatchModel.MatchData(challenger = challenger, challenged = challenged, challengeId = "1")

        //asserts

        assertEquals(challenger, matchData.challenger)
        assertEquals(challenged, matchData.challenged)
    }


    @Test
    fun successSetMatchFormatted(){
        //prepare
        val cdName = "Gabriel"
        val crName = "leticia"
        val formatted = MatchModel.FormattedMatchData(cdName, 1, crName, 1)
        //call
        val viewModel = MatchModel.InitScene.ViewModel(formatted)
        viewModel.matchFormatted = formatted
        //assert
        assertEquals(viewModel.matchFormatted, formatted)
    }

    @Test
    fun formattedMatchData() {
        //prepare
        val challengerName = "Anduin"
        val challengerPhoto = 1
        val challengedName = "Jaina"
        val challengedPhoto = 2

        //call
        val formattedMatchData = MatchModel.FormattedMatchData(challengerName = challengerName, challengerPhoto = challengerPhoto,
                                                                challengedName = challengedName, challengedPhoto = challengedPhoto)

        formattedMatchData.challengedName = challengedName
        formattedMatchData.challengedPhoto = challengedPhoto
        formattedMatchData.challengerName = challengerName
        formattedMatchData.challengerPhoto = challengerPhoto

        //asserts
        assertEquals(challengerName,formattedMatchData.challengerName)
        assertEquals(challengerPhoto, formattedMatchData.challengerPhoto)
        assertEquals(challengedName, formattedMatchData.challengedName)
        assertEquals(challengedPhoto, formattedMatchData.challengedPhoto)
    }

    @Test
    fun testMatchPlayer() {
        //prepare
        val name = "Rexxar"
        val photo = 3

        //call
        val testMatchPlayer = MatchModel.MatchPlayer(name =  "Rexxar", photo = 3)
        testMatchPlayer.name = name
        testMatchPlayer.photo = photo
        //asserts
        assertEquals(name, testMatchPlayer.name)
        assertEquals(photo, testMatchPlayer.photo)
    }

    @Test
    fun testSendMatchResultConstructor(){
        val sendMatchResult = MatchModel.SendMatchResult()

        assertNotNull(sendMatchResult)
    }

    @Test
    fun testSendMatchResultRequest(){
        val request = MatchModel.SendMatchResult.Request("1")

        assertNotNull(request)
    }

    @Test
    fun testSendMatchResultResponse(){
        val response = MatchModel.SendMatchResult.Response(
                MatchModel.SendMatchResult.Status.SUCESSED)

        assertEquals(response.status, MatchModel.SendMatchResult.Status.SUCESSED)

    }

    @Test
    fun testSendMatchResultViewModel(){
        val viewModel = MatchModel.SendMatchResult.ViewModel("asdasd")

        assertEquals(viewModel.message, "asdasd")
    }

    @Test
    fun testDeclineChallengeRequestConstructor(){
        val declineChallenge = MatchModel.DeclineChallengeRequest()
        assertNotNull(declineChallenge)
    }

    @Test
    fun testDeclineChallengeRequestRequest(){
        val request = MatchModel.DeclineChallengeRequest.
                                                                        Request("1")
        assertEquals("1", request.challengeId)
    }

    @Test
    fun testDeclineChallengeRequestResponse(){
        val response = MatchModel.DeclineChallengeRequest.
                Response(MatchModel.DeclineChallengeRequest.Status.SUCCESS)
        assertEquals(MatchModel.DeclineChallengeRequest.Status.SUCCESS, response.status)
    }

    @Test
    fun testDeclineChallengeRequestViewModel(){
        val viewModel = MatchModel.DeclineChallengeRequest
                .ViewModel(MatchModel.DeclineChallengeRequest.Status.SUCCESS, "1")
        assertEquals(MatchModel.DeclineChallengeRequest.Status.SUCCESS, viewModel.status)
        assertEquals("1", viewModel.message)
    }

    @Test
    fun testDeclineChallengeRequestStatus(){
        val status = MatchModel.DeclineChallengeRequest.Status
                .SUCCESS
        assertEquals(MatchModel.DeclineChallengeRequest.Status
                .SUCCESS, status)
    }

    @Test
    fun testSetsNumber(){
        val setsOne = MatchModel.SetsNumber.One
        val setsThree = MatchModel.SetsNumber.Three
        val setsFive = MatchModel.SetsNumber.Five
        val setsWO = MatchModel.SetsNumber.WO

        assertEquals(MatchModel.SetsNumber.One.number, setsOne.number)
        assertEquals(MatchModel.SetsNumber.Three.number, setsThree.number)
        assertEquals(MatchModel.SetsNumber.Five.number, setsFive.number)
        assertEquals(MatchModel.SetsNumber.WO.number, setsWO.number)

    }
}