package com.example.study_dgs.dataFetchers

import com.example.study_dgs.entities.User
import com.example.study_dgs.repositories.UserRepository
import com.netflix.dgs.codegen.generated.types.AddUserInput
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsMutation
import com.netflix.graphql.dgs.DgsQuery
import com.netflix.graphql.dgs.InputArgument

@DgsComponent
class UserDataFetcher(private val userRepository: UserRepository) {

    @DgsQuery
    fun user (
        @InputArgument userId: Long
    ): User {
        return userRepository.findById(userId).orElseThrow { Exception("User not found") }
    }

    @DgsMutation
    fun addUser(
        @InputArgument input: AddUserInput
    ): User {
        val user = User (
            username = input.username,
            email = input.email
        )
       return userRepository.save(user)
    }
}