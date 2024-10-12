package com.example.domain.usecases

import com.example.domain.UserRepository
import com.example.domain.models.Chat

class GetChatsList(private val userRepository: UserRepository) {
    suspend fun execute(): List<Chat>{
        return userRepository.getChatList()
    }
}