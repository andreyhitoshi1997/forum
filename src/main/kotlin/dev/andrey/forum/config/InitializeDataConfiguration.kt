package dev.andrey.forum.config

import dev.andrey.forum.model.Usuario
import dev.andrey.forum.repository.UsuarioRepository
import dev.andrey.forum.repository.RoleRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
class InitializeDataConfiguration {

    @Bean
    fun initializeData(
        usuarioRepository: UsuarioRepository,
        roleRepository: RoleRepository,
        passwordEncoder: PasswordEncoder,
        @Value("\${app.usuario.padrao.nome}")
        usuarioPadraoNome: String,
        @Value("\${app.usuario.padrao.email}")
        usuarioPadraoEmail: String,
        @Value("\${app.usuario.padrao.password}")
        usuarioPadraoPassword: String
    ): CommandLineRunner {
        return CommandLineRunner {
            // Verificar se o usuário padrão existe
            val usuarioExistente = usuarioRepository.findByEmail(usuarioPadraoEmail)
            if (usuarioExistente == null) {
                // Buscar a role LEITURA_ESCRITA
                val role = roleRepository.findByNome("LEITURA_ESCRITA")
                    ?: throw RuntimeException("Role LEITURA_ESCRITA não encontrada. Verifique as migrations.")

                // Criar usuário padrão
                val usuario = Usuario(
                    nome = usuarioPadraoNome,
                    email = usuarioPadraoEmail,
                    password = passwordEncoder.encode(usuarioPadraoPassword),
                    role = mutableListOf(role)
                )
                usuarioRepository.save(usuario)
                println("✓ Usuário padrão criado: $usuarioPadraoEmail")
            }
        }
    }
}

