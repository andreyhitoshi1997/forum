package dev.andrey.forum.controller

import dev.andrey.forum.dto.TopicoPorCategoriaDTO
import dev.andrey.forum.service.TopicoService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class RelatorioController(
    private val topicoService: TopicoService
) {

    @GetMapping
    fun relatorio(model: Model): String {
        model.addAttribute("relatorio", this.topicoService.relatorio())
        return "relatorio"
    }
}