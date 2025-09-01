package com.example.study_dgs.dataFetchers

import com.example.study_dgs.entities.Movie
import com.example.study_dgs.repositories.MovieRepository
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsData
import com.netflix.graphql.dgs.DgsQuery
import com.netflix.graphql.dgs.InputArgument

@DgsComponent
class MovieDataFetcher(
    private val movieRepository: MovieRepository
) {

//    @DgsData(parentType = "query", field = "movies")
    @DgsQuery
    fun movies(): MutableList<Movie> {
        return movieRepository.findAll()
    }

    @DgsQuery
    fun movie(
        @InputArgument movieId: Long
    ): Movie {
        return movieRepository.findById(movieId).orElseThrow {Exception("Movie not found")}
    }
}