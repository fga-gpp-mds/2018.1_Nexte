package com.nexte.nexte.ChallengeScene

/**
 * Interface to define Presentation Logic to Challenge Class that
 * will be used to call this Interactor on other classes layer
 */
interface FormatPlayersPresentationLogic {
    /**
     * Method responsible to present challenge data and send it to View
     *
     * @param response contains unformatted data received from [ChallengeModel]
     */
    fun formatPlayersToChallenge(response : ChallengeModel.ShowRankingPlayersRequest.Response)

    fun formatExpandedChallengedInfo(response: ChallengeModel.SelectPlayerForChallengeRequest.Response)

}

/**
* This class will be responsible to receive a [ChallengeModel.ShowRankingPlayersRequest.Response]
* and generate a [ChallengeModel.ShowRankingPlayersRequest.ViewModel], sending it to View
*
* @property viewChallenge Reference to display formatted data
*/

class ChallengePresenter : FormatPlayersPresentationLogic {

    var viewChallenge: ShowPlayersToChallengeDisplayLogic? = null

    /**
     * This method is responsible for formatting data contained on
     * [ChallengeModel.ShowRankingPlayersRequest.Response] and send it to View
     *
     * @param response contains unformatted data received from [ChallengeModel]
     */
    override fun formatPlayersToChallenge(response: ChallengeModel.ShowRankingPlayersRequest.Response) {
        val selectedPlayers = response.fiveUsersAbove

        var formattedPlayers: List<ChallengeModel.FormattedPlayer> = listOf()

        for(player in selectedPlayers){
            val formattedPlayer = ChallengeModel.FormattedPlayer(player.name,
                    String.format("#%d", player.rankingPosition), player.pictureAddress)

            formattedPlayers += formattedPlayer
        }

        val viewModel = ChallengeModel.ShowRankingPlayersRequest.ViewModel(formattedPlayers)

        viewChallenge?.displayPlayersToChallenge(viewModel)
    }

    override fun formatExpandedChallengedInfo(response: ChallengeModel.SelectPlayerForChallengeRequest.Response) {
        val selectedChallenged = response.challengedPersonalDetails

        val formattedChallengedExpanded = ChallengeModel.FormattedRankingDetails(name = "",
                                                                                wins = "%d %",
                                                                                losses = "%d %",
                                                                                rankingPosition = "# %d")

        // tô lembrada que temos que fazer a transformação de string aqui, mas não lembro como faz


        val viewModel = ChallengeModel.SelectPlayerForChallengeRequest.ViewModel(ChallengeModel.FormattedRankingDetails())

    }

}