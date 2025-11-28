package dev.andrey.forum.controller

import dev.andrey.forum.dto.AtualizacaoTopicoForm
import dev.andrey.forum.dto.NovoTopicoForm
import dev.andrey.forum.dto.TopicoView
import dev.andrey.forum.model.Topico
import dev.andrey.forum.service.TopicoService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
//Qual a URI que a aplicação vai trabalhar
@RequestMapping("/topicos")
class TopicoController(private val service: TopicoService) {

    @GetMapping
    fun listar(): List<TopicoView> {
        return service.listar()
    }

    @GetMapping("/{id}")
    fun buscarPorId(@PathVariable id: Long): TopicoView {
        //@PathVariable para entender uqe o id da URI é o id do método
        return service.buscarPorId(id)
    }

    @PostMapping
    fun cadastrar(@RequestBody @Valid form: NovoTopicoForm): List<Topico> {
        return service.cadastrar(form)
    }

    @PutMapping("/{id}")
    fun atualizar(@RequestBody @Valid form: AtualizacaoTopicoForm) {
        return service.atualizar(form)
    }
}