package com.nexte.nexte.EditProfileScene

/**
 * Interface responsible to define methods that will be used to communicate between
 * worker and presenter
 */
interface GetProfileToEditBusinessLogic {

    /**
     * Method that defines profile informations that will be responsible to
     * pass the request to Worker and send the response to Presenter
     *
     * @param request Request model of edit profile that contains data to pass for Worker
     */
    fun getProfileToEdit(request: EditProfileModel.RecoverUserRequest.Request)
}

/**
 * Interface responsible to define methods that will be used to communicate between worker and presenter
 * to set the edited user information in the user
 */
interface EditProfileBusinessLogic {

    /**
     * Method that defines profile informations that will be responsible to
     * pass the request to Worker and send the response to Presenter
     *
     * @param request Request model of edit profile that contains data to pass for Worker
     */
    fun setEditedProfile(request: EditProfileModel.EditProfileRequest.Request)
}

/**
 * Class responsible to intermediate communication between worker and presenter, for both getting user
 * information to edit and set user edited information in user
 *
 * @property formatUserDataPresenter
 * @property formatErrorCodePresenter
 * @property worker
 */
class EditProfileInteractor : GetProfileToEditBusinessLogic, EditProfileBusinessLogic {

    var formatUserDataPresenter: ShowProfileToEditPresentationLogic? = null
    var formatErrorCodePresenter: SendEditedProfileDataPresentationLogic? = null
    var worker = EditProfileWorker()

    /**
     * Method responsible to get unverified logged user, get user information in worker and send
     * it to presenter for formatting
     *
     * @param request Has the unverified user information
     */
    override fun getProfileToEdit(request: EditProfileModel.RecoverUserRequest.Request){

        worker.getUserProfileToEdit(request) { response ->
            formatUserDataPresenter?.presentProfileToEdit(response)
        }
    }

    /**
     * Method responsible to get edited data from fragment, and send to worker for update the existing user
     *
     * @param request Has the edited user information
     */
    override fun setEditedProfile(request: EditProfileModel.EditProfileRequest.Request) {

        worker.editUserProfile(request) { response ->
            formatErrorCodePresenter?.sendEditedProfileStatus(response)
        }
    }
}
