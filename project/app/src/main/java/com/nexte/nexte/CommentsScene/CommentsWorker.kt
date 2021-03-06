package com.nexte.nexte.CommentsScene

import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.android.core.Json
import com.github.kittinunf.fuel.android.extension.responseJson
import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.result.Result
import com.nexte.nexte.Entities.Comment.Comment
import com.nexte.nexte.Entities.Comment.CommentManager
import com.nexte.nexte.Entities.Comment.CommentMocker
import com.nexte.nexte.Entities.Story.Story
import com.nexte.nexte.Entities.Story.StoryManager
import com.nexte.nexte.UserSingleton
import com.nexte.nexte.UserType
import org.json.JSONArray
import org.json.JSONObject
import java.util.*

interface CommentsWorkerUpdateLogic {

    fun updateComment(response: CommentsModel.GetCommentsRequest.Response)
    fun updateNewComment(response: CommentsModel.PublishCommentRequest.Response)
    fun updateDeleteComment(response: CommentsModel.DeleteCommentRequest.Response)
    fun updateSendComplaint(response: CommentsModel.ComplaintRequest.Response)
}

/**
 * Class responsible to do request for anywhere, format Response and
 * call completion to send data for called class
 */
class CommentsWorker {


    var updateLogic: CommentsWorkerUpdateLogic? = null
    var commentsManager: CommentManager? = null
    var storyManager: StoryManager? = null
    var handleResulComments: (Request, com.github.kittinunf.fuel.core.Response, Result<Json, FuelError>) -> Unit = { _, _, result ->
        when(result){
            is Result.Failure -> {
                println(result.getException())
            }

            is Result.Success -> {
                val json = result.get()
                val commentsList = convertJsonToListOfComments(json.obj())
                commentsManager?.updateMany(commentsList)
                val newResponse = CommentsModel.GetCommentsRequest.Response(
                        commentsList.toMutableList())
                updateLogic?.updateComment(newResponse)
            }
        }
    }
    /**
     * Variable created to simulate mocked data to be implemented on Package mocker
     */

    /**
     * Function to get comments data of server
     *
     * @param request Comments model request that contains needed information to send to server
     */
    fun getCommentsData (request: CommentsModel.GetCommentsRequest.Request) {
        var story = storyManager?.get(request.storyId)
        val emptyStory = Story()
        story = story ?: emptyStory
        val commentsIdsMutable = mutableListOf<String>()
        for (commentId in story.commentsId) {
            commentsIdsMutable.add(commentId)
        }
        val comments = commentsManager?.getCommentsFromStory(commentsIdsMutable.toList())
        val response = CommentsModel.GetCommentsRequest.Response(comments!!.toMutableList())
        updateLogic?.updateComment(response)

        if (UserSingleton.userType != UserType.MOCKED) {
            val url = "/stories/" + request.storyId + "/comments"
            val header = mapOf("Content-Type" to "application/json",
                    "Accept-Version" to "0.1.0")
            Fuel.get(url).header(header).responseJson(handleResulComments)
        }
    }

    /**
     * Method resposible for tranforming a jsonObject, that contains the response of the api request,
     * into a list of comments
     *
     * @param jsonObject jsonObject that contains the response data from the api
     *
     * @return list of comments
     */
    fun convertJsonToListOfComments(jsonObject: JSONObject) : List<Comment> {
        val dataJson = jsonObject["data"] as JSONObject
        val commentsJsonArray = dataJson["comments"] as JSONArray

        val commentsMutableList = mutableListOf<Comment>()
        for (counter in 0 until commentsJsonArray.length()){
            val jsonComment = commentsJsonArray.getJSONObject(counter)
            val comment = Comment.createCommentFromJsonObject(jsonComment)
            commentsMutableList.add(comment)
        }
        return commentsMutableList.toList()
    }

    /**
     * Function responsible to set new comment that contains a message, updated date and an author
     * and passed the new comment to response
     * @param request Comments model from PublishCommentRequest that contains need information to
     * send to server
     */

    fun setNewComment (request: CommentsModel.PublishCommentRequest.Request) {
        var story = storyManager?.get(request.storyId)
        val emptyStory = Story()
        story = story ?: emptyStory

        CommentMocker.newCommentsId.add(request.commentToPost)
        val numberNewComments = CommentMocker.newCommentsId.size
        val newCommentId = numberNewComments + antComments

        val message = request.commentToPost
        val today = Date()
        val author = UserSingleton.loggedUserID
        var newComment = Comment(newCommentId.toString(), author, message, today)
        newComment = commentsManager?.update(newComment)!!
        storyManager?.addComment(story, newComment.id!!)
        val response = CommentsModel.PublishCommentRequest.Response(newComment)

        updateLogic?.updateNewComment(response)



//        if (UserSingleton.userType != UserType.MOCKED) {
//            val header = mapOf("accept-version" to "0.1.0")
//            val url = "http://10.0.2.2:3000/stories/" + request.storyId + "/comments"
//            url.httpGet().header(header).responseJson(handleResulComments)
//        }
    }

    /**
     * Function responsible to submit an alert message when the user wants to report a comment
     * and passed the message to response.
     * @param request Comments model from ComplaintRequest that contains need information to
     * send to server
     */

    fun sendComplaint (request: CommentsModel.ComplaintRequest.Request) {

        val serverCode = okMessage
        val response = CommentsModel.ComplaintRequest.Response(serverCode)

        updateLogic?.updateSendComplaint(response)

    }

    /**
     * Function responsible to delete the comment at the position set by request
     * and to send the list after deletion as the response to interactor
     *
     * @param request Position of the comment to be deleted
     */
    fun getToDeleteComment (request: CommentsModel.DeleteCommentRequest.Request){

        var story = storyManager?.get(request.storyId)
        val emptyStory = Story()
        story = story ?: emptyStory

        story = storyManager?.removeComment(story, request.commentIdentifier)

        val newComments = commentsManager?.getCommentsFromStory(story?.commentsId!!)

       // val comments = this.commentsManager?.delete(request.commentIdentifier.toString())
        val response = CommentsModel.DeleteCommentRequest.Response(newComments!!.toMutableList())
       updateLogic?.updateDeleteComment(response)
    }

    companion object {
        const val antComments = 10
        const val okMessage = 200
        const val idComment = "108"
    }
}