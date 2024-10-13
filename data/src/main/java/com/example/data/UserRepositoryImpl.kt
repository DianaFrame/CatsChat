package com.example.data

import com.example.domain.UserRepository
import com.example.domain.models.AuthUser
import com.example.domain.models.Chat
import com.example.domain.models.UserProfileInformation

class UserRepositoryImpl: UserRepository {
    override suspend fun getChatList(): List<Chat> {
        TODO("Not yet implemented")
    }

    override suspend fun authUser(authUser: AuthUser) {
        TODO("Not yet implemented")
    }

    override suspend fun getUserProfileInformation(): UserProfileInformation {
        TODO("Not yet implemented")
    }

    override suspend fun registerNewUser(userProfileInformation: UserProfileInformation) {
        TODO("Not yet implemented")
    }

    override suspend fun saveChangeUserProfileInformation(userProfileInformation: UserProfileInformation) {
        TODO("Not yet implemented")
    }
}