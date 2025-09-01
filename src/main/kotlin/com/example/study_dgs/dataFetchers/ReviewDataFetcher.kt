package com.example.study_dgs.dataFetchers

import com.example.study_dgs.entities.Review
import com.example.study_dgs.repositories.ReviewRepository
import com.netflix.dgs.codegen.generated.types.AddReviewInput
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsMutation
import com.netflix.graphql.dgs.DgsSubscription
import com.netflix.graphql.dgs.InputArgument
import jakarta.persistence.Id
import reactor.core.publisher.Flux
import reactor.core.publisher.Sinks
import reactor.util.concurrent.Queues

@DgsComponent
class ReviewDataFetcher(
    private val userDataFetcher: UserDataFetcher,
    private val movieDataFetcher: MovieDataFetcher,
    private val reviewRepository: ReviewRepository
) {

    @DgsMutation
    fun addReview (
        @InputArgument input: AddReviewInput
    ): Review {
        val user = userDataFetcher.user(input.userId)
        val movie = movieDataFetcher.movie(input.movieId)
        val review = Review (
            rating = input.rating,
            comment = input.comment,
            user = user,
            movie = movie
        )
        reviewRepository.save(review)

        reviewSink.tryEmitNext(review)
        return review
    }


    private val reviewSink = Sinks
        .many()
        .multicast()
        .onBackpressureBuffer<Review>(Queues.SMALL_BUFFER_SIZE, false)


    @DgsSubscription
    fun newReview (
        @InputArgument movieId: Long
    ): Flux<Review> {
        return reviewSink.asFlux()
            .filter { it.movie?.id == movieId }
    }
}