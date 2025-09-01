package com.example.study_dgs.repositories

import com.example.study_dgs.entities.Movie
import org.springframework.data.jpa.repository.JpaRepository

interface MovieRepository: JpaRepository<Movie, Long> {
}