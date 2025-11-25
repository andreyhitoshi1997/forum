package dev.andrey.forum.service

import dev.andrey.forum.dto.NovoTopicoForm
import dev.andrey.forum.dto.TopicoView
import dev.andrey.forum.model.Topico
import org.springframework.stereotype.Service

@Service
class TopicoService(
    private val cursoService: CursoService,
    private val usuarioService: UsuarioService
) {
    private val topicos: MutableList<Topico> = mutableListOf()

    fun listar(): List<TopicoView> {
        return topicos.map { t ->
            TopicoView(
                id = t.id,
                titulo = t.titulo,
                mensagem = t.mensagem,
                status = t.status,
                dataCriacao = t.dataCriacao
            )
        }
    }

    fun buscarPorId(id: Long): TopicoView {
        val topico = topicos.find { it.id == id }
            ?: throw IllegalArgumentException("Topico com id $id não encontrado")
        return TopicoView(
            id = topico.id,
            titulo = topico.titulo,
            mensagem = topico.mensagem,
            status = topico.status,
            dataCriacao = topico.dataCriacao
        )
    }

    fun cadastrar(dto: NovoTopicoForm): List<Topico> {
        val novoTopico = Topico(
            id = topicos.size.toLong() + 1,
            titulo = dto.titulo,
            mensagem = dto.mensagem,
            curso = cursoService.buscarPorId(dto.idCurso),
            autor = usuarioService.buscarPorId(dto.idUsuario)
        )
        topicos.add(novoTopico)
        return topicos
    }
}