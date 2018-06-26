package com.nexte.nexte.MatchScene

/**
 * This interface defines the methods that will be used to intermediate
 * communication between worker and presenter
 */
interface MatchBusinessLogic {



    /**
     * Method that pass the request data about the match result to the worker
     *
     * @param request Request that came from sendChallenge button
     */
    fun getMatchResult(request: MatchModel.SendMatchResult.Request)

    fun declineMatchResult(request: MatchModel.DeclineChallengeRequest.Request)

    fun getSentChallenge(request: MatchModel.InitScene.Request)

}

/**
 * This class defines how the communication between worker and presenter will happen
 *
 * Class that implements [MatchModel] and is responsible for the communication
 * between Worker and Presenter
 *
 * @property worker it's a reference for worker that will receives the request and
 * create an response
 * @property presenter it's a reference for presenter that will receive the response and
 * create a viewModel
 */
class MatchInteractor(var presenter : MatchPresentationLogic? = null) :
        MatchBusinessLogic, MatchUpdateWorkerLogic {

    var worker = MatchWorker()

    override fun getSentChallenge(request: MatchModel.InitScene.Request) {
        worker.getUserChallenges(request)
    }

    override fun getMatchResult(request: MatchModel.SendMatchResult.Request) {
        worker.generateMatchResult(request)
    }

    override fun getMatchResultResponse(response: MatchModel.SendMatchResult.Response) {
        if (response.status == MatchModel.SendMatchResult.Status.SUCESSED){
            this.presenter?.presentSuccessMessageForMatchResult(response)
        } else {
            this.presenter?.presentErrorMessageForMatchResult(response)
        }
    }

    override fun declineMatchResult(request: MatchModel.DeclineChallengeRequest.Request) {
        worker.declineMatch(request)
    }

    override fun declineMatchResultResponse(response: MatchModel.DeclineChallengeRequest.Response) {
        this.presenter?.presentDeclineMatch(response)
    }

    override fun updateSentChallenge(response: MatchModel.InitScene.Response) {
        presenter?.presentMatch(response)
    }

}
