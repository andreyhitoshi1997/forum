package dev.andrey.forum.model

import jakarta.servlet.ServletInputStream

data class Credentials(
    val username: String ="",
    val password: String =""
)
