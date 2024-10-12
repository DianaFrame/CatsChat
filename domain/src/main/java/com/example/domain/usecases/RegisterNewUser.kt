package com.example.domain.usecases

import com.example.domain.UserRepository
import com.example.domain.models.UserProfileInformation

class RegisterNewUser(private val userRepository: UserRepository) {
    suspend fun execute(userProfileInformation: UserProfileInformation){
        userRepository.registerNewUser(userProfileInformation = userProfileInformation)
    }
}