package dev.andrey.forum.repository

import dev.andrey.forum.model.Usuario
import org.springframework.data.jpa.repository.JpaRepository

interface UsuarioRepository: JpaRepository<Usuario, Long> {
    fun id(id: Long): MutableList<Usuario>
    fun findByEmail(email: String): Usuario?
}