package com.nexte.nexte.LikeListScene

import com.github.kittinunf.fuel.android.core.Json
import com.github.kittinunf.fuel.core.FuelError
import com.nexte.nexte.Entities.User.UserAdapterSpy
import com.nexte.nexte.Entities.User.UserManager
import com.nexte.nexte.Entities.Like.LikeAdapterSpy
import com.nexte.nexte.Entities.Like.LikeManager
import com.nexte.nexte.Entities.Story.StoryAdapterSpy
import com.nexte.nexte.Entities.Story.StoryManager
import org.json.JSONArray
import org.json.JSONObject
import org.junit.After
import org.junit.Before
import org.junit.Assert.*
import org.junit.Test
import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.fuel.core.Response
import com.nexte.nexte.Entities.Like.Like
import com.nexte.nexte.UserSingleton
import com.nexte.nexte.UserType
import java.net.URL
import java.util.*
import kotlin.concurrent.thread

class LikeListWorkerTest {

    var worker: LikeListWorker?=null
    var mock: MockWorkersUpdateLogic?=null
    private val jsonObject=JSONObject()

    @Before
    fun setUp() {
        this.worker=LikeListWorker()
        this.mock=MockWorkersUpdateLogic()
        this.worker?.updateLogic=mock
        this.worker?.userManager=UserManager(userAdapter=UserAdapterSpy())
        this.worker?.storyManager=StoryManager(StoryAdapterSpy())
        this.worker?.likeManager=LikeManager(LikeAdapterSpy())

        val likeJson=JSONObject()
        likeJson.put("date", "2018-01-07T00:00:00.000Z")
        likeJson.put("id", "asdasd")
        likeJson.put("user", "asdasd")

        val likesJsonArray=JSONArray()
        likesJsonArray.put(likeJson)

        val dataObject=JSONObject()
        dataObject.put("likes", likesJsonArray)

        jsonObject.put("data", dataObject)
    }

    @Test
    fun testGetUserFromLikesWithNullUserManager(){
        //prepare
        val backup = worker?.userManager
        worker?.userManager = null
        val likes = listOf(Like("1", "1", Date()))
        //call
        val returnedValue = worker?.getUserFromLikes(likes)
        //assert
        assertEquals(returnedValue?.size, 0)
        //backup
        worker?.userManager = backup
    }

    @Test
    fun testGetListLikesPlayers() {
        //prepare
        val request=LikeListModel.Request("1", "1")

        //call
        this.worker?.getListLikesPlayers(request=request)

        //assert
        assertEquals(this.mock?.response?.players?.size, 1)
        assertEquals(this.mock?.response?.players!![0].name, "User test")
        assertNotNull(request)
    }

    @Test
    fun testGetListLikesPlayersWithoutMock() {
        //prepare
        val request=LikeListModel.Request("1", "1")
        UserSingleton.userType = UserType.REAL
        //call
        thread {this.worker?.getListLikesPlayers(request=request)}.join()

        //assert
        assertEquals(this.mock?.response?.players?.size, 1)
        assertEquals(this.mock?.response?.players!![0].name, "User test")
        assertNotNull(request)
    }

    @Test
    fun getUpdate() {
        //prepare and call
        val userManager=worker?.userManager
        val updateLogic=worker?.updateLogic
        //assert
        assertEquals(worker?.updateLogic, updateLogic)
        assertEquals(worker?.userManager, userManager)
    }

    @Test
    fun testJsonConvertJsonToListOfLikes() {

        println(jsonObject)

        val likes=this.worker?.convertJsonToListOfLikes(jsonObject)

        assertNotNull(likes)

    }

    @Test
    fun testNullStoryInLikes() {
        //prepare
        val tokenId="1"
        val storyId="-2"
        val request=LikeListModel.Request(tokenId, storyId)
        val value = 1
        //call
        this.worker?.getListLikesPlayers(request)

        //assert
        assertEquals(this.mock?.response?.players?.size, value)
    }

    @Test
    fun testGetListWithNullStoryManager() {
        //prepare
        val backup = worker?.storyManager
        worker?.storyManager = null
        val request = LikeListModel.Request("1", "1")
        mock?.response = null

        //call
        thread {
            worker?.getListLikesPlayers(request)
        }.join()

        //assert
        assertNotNull(mock?.response)

        //prepare
        worker?.storyManager = backup

    }

    @Test
    fun testGetListWithNullLikeManager() {
        //prepare
        val backup = worker?.likeManager
        worker?.likeManager = null
        val request = LikeListModel.Request("1", "1")
        mock?.response = null

        //call
        thread {
            worker?.getListLikesPlayers(request)
        }.join()

        //assert
        assertNotNull(mock?.response)

        //prepare
        worker?.likeManager = backup

    }

    @Test
    fun testGetListWithNullUpdateLogic() {
        //prepare
        val backup = worker?.updateLogic
        worker?.updateLogic = null
        val request = LikeListModel.Request("1", "1")
        mock?.response = null

        //call
        thread {
            worker?.getListLikesPlayers(request)
        }.join()

        //assert
        assertNull(mock?.response)

        //prepare
        worker?.updateLogic = backup

    }

    @Test
    fun testNullListOfLikes() {
        //prepare
        val likes=mutableListOf<String>()
        val value = null

        //call
        this.worker?.likeManager?.getLikesFromStory(likes)

        //assert
        assertEquals(this.mock?.response?.players?.size, value)
    }

    @Test
    fun testGetUpdateLogic() {
        //prepare and call
        val update=worker?.updateLogic

        //assert
        assertEquals(update, worker?.updateLogic)
    }

    @Test
    fun testGetUserManager() {
        //prepare and call
        val userManager=worker?.userManager

        //assert
        assertEquals(userManager, worker?.userManager)
    }

    @Test
    fun testGetLikeManager() {
        //prepare and call
        val likeManager=worker?.likeManager

        //assert
        assertEquals(likeManager, worker?.likeManager)
    }

    @Test
    fun testGetStoryManager() {
        //prepare and call
        val storyManager=worker?.storyManager

        //assert
        assertEquals(storyManager, worker?.storyManager)
    }

    @Test
    fun testInvoke() {
        mock?.response = null
        val url = URL("http://www.forever21.com/")
        val request = Request(com.github.kittinunf.fuel.core.Method.GET, "", url)
        val response = Response(url)

        val result: com.github.kittinunf.result.Result<Json, FuelError> = com.github.kittinunf.result.Result.error(FuelError(Exception("teste")))
        this.worker?.handleResultLikeList?.invoke(request, response, result)

        assertNull(mock?.response)
    }

    @Test
    fun testInvokeSuccess() {
        //prepare
    mock?.response
    val url = URL("http://www.forever21.com/")
    val request = Request(com.github.kittinunf.fuel.core.Method.GET, "", url)
    val response = Response(url)

    val json = Json(jsonObject.toString())

    val resultSuccess: com.github.kittinunf.result.Result<Json, FuelError> = com.github.kittinunf.result.Result.Success(json)
    //call
    thread {worker?.handleResultLikeList?.invoke(request, response, resultSuccess) }.join()
    //assert
    assertNotNull(mock?.response)

    }

    @After
    fun tearDown() {
        this.worker=null
    }

    class MockWorkersUpdateLogic : WorkerUpdateLogic {

        var response: LikeListModel.Response?=null

        override fun updateUsers(response: LikeListModel.Response) {
            this.response=response
        }
    }

}