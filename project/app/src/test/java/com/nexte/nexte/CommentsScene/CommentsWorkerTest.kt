package com.nexte.nexte.CommentsScene

import com.nexte.nexte.Entities.Comment.Comment
import com.nexte.nexte.Entities.Comment.CommentAdapterSpy
import com.nexte.nexte.Entities.Comment.CommentManager
import com.nexte.nexte.Entities.Story.StoryAdapterSpy
import com.nexte.nexte.Entities.Story.StoryManager
import org.json.JSONArray
import org.json.JSONObject
import org.junit.After
import org.junit.Before
import org.junit.Assert.*
import org.junit.Test
import java.util.*
import kotlin.concurrent.thread
import com.github.kittinunf.result.Result



class CommentsWorkerTest {

    private var worker: CommentsWorker? = null
    var mock: MockCommentsWorkerUpdateLogic? = null

    @Before
    fun setUp() {
        this.worker = CommentsWorker()
        this.mock = MockCommentsWorkerUpdateLogic()
        this.worker?.updateLogic = mock
        this.worker?.commentsManager = CommentManager(CommentAdapterSpy())
        this.worker?.storyManager = StoryManager(StoryAdapterSpy())
    }

    @Test
    fun successGetCommentsData(){
        //prepare
        val user1 = "1"
        val userId1 = "2"
        val commentUser1 = ""
        val date1 = Date()

        val user2 = "1"
        val userId2 = "2"
        val commentUser2 = ""
        val date2 = Date()

        val comment1 = Comment(user1,
                userId1,
                commentUser1,
                date1)

        val comment2 = Comment(user2,
                userId2,
                commentUser2,
                date2)

        val request = CommentsModel.GetCommentsRequest.Request("1", "1")

        //call
        this.worker?.getCommentsData(request)

        assertEquals(comment1.id, this.mock?.response1?.comments!![0].id)
        assertEquals(comment2.id, this.mock?.response1?.comments!![1].id)

        assertEquals(comment1.userId, this.mock?.response1?.comments!![0].userId)
        assertEquals(comment2.userId, this.mock?.response1?.comments!![1].userId)

        assertEquals(comment1.comment, this.mock?.response1?.comments!![0].comment)
        assertEquals(comment2.comment, this.mock?.response1?.comments!![1].comment)


    }

    @Test
    fun djns() {
        val tokenID = ""
        val storyId = ""

        val request = CommentsModel.GetCommentsRequest.Request(tokenID, storyId)
        val story = this.worker?.storyManager?.get(request.storyId)

        assertNull(story)
    }

    @Test
    fun fsfsfs() {
        val id1 = "dhh"
        val userId1 = "uhfuf"
        val date1 = Date()
        val comments1 = "jeije"
        val id2 = "dhh"
        val userId2 = "uhfuf"
        val date2 = Date()
        val comments2 = "jeije"

        val com1 = Comment(id1, userId1,comments1, date1)
        val com2 = Comment(id2, userId2, comments2, date2)

        val comment = mutableListOf(com1, com2)

        val newResponse = CommentsModel.GetCommentsRequest.Response(comment)
        this.worker?.updateLogic?.updateComment(newResponse)

        assertEquals(this.mock?.response1, newResponse)
    }

    @Test
    fun testNullStoryInComments() {
        // prepare
        val request = CommentsModel.GetCommentsRequest.Request("kk", "-1")

        // cal
        this.worker?.getCommentsData(request)

        // assert
        assertEquals(this.mock?.response1?.comments?.size, 2)
    }

    @Test
    fun testGetUpdateLogic() {
        //prepare and call
        val updateLogic = worker?.updateLogic

        //assert
        assertEquals(updateLogic, worker?.updateLogic)
    }

    @Test
    fun testGetCommentsManager() {
        //prepare and call
        val commentsMan = worker?.commentsManager

        //assert
        assertEquals(commentsMan, worker?.commentsManager)
    }


    @Test
    fun testGetStoryManager() {
        //prepare and call
        val storyMan = worker?.storyManager

        //assert
        assertEquals(storyMan, worker?.storyManager)
    }

    @Test
    fun successSetNewComment() {
        //prepare
        val comment = ""
        val request = CommentsModel.PublishCommentRequest.Request(comment)
        val today = Date()
        val id = "1"
        val userId = "2"
        val newComment = Comment(id, userId, comment, today)

        //call
        worker?.setNewComment(request)
        //assert
        assertEquals(this.mock?.response2?.newComment?.id, newComment.id)
        assertEquals(this.mock?.response2?.newComment?.userId, newComment.userId)
        assertEquals(this.mock?.response2?.newComment?.comment, newComment.comment)


    }

    @Test
    fun successSetComplaint() {
        val request = CommentsModel.ComplaintRequest.Request(
                3
        )

        //call
        worker?.sendComplaint(request)

        //assert
        assertEquals(this.mock?.response3?.serverResponse,200)

    }

    @Test
    fun successGetToDeleteComment() {
        //prepare

        val comment1 = Comment("1",
                "2",
                "", Date())


        val requestToDel = CommentsModel.DeleteCommentRequest.Request(1)

        //call
        thread {this.worker?.getToDeleteComment(requestToDel)}.join()


        //assert
        assertEquals(comment1.id, this.mock?.response4?.delComments?.id)
        assertEquals(comment1.userId, this.mock?.response4?.delComments?.userId)
        assertEquals(comment1.comment, this.mock?.response4?.delComments?.comment)
    }

    @Test
    fun getCommentsDeleteWithNullUpdateLogic() {
        //prepare
        val backup = worker?.updateLogic
        worker?.updateLogic = null

        val comment1 = Comment("1",
                "2",
                "", Date())

        val requestToDel = CommentsModel.DeleteCommentRequest.Request(1)

        //call
        thread {this.worker?.getToDeleteComment(requestToDel)}.join()

        //assert
        assertNull(mock?.response4)

        //backup
        worker?.updateLogic = backup
    }

    @Test
    fun testJsonConvertToListOfComments() {
        val commentJson = JSONObject()
        commentJson.put("id", "mbsid")
        commentJson.put("user", "mbslet")
        commentJson.put("date", "01-03-2018T00:00:00.000Z")
        commentJson.put("comment", "Boa!")

        val commentJsonArray = JSONArray()
        commentJsonArray.put(commentJson)

        val dataObject = JSONObject()
        dataObject.put("comments",commentJsonArray)

        val jsonObject = JSONObject()
        jsonObject.put("data", dataObject)

        println(jsonObject)

        val comments = this.worker?.convertJsonToListOfComments(jsonObject)

        assertEquals(comments!![0].id, commentJson["id"] as String)
        assertEquals(comments!![0].userId, commentJson["user"] as String)
        assertEquals(comments!![0].comment, commentJson["comment"] as String)

    }


    @After
    fun tearDown() {
        this.worker = null
    }

    class MockCommentsWorkerUpdateLogic : CommentsWorkerUpdateLogic {
        var response1: CommentsModel.GetCommentsRequest.Response? = null
        var response2: CommentsModel.PublishCommentRequest.Response? = null
        var response3: CommentsModel.ComplaintRequest.Response? = null
        var response4: CommentsModel.DeleteCommentRequest.Response? = null

        override fun updateComment(response: CommentsModel.GetCommentsRequest.Response) {
            this.response1 = response
        }

        override fun updateNewComment(response: CommentsModel.PublishCommentRequest.Response) {
            this.response2 = response
            println(response)
            println(response.newComment.id)
        }


        override fun updateSendComplaint(response: CommentsModel.ComplaintRequest.Response) {
            this.response3 = response
        }

        override fun updateDeleteComment(response: CommentsModel.DeleteCommentRequest.Response) {
            this.response4 = response
        }
    }

}