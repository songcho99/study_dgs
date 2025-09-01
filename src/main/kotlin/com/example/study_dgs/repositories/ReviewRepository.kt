package com.example.study_dgs.repositories

import com.example.study_dgs.entities.Review
import org.springframework.data.jpa.repository.JpaRepository

interface ReviewRepository: JpaRepository<Review, Long> {
}