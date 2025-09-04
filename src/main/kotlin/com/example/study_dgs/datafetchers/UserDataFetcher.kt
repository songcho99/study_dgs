package com.example.study_dgs.datafetchers

import com.example.study_dgs.entities.Review
import com.example.study_dgs.entities.User
import com.example.study_dgs.repositories.UserRepository
import com.netflix.dgs.codegen.generated.DgsConstants
import com.netflix.dgs.codegen.generated.types.AddUserInput
import com.netflix.graphql.dgs.*

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

    @DgsData(parentType = DgsConstants.REVIEW.TYPE_NAME, field = DgsConstants.REVIEW.User)
    fun getUserByReview(
        dfe: DgsDataFetchingEnvironment
    ): User {
        val review = dfe.getSourceOrThrow<Review>()
        return userRepository.findById(review.user?.id!!).get()
    }
}