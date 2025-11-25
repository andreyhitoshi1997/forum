package dev.andrey.forum.dto

import dev.andrey.forum.model.StatusTopico
import java.time.LocalDateTime

data class TopicoView (
    val id:  Long?,
    val titulo: String,
    val mensagem: String,
    val status: StatusTopico,
    val dataCriacao: LocalDateTime
)