package com.myproject.postproject.controller

import mu.KotlinLogging
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping


@Controller
class HomeController {
    val log = KotlinLogging.logger {}

    @RequestMapping("/")
    fun home(): String {
        log.info { "call home controller" }
        return "home"
    }

    @GetMapping("/main")
    fun main(): String {
        return "main"
    }

}