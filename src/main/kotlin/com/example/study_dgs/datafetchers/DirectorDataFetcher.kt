package com.example.study_dgs.datafetchers

import com.example.study_dgs.entities.Director
import com.example.study_dgs.entities.Movie
import com.example.study_dgs.repositories.DirectorRepository
import com.netflix.dgs.codegen.generated.DgsConstants
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsData
import com.netflix.graphql.dgs.DgsDataFetchingEnvironment

@DgsComponent
class DirectorDataFetcher (
    private val directorRepository: DirectorRepository
) {
    @DgsData(parentType = DgsConstants.MOVIE.TYPE_NAME, field = DgsConstants.MOVIE.Director)
    fun getDirectorByMovie(
        dfe: DgsDataFetchingEnvironment
    ): Director {
//        val movie = dfe.getSource<Movie>() ?: throw Exception("Cannot find movie")
        val movie = dfe.getSourceOrThrow<Movie>()

        return directorRepository.findById(movie.director?.id!!).get()
    }
}