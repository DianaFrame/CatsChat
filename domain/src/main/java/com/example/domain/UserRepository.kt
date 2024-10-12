package com.example.domain

import com.example.domain.models.AuthUser
import com.example.domain.models.Chat
import com.example.domain.models.UserProfileInformation

interface UserRepository {

    suspend fun getChatList(): List<Chat>

    suspend fun authUser(authUser: AuthUser)

    suspend fun getUserProfileInformation(): UserProfileInformation

    suspend fun registerNewUser(userProfileInformation: UserProfileInformation)

    suspend fun saveChangeUserProfileInformation(userProfileInformation: UserProfileInformation)

}