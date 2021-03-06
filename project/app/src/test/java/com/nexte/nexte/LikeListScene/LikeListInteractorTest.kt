package com.nexte.nexte.LikeListScene

import com.facebook.accountkit.LoginModel
import com.nexte.nexte.Entities.Like.LikeAdapterSpy
import com.nexte.nexte.Entities.Like.LikeManager
import com.nexte.nexte.Entities.Story.StoryAdapterSpy
import com.nexte.nexte.Entities.Story.StoryManager
import com.nexte.nexte.Entities.User.User
import com.nexte.nexte.Entities.User.UserAdapterSpy
import com.nexte.nexte.Entities.User.UserManager
import org.junit.After
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test
import kotlin.concurrent.thread

class LikeListInteractorTest {

    private var mock: MockLikeListPresentationLogic? = null
    private var interactor: LikeListInteractor? = null
    private var mockUpdateResponseLogic: MockWorkerUpdateLogic? = null

    @Before
    fun setUp() {
        this.mock = MockLikeListPresentationLogic()
        this.mockUpdateResponseLogic = MockWorkerUpdateLogic()
        this.interactor = LikeListInteractor(presenter = mock)
        this.mockUpdateResponseLogic?.mock = mock
        this.interactor?.worker?.updateLogic = this.mockUpdateResponseLogic
        this.interactor?.worker?.userManager = UserManager(UserAdapterSpy())
        this.interactor?.worker?.likeManager = LikeManager(LikeAdapterSpy())
        this.interactor?.worker?.storyManager = StoryManager(StoryAdapterSpy())
    }

    @Test
    fun testFetchDataToList(){
        //prepare
        val request = LikeListModel.Request("1", "1")

        //call
        thread { this.interactor?.fetchDataToList(request = request) }.join()
        request.storyId
        request.tokenId

        //assert
        assertEquals(this.mock?.passedHere, true)
    }

    @Test
    fun testGetUsers(){
        //prepare
        val response = LikeListModel.Response(listOf())

        //call
        this.interactor?.updateUsers(response = response)

        //assert
        assertEquals(this.mock?.passedHere, true)
    }

    @Test
    fun testNullPresenter() {
        //prepare and call
        this.interactor?.presenter = null
        this.mock?.passedHere = false
    }

    @Test
    fun testConstructorInteractor() {
        //prepare and call
        val interactor = LikeListInteractor()

        //assert
        assertNotNull(interactor)
    }

    @Test
    fun testUpdateUsersWithNullPresenter(){
        //prepare
        mock?.passedHere = false
        val backup = interactor?.presenter
        interactor?.presenter = null
        val response = LikeListModel.Response(listOf())
        //call
        interactor?.updateUsers(response)
        //assert
        assertFalse(mock?.passedHere!!)
        //backup
        interactor?.presenter = backup
    }

    @Test
    fun successSetWorkerTest() {
        //prepare
        val newWorker = LikeListWorker()

        //call
        this.interactor?.worker = newWorker

        //assert
        assertEquals(newWorker, interactor?.worker)
    }

    @After
    fun tearDown() {
        this.mock = null
        this.interactor = null
        this.mock?.passedHere = false
    }
}

private class MockLikeListPresentationLogic: LikeListPresentationLogic{

    var passedHere = false

    override fun formatLikeList(response: LikeListModel.Response) {
        this.passedHere = true
    }
}

private class MockWorkerUpdateLogic : WorkerUpdateLogic{
    var mock: MockLikeListPresentationLogic? = null

    override fun updateUsers(response: LikeListModel.Response) {
        this.mock?.formatLikeList(response = response)
    }
}