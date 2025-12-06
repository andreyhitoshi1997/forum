package dev.andrey.forum.service

import dev.andrey.forum.dto.AtualizacaoTopicoForm
import dev.andrey.forum.dto.NovoTopicoForm
import dev.andrey.forum.dto.TopicoView
import dev.andrey.forum.exceptions.IllegalArgumentException
import dev.andrey.forum.mapper.TopicoFormMapper
import dev.andrey.forum.mapper.TopicoViewMapper
import dev.andrey.forum.model.Topico
import dev.andrey.forum.repository.TopicoRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class TopicoService(
    private val repository: TopicoRepository,
    private val topicoViewMapper: TopicoViewMapper,
    private val topicoFormMapper: TopicoFormMapper,
    @Value("\${app.mensagem.topico.nao-encontrado:Topico com id %d não encontrado}")
    private val mensagemErroTopico: String
) {

    fun listar(nomeCurso: String?, paginacao: Pageable): List<TopicoView> {
        val topicos = if(nomeCurso == null) {
            this.repository.findAll(paginacao).content
        } else {
            this.repository.findByCursoNome(nomeCurso, paginacao).content
        }
        return topicos.map { topicoViewMapper.map(it) }
    }

    fun buscarPorId(id: Long): TopicoView {
        val topico = this.repository.findById(id)
            .orElseThrow { IllegalArgumentException(mensagemErroTopico.format(id)) }
        return topicoViewMapper.map(topico)
    }

    fun cadastrar(dto: NovoTopicoForm): TopicoView {
        val topico = this.repository.save(topicoFormMapper.map(dto))
        return topicoViewMapper.map(topico)
    }

    fun atualizar(form: AtualizacaoTopicoForm): TopicoView {
        val topico = this.repository.findById(form.id)
            .orElseThrow { IllegalArgumentException(mensagemErroTopico.format(form.id)) }
        val topicoAtualizado = Topico(
            id = form.id,
            titulo = form.titulo,
            mensagem = form.mensagem,
            autor = topico.autor,
            curso = topico.curso,
            status = topico.status
        )
        val topicoSalvo = this.repository.save(topicoAtualizado)
        return topicoViewMapper.map(topicoSalvo)
    }

    fun deletar(id: Long) {
        this.repository.deleteById(id)
    }
}