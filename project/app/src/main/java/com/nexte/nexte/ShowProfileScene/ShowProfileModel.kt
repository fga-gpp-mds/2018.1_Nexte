package com.nexte.nexte.ShowProfileScene

import com.nexte.nexte.Entities.User.User
import com.nexte.nexte.Player

/**
 * Class to define Model of ShowProfile Scene to enable the access to users
 * This class declares the [Request], [Response], [ViewModel] and [Player],
 * information that will define what will be passed between classes
 */
class ShowProfileModel {

    /**
     * Class responsible to pass data of View to Interactor and after to Worker so it can
     * request data
     *
     * @property userID variable that holds the name chosen by the user on the app
     * @property tokenID variable that holds the token that validates the user in the system
     **/
    class Request(var userId: String)

    /**
     * Class responsible to store received information of worker to pass for Presenter
     *
     * @property user variable that return the attributes of player after the validation step
     **/
    class Response(var user: User?)

    /**
     * Class responsible to define how the list fragment will display the formatted data, passed to fragment
     *
     * @property playerInfo attribute already processed by [ShowProfilePresenter] class for display on screen
     **/
    class ViewModel(var playerInfo: FormattedPlayer, var formattedChallenges: List<FormattedChallenge>)

    //-------------- Aux Classes --------------

    /**
     * This class holds the formatted player information that will be defined on [ShowProfilePresenter]
     *
     * @property name Name displayed
     * @property rank The ranking position displayed
     * @property club User club displayed
     * @property email User email displayed
     * @property age User age displayed
     * @property profileImage User profile Image
     */
    class FormattedPlayer(
            var name: String?,
            var rank: String?,

            var email: String?,
            var number: String?,
            var profileImage: Int?)

    /**
     * This class hold the latest played games of the user formatted
     *
     * @property challengeDates date of the played challenge
     * @property setsResult sets information of the games
     * @property gamesResults the final result of the games
     * @property headToHeadResults the information of who won more against each other
     * @property opponentName the name of the opponent player
     * @property opponentPictureUrl the picture url of the opponent player
     * @property opponentPictureUrl the color to show of the opponent player
     * @property challengeResult the information about the result for the player
     */
    class FormattedChallenge(
            var challengeDates: String,
            var setsResult: String,
            var gamesResults: String,
            var headToHeadResults: String,
            var opponentName: String?,
            var opponentPictureUrl: String?,
            var opponentPictureAdress: Int,
            var opponentColor: Int,
            var challengeResult: ChallengeResult)

    /**
     * Enum to represent the result of the challenge for the player
     */
    enum class ChallengeResult{
            WON, LOST, WO
    }

}
