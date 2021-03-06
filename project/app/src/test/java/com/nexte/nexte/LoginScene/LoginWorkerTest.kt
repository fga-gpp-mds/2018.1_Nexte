package com.nexte.nexte.LoginScene

import com.github.kittinunf.fuel.android.core.Json
import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.Method
import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.result.Result
import com.nexte.nexte.Entities.User.UserAdapterSpy
import com.nexte.nexte.HelpForRealm
import org.json.JSONObject
import org.junit.After
import org.junit.Before
import com.nexte.nexte.Entities.User.UserManager

import org.junit.Assert.*
import org.junit.Test
import java.net.URL
import kotlin.concurrent.thread

class LoginWorkerTest: HelpForRealm() {

    private var worker: LoginWorker? = null
    private var updateLogicMock: MockUpdateLogic? = null
    private var jsonObject = JSONObject()
    private var userManager:  UserManager? = null

    @Before
    fun setUp() {
        super.setUpWithUser()
        super.setUpWithUserCategory()
        super.setUpRealm()
        this.userManager = UserManager(UserAdapterSpy())
        this.updateLogicMock = MockUpdateLogic()
        worker = LoginWorker()
        worker?.userManager = this.userManager
        worker?.updateLogic = this.updateLogicMock
    }

    @Test
    fun testSettersAndGetters(){
        //prepare  and call
        val updateLogic = this.worker?.updateLogic
        val userManager = this.worker?.userManager

        //assert
        assertEquals(worker?.updateLogic, updateLogic)
        assertEquals(worker?.userManager, userManager)
    }


    @Test
    fun testAuthenticateUserTokenEmpty(){
        //prepare
        val userName = "luis-gustavo"
        val password = "123456"
        val request = LoginModel.Authentication.Request(userName = userName, password = password)

        //call
        thread { this.worker?.authenticateUser(request = request)}.join()

        //assert
        assertNull(this.updateLogicMock?.response1)
    }

    @Test
    fun testRequestForAuth() {
        //prepare
        val token = "d453243gfecwg4"
        val request = LoginModel.AccountKit.Request(token = token)

        //call
        thread { this.worker?.requestForAuth(request = request) }.join()

        //assert
        assertNull(this.updateLogicMock?.response2)// It will never pass there
    }

    @Test
    fun testAuthenticateHandlerOnFailure() {
        //prepare
        updateLogicMock?.response1 = null
        val url = URL("http://www.randomsite.com/")
        val request = Request(Method.POST, "", url)
        val response = Response(url)
        val result: Result<Json, FuelError> = Result.error(FuelError(Exception("quero uma moto pra morrer antes dos 30")))

        //call
        worker?.authenticateHandler?.invoke(request, response, result)

        //assert
        assertNotNull(updateLogicMock?.response1)
    }

    @Test
    fun testNullUpdateLogicForAccountKit(){
        //prepare
        val backup = worker?.updateLogic
        worker?.updateLogic = null
        val request = LoginModel.AccountKit.Request("miugel")
        updateLogicMock?.response2 = null

        //call
        thread{  worker?.requestForAuth(request) }.join()

        //assert
        assertNull(updateLogicMock?.response2)

        //backup
        worker?.updateLogic = backup
    }

    @Test
    fun testNullUpdateLogicForDefaultAuthentication(){
        //prepare
        val backup = worker?.updateLogic
        worker?.updateLogic = null
        val request = LoginModel.Authentication.Request("miguel", "2343545")
        updateLogicMock?.response1 = null

        //call
        thread{  worker?.authenticateUser(request) }.join()

        //assert
        assertNull(updateLogicMock?.response1)

        //backup
        worker?.updateLogic = backup
    }


    @Test
    fun testDefineBodyForUserAuth() {
        //prepare
        val json = JSONObject()

        val appJson = JSONObject()
        appJson.put("version", "1.0.0")
        appJson.put("type", "android")

        val loginContent = JSONObject()
        loginContent.put("username", "miguel")
        loginContent.put("password", "123456")
        val loginJson = JSONObject().put("login", loginContent)

        json.put("app", appJson)
        json.put("data", loginJson)

        val finalBody = json.toString()

        //call
        val userBodyRequest = worker?.defineBodyForUserAuth("miguel", "123456")

        //assert
        assertEquals(finalBody, userBodyRequest)
    }

    @Test
    fun testRequestAuthenticateHandlerOnFailure() {
        //prepare
        updateLogicMock?.response1 = null
        val url = URL("http://www.randomsite.com/")
        val request = Request(Method.GET, "",url)
        val response = Response(url)
        val result: Result<Json, FuelError> = Result.error(FuelError(Exception("quero uma moto pra morrer antes dos 30")))

        //call
        thread { worker?.authenticateHandler?.invoke(request, response, result) }.join()

        //assert
        assertNotNull(updateLogicMock?.response1)
    }


    @Test
    fun testRequestAuthHandlerOnFailure() {
        //prepare
        updateLogicMock?.response2 = null
        val url = URL("http://www.randomsite.com/")
        val request = Request(Method.GET, "",url)
        val response = Response(url)
        val result: Result<Json, FuelError> = Result.error(FuelError(Exception("quero uma moto pra morrer antes dos 30")))

        //call
        thread { worker?.requestAuthHandler?.invoke(request, response, result) }.join()

        //assert
        assertNotNull(updateLogicMock?.response2)
    }

    @Test
    fun testDefineBodyForAccountKitAuth() {
        val json = JSONObject()
        val token = "2e5tn4ugnreiu"

        val mockRequest = worker?.defineBodyForAccountKitAuth(token)

        json.put("data", JSONObject().put("fbAuthCode",  token))


        assertEquals(mockRequest, json.toString())
    }


    @After
    fun tearDown() {
        worker = null
    }
}

class MockUpdateLogic: LoginWorkerUpdateLogic {

    var response1: LoginModel.Authentication.Response? = null
    var response2: LoginModel.AccountKit.Response? = null

    override fun authenticateUser(response: LoginModel.Authentication.Response) {
        this.response1 = response
    }

    override fun requestAuth(response: LoginModel.AccountKit.Response) {
        this.response2 = response
    }
}