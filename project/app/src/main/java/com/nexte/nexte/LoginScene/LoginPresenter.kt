package com.nexte.nexte.LoginScene

/**
 * Interface resposible to format request provided in Interactor to data as
 * it should be presented at fragment as viewModel
 */
interface LoginPresentationLogic {

    /**
     * Method responsible to format login data and send for fragment
     *
     * @param response It's login model response containing unformatted data
     * received [LoginModel]
     */
    fun presentLogin(response: LoginModel.Authentication.Response)

    /**
     * Method responsible to handle with authentication not expected
     *
     * @param response It's login model response containing unformatted data
     * received [LoginModel]
     */
    fun presentError(response: LoginModel.Authentication.Response)

    /**
     * Method responsible to handle with authentication provided from Facebook API
     *
     * @param response It's login model response containing unformatted data
     * received [LoginModel]
     */
    fun presentAccountKit(response: LoginModel.AccountKit.Response)

}

/**
 * Class that formats data from LoginInteractor(request) to LoginFragment(viewModel)
 *
 * @property view reference to fragment
 */
class LoginPresenter: LoginPresentationLogic {

    var view: LoginDisplayLogic? = null

    override fun presentLogin(response: LoginModel.Authentication.Response) {

        val token: String = response.tokenId
        val viewModel: LoginModel.Authentication.ViewModel = LoginModel.Authentication.ViewModel(token)
        this.view?.displayAuthenticateState(viewModel)
    }

    override fun presentError(response: LoginModel.Authentication.Response) {
        val token: String = ""
        val viewModel: LoginModel.Authentication.ViewModel = LoginModel.Authentication.ViewModel(token)
        this.view?.displayAuthenticateState(viewModel)
    }

    override fun presentAccountKit(response: LoginModel.AccountKit.Response) {
        val statusCode = response.statusCode
        var message: String = ""

        when(statusCode) {
            LoginModel.AccountKit.StatusCode.SUCESSED -> { message = LoginModel.AccountKit.StatusCode.SUCESSED.toString() }
            LoginModel.AccountKit.StatusCode.CANCELLED -> { message = "Ops, a autenticação foi cancelada" }
            LoginModel.AccountKit.StatusCode.ERROR -> { message = "Ops, houve um erro, tente novamente" }
        }

        val viewModel = LoginModel.AccountKit.ViewModel(message)
        this.view?.displayAccountKit(viewModel)
    }
}

