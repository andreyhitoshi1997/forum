package dev.andrey.forum.service

import dev.andrey.forum.model.Usuario
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class UserDetail(
    private val usuario: Usuario
) : UserDetails {
    override fun getAuthorities() =  usuario.role

    override fun getPassword()=  usuario.password

    override fun getUsername() = usuario.email
}