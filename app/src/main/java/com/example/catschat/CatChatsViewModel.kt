package com.example.catschat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.data.UserRepositoryImpl
import com.example.domain.models.Chat
import com.example.domain.usecases.AuthenticateUser
import com.example.domain.usecases.GetChatsList
import com.example.domain.usecases.GetUserProfileInformation
import com.example.domain.usecases.RegisterNewUser
import com.example.domain.usecases.SaveChangeUserProfileInformation
typealias Chats = List<Chat>


class CatChatsViewModel(
    private val getChatsList: GetChatsList,
    private val getUserProfileInformation: GetUserProfileInformation,
    private val authenticationUser: AuthenticateUser,
    private val registerNewUser: RegisterNewUser,
    private val saveChangeUserProfileInformation: SaveChangeUserProfileInformation,
): ViewModel() {

    companion object{
        @Suppress("UNCHECKED_CAST")
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory{
            private val userRepositoryImpl = UserRepositoryImpl()
            private val getChatsList = GetChatsList(userRepository = userRepositoryImpl)
            private val getUserProfileInformation = GetUserProfileInformation(userRepository = userRepositoryImpl)
            private val authenticationUser = AuthenticateUser(userRepository = userRepositoryImpl)
            private val registerNewUser = RegisterNewUser(userRepository = userRepositoryImpl)
            private val saveChangeUserProfileInformation = SaveChangeUserProfileInformation(userRepository = userRepositoryImpl)
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return CatChatsViewModel(
                    getChatsList = getChatsList,
                    getUserProfileInformation = getUserProfileInformation,
                    authenticationUser = authenticationUser,
                    registerNewUser = registerNewUser,
                    saveChangeUserProfileInformation = saveChangeUserProfileInformation
                ) as T
            }
        }

    }

}

