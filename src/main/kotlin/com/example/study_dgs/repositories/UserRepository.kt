package com.example.study_dgs.repositories

import com.example.study_dgs.entities.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository: JpaRepository<User, Long> {
}