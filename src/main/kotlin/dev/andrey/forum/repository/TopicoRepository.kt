package dev.andrey.forum.repository

import dev.andrey.forum.model.Topico
import org.springframework.stereotype.Repository

@Repository
class TopicoRepository {
    companion object {
        private val topicos: MutableList<Topico> = mutableListOf()
    }

    fun salvar(topico: Topico): Topico {
        topicos.add(topico)
        return topico
    }

    fun listar(): List<Topico> {
        return topicos.toList()
    }

    fun buscarPorId(id: Long): Topico? {
        return topicos.find { it.id == id }
    }
}

