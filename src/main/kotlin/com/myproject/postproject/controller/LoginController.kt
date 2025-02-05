package com.myproject.postproject.controller

import com.myproject.postproject.service.MemberService
import jakarta.validation.Valid
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import java.lang.IllegalStateException


@Controller
class LoginController (
    private val memberService: MemberService
){

    @GetMapping("/login")
    fun loginForm(model : Model) :String{
        model.addAttribute("memberForm", LoginForm())
        return "login/loginForm"
    }

    @PostMapping("/login")
    fun login(@Valid @ModelAttribute loginForm: LoginForm, bindingResult: BindingResult): String {
        if (bindingResult.hasErrors()) {
            return "login/loginForm"
        }
        try {
            memberService.logIn(loginForm.accountId!!, loginForm.password!!)
        } catch (e :IllegalStateException){
            return "login/loginForm"
        }

        return "redirect:/main"
    }
}