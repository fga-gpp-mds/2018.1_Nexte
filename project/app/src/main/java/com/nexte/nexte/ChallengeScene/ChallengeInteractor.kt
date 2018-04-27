package com.nexte.nexte.ChallengeScene

/**
 * Interface to define Business Logic to Challenge Class
 * that will used to call this Interactor on other class layer
 */
interface RequestPlayersToChallengeBusinessLogic {

    /**
     * Method that pass the request to Worker and send the response to Presenter for the request
     * responsible to get first list of players
     *
     * @param request variable equals to the server response due to the challenged response
     */
    fun requestPlayersToChallenge(request: ChallengeModel.ShowRankingPlayersRequest.Request)
    TODO ("CRIAR FUNÇÃO PARA RECEBER O SEGUNDO REQUEST")
}

/**
 * Class that implements [RequestPlayersToChallengeBusinessLogic] and is responsible for the communication*
 * @property worker Reference to worker [ChallengeWorker]
 * @property presenter Reference to presenter [ChallengePresenter]
 */
class ChallengeInteractor : RequestPlayersToChallengeBusinessLogic {

    var worker = ChallengeWorker()
    var presenter: FormatPlayersPresentationLogic? = null

    override fun requestPlayersToChallenge(request: ChallengeModel.ShowRankingPlayersRequest.Request) {

        worker.fetchPlayersToChallenge(request) { response ->
            this.presenter?.formatPlayersToChallenge(response)
        }
    }

    TODO("OVERRIDE NA SEGUNDA FUNÇÃO DA INTERFACE")
}