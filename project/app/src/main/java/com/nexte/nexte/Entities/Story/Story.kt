package com.nexte.nexte.Entities.Story
import android.annotation.SuppressLint
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

open class Story(var id: String? = null,
                 var winner: StoryPlayer? = null,
                 var loser: StoryPlayer? = null,
                 var date: Date? = null,
                 var commentsId: List<String> = listOf(),
                 var likesId: List<String> = listOf()){

    enum class ServerRequest(val request: Map<String, String>) {
        STORIES(hashMapOf("route" to "stories", "method" to "get"))
    }

    companion object {

        @SuppressLint("SimpleDateFormat")
                /**
                 * Method used to tranform a jsonObject(received from api) to a Story
                 *
                 * @param jsonStory jsonObject that has story data
                 *
                 * @return story created from json
                 */
        fun createStoryFromJsonObject(jsonStory: JSONObject): Story {
            val id = jsonStory["id"] as String
            val challengeJson = jsonStory["challenge"] as JSONObject

            //Winner and its attributes
            val winnerJson = challengeJson["winner"] as JSONObject
            val winnerId = winnerJson.optString("userID")
            val winnerSetResult = winnerJson["setResult"] as Int
            val winner = StoryPlayer(userId = winnerId, setResult = winnerSetResult)

            //Loser and its attributes
            val loserJson = challengeJson["loser"] as JSONObject
            val loserId = loserJson.optString("userID")
            val loserSetResult = loserJson["setResult"] as Int
            val loser = StoryPlayer(userId = loserId, setResult = loserSetResult)

            //Loop to build list from comments array
            val commentsIdsJsonArray = jsonStory["comments"] as JSONArray
            var commentsIds = mutableListOf<String>()
            for (counter in 0 until commentsIdsJsonArray.length()){
                commentsIds.add(commentsIdsJsonArray.getString(counter))
            }

            //Loop to build list from likes array
            val likesIdsJsonArray = jsonStory["likes"] as JSONArray
            var likesIds = mutableListOf<String>()
            for (counter in 0 until likesIdsJsonArray.length()){
                likesIds.add(likesIdsJsonArray.getString(counter))
            }

            val date = SimpleDateFormat("yyyy-mm-dd")
                    .parse(jsonStory["date"] as String)

            return Story(id, winner, loser, date, commentsIds, likesIds)
        }
    }
}
