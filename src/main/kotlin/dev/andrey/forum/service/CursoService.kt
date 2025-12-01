package dev.andrey.forum.service

import dev.andrey.forum.model.Curso
import dev.andrey.forum.repository.CursoRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class CursoService(
    private val repository: CursoRepository,
    @Value("\${app.curso.padrao.nome:Curso Padrão}")
    private val cursoPadraoNome: String,
    @Value("\${app.curso.padrao.categoria:Geral}")
    private val cursoPadraoCategoria: String
) {

    fun buscarPorId(id: Long): Curso {
        return this.repository.findById(id)
            .orElseGet {
                val cursoPadrao = Curso(
                    nome = cursoPadraoNome,
                    categoria = cursoPadraoCategoria
                )
                this.repository.save(cursoPadrao)
            }
    }
}
