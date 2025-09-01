package com.example.study_dgs.repositories

import com.example.study_dgs.entities.Director
import org.springframework.data.jpa.repository.JpaRepository

interface DirectorRepository: JpaRepository<Director, Long> {
}