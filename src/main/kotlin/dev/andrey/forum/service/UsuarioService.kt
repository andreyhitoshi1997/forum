package dev.andrey.forum.service

import dev.andrey.forum.model.Usuario
import dev.andrey.forum.repository.UsuarioRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class UsuarioService(
    private val repository: UsuarioRepository,
    @Value("\${app.usuario.padrao.nome:Usuário Padrão}")
    private val usuarioPadraoNome: String,
    @Value("\${app.usuario.padrao.email:usuario@padrao.com}")
    private val usuarioPadraoEmail: String
) {

    fun buscarPorId(id: Long): Usuario {
        return this.repository.findById(id)
            .orElseGet {
                val usuarioPadrao = Usuario(
                    nome = usuarioPadraoNome,
                    email = usuarioPadraoEmail
                )
                this.repository.save(usuarioPadrao)
            }
    }

}
