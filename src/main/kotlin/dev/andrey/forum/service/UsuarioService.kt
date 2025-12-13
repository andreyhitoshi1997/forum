package dev.andrey.forum.service

import dev.andrey.forum.model.Usuario
import dev.andrey.forum.repository.UsuarioRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UsuarioService(
    private val repository: UsuarioRepository,
    @Value("\${app.usuario.padrao.nome}")
    private val usuarioPadraoNome: String,
    @Value("\${app.usuario.padrao.email}")
    private val usuarioPadraoEmail: String
): UserDetailsService {

    fun buscarPorId(id: Long): Usuario {
        return this.repository.findById(id)
            .orElseThrow {
                RuntimeException("Usuário com ID $id não encontrado")
            }
    }

    override fun loadUserByUsername(username: String?): UserDetails {
        val usuario = this.repository.findByEmail(username!!)
            ?: throw UsernameNotFoundException("Usuário não encontrado: $username")

        val authorities = usuario.role.map { SimpleGrantedAuthority(it.getAuthority()) }

        return User(usuario.email, usuario.password, authorities)
    }
}
