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
    @Value("\${app.usuario.padrao.nome:Usuário Padrão}")
    private val usuarioPadraoNome: String,
    @Value("\${app.usuario.padrao.email:usuario@padrao.com}")
    private val usuarioPadraoEmail: String,
    @Value("\${app.usuario.padrao.password:senha123}")
    private val usuarioPadraoPassword: String
): UserDetailsService {

    fun buscarPorId(id: Long): Usuario {
        return this.repository.findById(id)
            .orElseGet {
                // Retornar usuário padrão se não encontrar
                this.repository.findByEmail(usuarioPadraoEmail)
                    ?: run {
                        val usuarioPadrao = Usuario(
                            nome = usuarioPadraoNome,
                            email = usuarioPadraoEmail,
                            password = usuarioPadraoPassword
                        )
                        this.repository.save(usuarioPadrao)
                    }
            }
    }

    override fun loadUserByUsername(username: String?): UserDetails {
        val usuario = this.repository.findByEmail(username!!)
            ?: throw UsernameNotFoundException("Usuário não encontrado: $username")

        val authorities = usuario.role.map { SimpleGrantedAuthority(it.getAuthority()) }

        return User(usuario.email, usuario.password, authorities)
    }
}
