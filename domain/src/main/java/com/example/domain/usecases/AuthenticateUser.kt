package com.example.domain.usecases

import com.example.domain.UserRepository
import com.example.domain.models.AuthUser

class AuthenticateUser(private val userRepository: UserRepository) {
    suspend fun execute(authUser: AuthUser){
        userRepository.authUser(authUser = authUser)
    }
}