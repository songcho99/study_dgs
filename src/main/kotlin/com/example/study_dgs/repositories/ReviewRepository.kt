package com.example.study_dgs.repositories

import com.example.study_dgs.entities.Review
import jakarta.persistence.Id
import org.springframework.data.jpa.repository.JpaRepository

interface ReviewRepository: JpaRepository<Review, Long> {

    fun findByMovieId(movieId: Long): List<Review>

    fun findByUserId(userId: Long): List<Review>
}