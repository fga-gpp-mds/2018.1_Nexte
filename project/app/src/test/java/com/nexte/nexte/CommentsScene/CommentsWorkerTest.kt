package com.nexte.nexte.CommentsScene

import com.nexte.nexte.Entities.Comment.Comment
import com.nexte.nexte.R
import com.nexte.nexte.UserSingleton
import org.junit.After
import org.junit.Before
import org.junit.Assert.*
import org.junit.Test
import java.util.*

class CommentsWorkerTest {

    private var worker: CommentsWorker? = null

    @Before
    fun setUp() {
        this.worker = CommentsWorker()
    }

    @Test
    fun successGetCommentsData(){
        //prepare
        val user1 = "123abc"
        val userId1 = "fulano123"
        val commentUser1 = "Mas cê ta brava?"
        val date1 = Date()

        val user2 = "456def"
        val userId2 = "ciclano456"
        val commentUser2 = "Legal ein"
        val date2 = Date()

        val comment1 = Comment(user1,
                userId1,
                commentUser1,
                date1)

        val comment2 = Comment(user2,
                userId2,
                commentUser2,
                date2)

        val commentsList = mutableListOf(comment1, comment2)


        //assert

//        val request = CommentsModel.GetCommentsRequest.Request()
//
//        val comment1 = Comment("123456",
//                "le123",
//                "Boa!!",
//                         Date())
//        val comment4 = Comment("ria123",
//                "maria123",
//                      "Go!",
//                      Date())


        //call
        this.worker?.getCommentsData(request, {response ->

            assertEquals(comment1.id, response.comments[0].id)
            assertEquals(comment2.id, response.comments[1].id)

            assertEquals(comment1.userId, response.comments[0].userId)
            assertEquals(comment2.userId, response.comments[1].userId)

            assertEquals(comment1.comment, response.comments[0].comment)
            assertEquals(comment2.comment, response.comments[1].comment)

            assertEquals(comment1.date, response.comments[0].date)
            assertEquals(comment2.date, response.comments[1].date)
            )}
    }

    @Test
    fun successSetNewComment() {
        //prepare
        val comment = "Jogo Fantástico"
        val request = CommentsModel.PublishCommentRequest.Request(comment)
        val today = Date()
        val author = CommentsModel.Player(UserSingleton.getUserInformations().name, 3)
        val newComment = Comment("54633jp", "angelo", "eae", Date())

        //call
        worker?.setNewComment(request, {response ->
            //assert
            assertEquals(response.newComment.author.name,newComment.author.name)
            assertEquals(response.newComment.comment, newComment.comment)
            assertEquals(response.newComment.commentId, newComment.commentId)
        })
    }

    @Test
    fun successSetComplaint() {
        val request = CommentsModel.ComplaintRequest.Request(
                3
        )

        //call
        worker?.sendComplaint(request, {response ->
            //assert
            assertEquals(response.serverResponse,200)
        })
    }

    @Test
    fun successGetToDeletComment() {
        //prepare

        val comment1 = Comment("hahaha",
                "lehaha",
                "Joga muito", Date())


        val commentsList = Comment("hahaha", "lehaha", "Joga muito", Date())

        //call
        worker?.getToDeleteComment(requestToDel) {response ->

        //assert
        assertEquals(comment1.id, response.delComments.id)

        assertEquals(comment1.userId, response.delComments.userId)

        assertEquals(comment1.comment, response.delComments.comment)

        assertEquals(comment1.date, response.delComments.date)


            )}


   // worker?.getToDeleteComment(requestToDel) {response ->

    @After
    fun tearDown() {
        this.worker = null
    }
}