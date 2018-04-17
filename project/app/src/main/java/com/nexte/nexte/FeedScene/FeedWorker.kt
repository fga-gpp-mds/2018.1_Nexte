package com.nexte.nexte.FeedScene

import com.nexte.nexte.MainActivity
import com.nexte.nexte.R
import java.util.*
import kotlin.collections.ArrayList

/**
 * Class responsible to do request for anywhere, format response and
 * call completion to send data for called class
 */
class FeedWorker {

    /**
     * Function to fetch feed data of server
     *
     * @param request Feed model request that contains need information to send for server
     * @param completion Method for call on parent class
     */
    fun fetchFeedData(request: FeedModel.GetFeedActivities.Request,
                      completion: (FeedModel.GetFeedActivities.Response) -> Unit) {
        val response = FeedModel.GetFeedActivities.Response(FeedManager.feedList)
        completion(response)
    }

    /**
     * Function that receives the unformattedActivity from Interactor and sends it
     * unformatted Activity to presenter as a completion, after calling the function to add
     * or remove users from likes list
     *
     * @param request String as the identifier to find the activity to be altered
     * @param completion unformatted activity sent to interactor
     */
    fun manageLikes(request: FeedModel.LikeAndUnlike.Request,
                    completion: (FeedModel.LikeAndUnlike.Response) -> Unit) {

        var activity = FeedManager.getFeedActivity(request.identifier)
        activity = FeedManager.addAndRemoveUser(activity)

        val updatedActivity = FeedModel.LikeAndUnlike.Response(
                FeedManager.updateFeedActivity(request.identifier, activity))

        completion(updatedActivity)
    }


}