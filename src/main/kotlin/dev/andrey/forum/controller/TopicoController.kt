package dev.andrey.forum.controller

import dev.andrey.forum.dto.AtualizacaoTopicoForm
import dev.andrey.forum.dto.NovoTopicoForm
import dev.andrey.forum.dto.TopicoView
import dev.andrey.forum.service.TopicoService
import jakarta.validation.Valid
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder

@RestController
//Qual a URI que a aplicação vai trabalhar
@RequestMapping("/topicos")
class TopicoController(private val service: TopicoService) {

    @GetMapping
    fun listar(@RequestParam(required = false) nomeCurso: String?, paginacao: Pageable, direction: Sort.Direction = Sort.Direction.DESC): List<TopicoView> {
        return service.listar(nomeCurso, paginacao)
    }

    @GetMapping("/{id}")
    fun buscarPorId(@PathVariable id: Long): TopicoView {
        //@PathVariable para entender uqe o id da URI é o id do método
        return service.buscarPorId(id)
    }

    @PostMapping
    fun cadastrar(
        @RequestBody dto: NovoTopicoForm,
        uriBuilder: UriComponentsBuilder
    ): ResponseEntity<TopicoView> {
        val topicoView: TopicoView = service.cadastrar(dto)
        val uri = uriBuilder.path("/topicos/${topicoView.id}").build().toUri()
        return ResponseEntity.created(uri).body(topicoView)
    }

    @DeleteMapping("/{id}")
    fun remover(@PathVariable id: Long) {
        service.deletar(id)
    }

    @PutMapping("/{id}")
    fun atualizar(@PathVariable id: Long, @RequestBody @Valid form: AtualizacaoTopicoForm): ResponseEntity<TopicoView> {
        form.id = id
        return ResponseEntity.ok(service.atualizar(form))
    }
}