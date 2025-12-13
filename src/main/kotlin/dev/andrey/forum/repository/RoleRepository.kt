package dev.andrey.forum.repository

import dev.andrey.forum.model.Role
import org.springframework.data.jpa.repository.JpaRepository

interface RoleRepository : JpaRepository<Role, Long> {
    fun findByNome(nome: String): Role?
}

