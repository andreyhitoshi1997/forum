package dev.andrey.forum.repository

import dev.andrey.forum.model.Topico
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface TopicoRepository: JpaRepository<Topico, Long> {
    fun findByCursoNome(nomeCurso: String, paginacao: Pageable): Page<Topico>
    //Query JPQL
    @Query("SELECT new dev.andrey.forum.dto.TopicoPorCategoriaDTO(curso.categoria, COUNT(t)) FROM Topico t JOIN t.curso curso GROUP BY curso.categoria")
    fun relatorio(): List<dev.andrey.forum.dto.TopicoPorCategoriaDTO>
}