package dev.andrey.forum.service

import dev.andrey.forum.model.Usuario
import org.springframework.stereotype.Service
import java.util.Arrays

@Service
class UsuarioService(var usuarios: List<Usuario>) {
    init {
        val usuario1 = Usuario(
            id = 1,
            nome = "Andrey",
            email = "usuario@email.com"
        )
        val usuario2 = Usuario(
            id = 2,
            nome = "Maria",
            email = "autor2@email.com"
        )
        val usuario3 = Usuario(
            id = 3,
            nome = "João",
            email = "autor3@email.com"
        )

        usuarios = Arrays.asList(usuario1, usuario2, usuario3)
    }

    fun buscarPorId(dto: Long): Usuario {
        return usuarios.stream().filter { u -> u.id == dto }.findFirst()
            .orElseThrow { IllegalArgumentException("Usuario com id $dto não encontrado") }
    }

}
