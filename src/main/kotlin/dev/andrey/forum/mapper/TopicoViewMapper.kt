package dev.andrey.forum.mapper

import dev.andrey.forum.dto.TopicoView
import dev.andrey.forum.model.Topico
import org.springframework.stereotype.Component

//Nomenclatura
@Component
class TopicoViewMapper: Mapper<Topico, TopicoView> {
    override fun map(t: Topico): TopicoView {
        return TopicoView(
            id = t.id,
            titulo = t.titulo,
            mensagem = t.mensagem,
            status = t.status,
            dataCriacao = t.dataCriacao
        )
    }
}