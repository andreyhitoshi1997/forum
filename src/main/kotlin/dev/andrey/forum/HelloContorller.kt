package dev.andrey.forum

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/hello")
class HelloContorller {

    @GetMapping
    fun hello(): String {
        return "Hello World!"
    }
}