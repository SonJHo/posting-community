package com.myproject.postproject.controller

import com.myproject.postproject.service.MemberService
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpSession
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
        model.addAttribute("loginForm", LoginForm())
        return "login/loginForm"
    }

    @PostMapping("/login")
    fun login(@Valid @ModelAttribute loginForm: LoginForm, bindingResult: BindingResult,
              request: HttpServletRequest): String {
        if (bindingResult.hasErrors()) {
            return "login/loginForm"
        }
        try {
            val member = memberService.logIn(loginForm.accountId!!, loginForm.password!!)
            val session: HttpSession = request.getSession(true) // 세션이 없으면 생성
            session.setAttribute("loginUser", member) //세션에 로그인한 사용자 정보 저장

        } catch (e :IllegalStateException){
            return "login/loginForm"
        } catch (e : NoSuchElementException){
            return "login/loginForm"
        }

        return "redirect:/main"
    }

    @PostMapping("/logout")
    fun logOut(request: HttpServletRequest): String {
        request.getSession(false)?.invalidate() // 세션 삭제
        return "redirect:/login"
    }
}