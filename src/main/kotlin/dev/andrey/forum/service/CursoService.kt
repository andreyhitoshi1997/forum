package dev.andrey.forum.service

import dev.andrey.forum.model.Curso
import org.springframework.stereotype.Service
import java.util.Arrays

@Service
class CursoService(var cursos: List<Curso>) {
    init {
        val curso1 = Curso(
            id = 1,
            nome = "Kotlin",
            categoria = "Programação"
        )
        val curso2 = Curso(
            id = 2,
            nome = "Java",
            categoria = "Programação"
        )
        val curso3 = Curso(
            id = 3,
            nome = "Python",
            categoria = "Programação"
        )
        cursos = Arrays.asList(curso1, curso2, curso3)
    }

    fun buscarPorId(id: Long): Curso {
        return cursos.stream().filter { c -> c.id == id }.findFirst()
            .orElseThrow { IllegalArgumentException("Curso com id $id não encontrado") }
    }


}