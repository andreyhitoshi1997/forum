package dev.andrey.forum.mapper

import dev.andrey.forum.dto.NovoTopicoForm
import dev.andrey.forum.model.Topico
import dev.andrey.forum.service.CursoService
import dev.andrey.forum.service.UsuarioService
import org.springframework.stereotype.Component

@Component
class TopicoFormMapper(
    private val cursoService: CursoService,
    private val usuarioService: UsuarioService
): Mapper<NovoTopicoForm, Topico> {
    override fun map(topicos: NovoTopicoForm): Topico {
        return Topico(

            titulo = topicos.titulo,
            mensagem = topicos.mensagem,
            curso = cursoService.buscarPorId(topicos.idCurso),
            autor = usuarioService.buscarPorId(topicos.idUsuario)
        )
    }

}
