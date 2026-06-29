package com.example.studyapp.feature.users

import com.example.studyapp.data.model.UserApiModel
import com.example.studyapp.domain.model.User

sealed interface UserUiState {
    // A Object is a singleton, so it will be only one instance of Loading while the app is running
    object Loading : UserUiState // Loading user data from the repository

    // Data classes need to be instantiated with different data everytime, it shouldn't be Objects
    // "data class" instead of only "class" is optimized. If the value is equal to the last
    // one, compose does not trigger the recomposition (that is done under the hood)
    data class SuccessLoadingUsers(val users: List<User>) : UserUiState // User list loaded and available
    // Error state also uses a data class to ensure the message is compared during recomposition
    data class ErrorLoadingUsers(val message: String) : UserUiState // Error retrieving User List
}
