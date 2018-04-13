package com.nexte.nexte.EditProfileScene

import com.nexte.nexte.Player
import com.nexte.nexte.UserSingleton

/**
 * Created by lorrany on 27/03/18.
 */


/**
 * Class responsible to get a request and generate a response
 */
class EditProfileWorker {

    /**
     * Gets and username and returns user information
     *
     * @param request Contains the username and TokenID that will be used to recover the user
     */
    fun getUserProfileToEdit(request: EditProfileModel.FirstRequest.Request, completion:
        (EditProfileModel.FirstRequest.Response) -> Unit) {

        val username = request.username
        val tokenID = request.tokenID

        val emptyUser = Player("",
            -1,
            "",
            "",
            "",
            "",
            -1,
            "")

        var returnedUser: Player? = null

        // This condition verify if exist a user
        if(tokenID == "") {
            returnedUser = emptyUser
            UserSingleton.setUserInformations(emptyUser)
        } else if(username == "gabrielalbino") {
            returnedUser = UserSingleton.getUserInformations()
        }

        val response: EditProfileModel.FirstRequest.Response = EditProfileModel.FirstRequest.Response(returnedUser!!)

        completion(response)
    }

    /**
     * Responsible to get edited user information, validate it and if there is no error, set it
     * to our user, otherwise sends and error code.
     *
     * @param request Contains the edited user information
     */
    fun editUserProfile(request: EditProfileModel.SecondRequest.Request, completion:
                       (EditProfileModel.SecondRequest.Response) -> Unit) {

        val MINPASSWORDLENGTH = 6
        val user = request.user
        var errorID: Int? = null
        var newUser: Player? = null

        if(!user.email.contains('@')) {
            errorID = 1
        } else if(user.password.isNotEmpty() && user.password.length < MINPASSWORDLENGTH) {
            errorID = 2
        } else {
            newUser = user
            if(user.password.isEmpty()) {
                user.password = UserSingleton.getUserInformations().password
            }
        }

        val response: EditProfileModel.SecondRequest.Response =
                EditProfileModel.SecondRequest.Response(errorID, newUser)

        completion(response)
    }
}