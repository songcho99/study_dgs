package com.example.study_dgs.datafetchers

import com.example.study_dgs.entities.Director
import com.example.study_dgs.entities.Movie
import com.example.study_dgs.repositories.MovieRepository
import com.netflix.dgs.codegen.generated.DgsConstants
import com.netflix.graphql.dgs.*

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

    @DgsData(parentType = DgsConstants.DIRECTOR.TYPE_NAME, field = DgsConstants.DIRECTOR.Movies)
    fun getMovieByDirector(
        dfe: DgsDataFetchingEnvironment
    ): List<Movie> {
        val director = dfe.getSourceOrThrow<Director>()
        return movieRepository.findByDirectorId(director.id!!)
    }
}