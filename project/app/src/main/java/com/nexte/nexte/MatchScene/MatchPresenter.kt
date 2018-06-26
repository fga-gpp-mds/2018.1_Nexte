package com.nexte.nexte.MatchScene

import com.nexte.nexte.Entities.Challenge.Challenge
import com.nexte.nexte.Entities.Challenge.ChallengeManager
import com.nexte.nexte.Entities.User.User
import com.nexte.nexte.Entities.User.UserManager
import com.nexte.nexte.UserSingleton

/**
 * Created by leticia on 01/05/18.
 */

/**
 * Interface to define Presentation Logic to Match Class that
 * will be used to call this Interactor on other class layer
 */
interface MatchPresentationLogic {

    /**
     * Method responsible to format match data information and send to view
     *
     * @param response contains unformatted data received from [MatchModel]
     */
    fun presentMatch (response: MatchModel.InitScene.Response)

    /**
     * Method responsible for creating the appropriate message for
     * a success match result
     *
     * @param response contains the data about the status of the match result
     */
    fun presentSuccessMessageForMatchResult (response: MatchModel.SendMatchResult.Response)

    /**
     * Method responsible for creating the appropriate message for
     * a failed match result
     *
     * @param response contains the data about the status of the match result
     */
    fun presentErrorMessageForMatchResult (response: MatchModel.SendMatchResult.Response)

    /**
     * Method responsible for creating the appropriate message for
     * a decline match result
     *
     * @param response contains the data about the status of the decline match result
     */
    fun presentDeclineMatch(response: MatchModel.DeclineChallengeRequest.Response)
}

/**
 * Class needed to format response so the data can be displayed on activity at [MatchFragment]
 *
 * @property viewController Reference to the activity where data will be displayed on view
 */
class MatchPresenter(var viewController: MatchDisplayLogic? = null) : MatchPresentationLogic {

    var challengeManager: ChallengeManager? = null
    var userManager: UserManager? = null

    override fun presentMatch(response: MatchModel.InitScene.Response) {

        val viewModel = MatchModel.InitScene.ViewModel(formatMatch(response.match))

        viewController?.displayMatch(viewModel)
    }

    /**
     * Function that formats data of players to be displayed on [MatchFragment]
     *
     * @param toFormat Data at unformatted stage that needs to be formatted
     */

//    private fun formatMatch(user: User) : List<MatchModel.FormattedMatchData> {
//
//        var challengesSent = challengeManager?.getSentChallengeFromRealm(user.id)
//        val challengeModelChallengesMutable = mutableListOf<MatchModel.FormattedMatchData>()
//
//        if (challengesSent == null) {
//            challengesSent = listOf()
//        } else {
//            challengesSent
//        }
//
//        for (challenge in challengesSent) {
//            val userId = user.id
//            val userName = user.name
//            val userPhoto = user.profilePicture
//            val challengeSent = user.challengeSended
//            val challengeReceived = user.challengeReceived
//
////            val challengerId = challenge.challengerId
////            val challengedId = challenge.challengedId
////            val challengeDate = challenge.challengeDate
////            val challengeStatus = challenge.status
//
//
//            val matchFormatted = MatchModel.FormattedMatchData(userName,
//                    userPhoto.toInt()
//                                                               )
//
//            challengeModelChallengesMutable.add(matchFormatted)
//        }
//        return challengeModelChallengesMutable.toList()
//
    private fun formatMatch(toFormat: MatchModel.MatchData, user: User): List<MatchModel.FormattedMatchData> {

        var challengesSent = challengeManager?.getSentChallengeFromRealm(user.id)
        val challengeModelChallengesMutable = mutableListOf<MatchModel.FormattedMatchData>()

        for (challenge in challengeModelChallengesMutable){
            if (challengesSent == null) {
                challengesSent = listOf()
            }
            else {
                // Do Nothing
            }
        val challengedName = toFormat.challenged.name
        val challengedPhoto = toFormat.challenged.photo
        val challengerName = toFormat.challenger.name
        val challengerPhoto = toFormat.challenger.photo


        val matchFormatted = MatchModel.FormattedMatchData(challengedName,
                challengedPhoto,
                challengerName,
                challengerPhoto)

    }
        challengeModelChallengesMutable.add()
        return challengeModelChallengesMutable.toList()
}

    override fun presentSuccessMessageForMatchResult(response: MatchModel.SendMatchResult.Response) {
        val message = "Resultado do desafio enviado com sucesso!"
        val viewModel = MatchModel.SendMatchResult.ViewModel(message)
        this.viewController?.displayMatchResultMessage(viewModel)
    }

    override fun presentErrorMessageForMatchResult(response: MatchModel.SendMatchResult.Response) {
        val message = "Resultado do desafio não pode ser enviado. Por favor, tente novamente mais tarde."
        val viewModel = MatchModel.SendMatchResult.ViewModel(message)
        this.viewController?.displayMatchResultMessage(viewModel)
    }

    override fun presentDeclineMatch(response: MatchModel.DeclineChallengeRequest.Response) {
        var viewModel: MatchModel.DeclineChallengeRequest.ViewModel? = null
        var message: String? = null

        if (response.status == MatchModel.DeclineChallengeRequest.Status.SUCCESS){
            message = ""
        }else{
            message = "Não foi possível cancelar esse desafio!"
        }

        viewModel = MatchModel.DeclineChallengeRequest.
                ViewModel(response.status, message)
        viewController?.displayDeclineMatch(viewModel)
    }
}