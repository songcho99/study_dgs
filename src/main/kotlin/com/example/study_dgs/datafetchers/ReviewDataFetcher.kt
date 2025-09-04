package com.example.study_dgs.datafetchers

import com.example.study_dgs.entities.Movie
import com.example.study_dgs.entities.Review
import com.example.study_dgs.entities.User
import com.example.study_dgs.repositories.ReviewRepository
import com.netflix.dgs.codegen.generated.DgsConstants
import com.netflix.dgs.codegen.generated.types.AddReviewInput
import com.netflix.graphql.dgs.*
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

    @DgsData(parentType = DgsConstants.MOVIE.TYPE_NAME, field = DgsConstants.MOVIE.Reviews)
    fun getReviewsByMovie(
        dfe: DgsDataFetchingEnvironment
    ): List<Review> {
        val movie = dfe.getSourceOrThrow<Movie>()

        return reviewRepository.findByMovieId(movie.id!!)
    }

    @DgsData(parentType = DgsConstants.USER.TYPE_NAME, field = DgsConstants.USER.Reviews)
    fun getReviewsByUser(
        dfe: DgsDataFetchingEnvironment
    ): List<Review> {
        val user = dfe.getSourceOrThrow<User>()
        return reviewRepository.findByUserId(user.id!!)
    }
}