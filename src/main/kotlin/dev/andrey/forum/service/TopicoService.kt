package dev.andrey.forum.service

import dev.andrey.forum.dto.AtualizacaoTopicoForm
import dev.andrey.forum.dto.NovoTopicoForm
import dev.andrey.forum.dto.TopicoView
import dev.andrey.forum.mapper.TopicoFormMapper
import dev.andrey.forum.mapper.TopicoViewMapper
import dev.andrey.forum.model.Topico
import org.springframework.stereotype.Service

@Service
class TopicoService(
    private val topicoViewMapper: TopicoViewMapper,
    private val topicoFormMapper: TopicoFormMapper
) {
    private val topicos: MutableList<Topico> = mutableListOf()

    fun listar(): List<TopicoView> {
        return topicos.map { t ->
            topicoViewMapper.map(t)
        }
    }

    fun buscarPorId(id: Long): TopicoView {
        val topico = topicos.find { it.id == id }
            ?: throw IllegalArgumentException("Topico com id $id não encontrado")
        return topicoViewMapper.map(topico)
    }

    fun cadastrar(dto: NovoTopicoForm): List<Topico> {
        val topico = topicoFormMapper.map(dto)
        topico.id = (topicos.size + 1).toLong()
        topicos.add(topico)
        return topicos
    }

    fun atualizar(form: AtualizacaoTopicoForm) {
        val topico = topicos.find { it.id == form.id }
            ?: throw IllegalArgumentException("Topico com id ${form.id} não encontrado")
        val index = topicos.indexOf(topico)
        topicos[index] = Topico(
            id = form.id,
            titulo = form.titulo,
            mensagem = form.mensagem,
            autor = topico.autor,
            curso = topico.curso,
            status = topico.status
        )
    }
}