package com.example.domain.usecases

import com.example.domain.UserRepository
import com.example.domain.models.UserProfileInformation

class GetUserProfileInformation(private val userRepository: UserRepository) {
    suspend fun execute(): UserProfileInformation{
        return userRepository.getUserProfileInformation()
    }
}