package com.myproject.postproject.controller

import com.myproject.postproject.dto.login.LoginForm
import com.myproject.postproject.dto.user.UserDTO
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import java.lang.IllegalStateException


@Controller
class LoginController (
    private val memberService: MemberService
){

    @GetMapping("/login-page")
    fun loginForm(model : Model) :String{
        model.addAttribute("loginForm", LoginForm())
        return "login/loginForm"
    }

    @PostMapping("/login-page")
    fun login(@Valid @ModelAttribute loginForm: LoginForm, bindingResult: BindingResult,
              request: HttpServletRequest, model: Model): String {
        if (bindingResult.hasErrors()) {
            return "login/loginForm"
        }

        try {
            val member = memberService.logIn(loginForm.accountId!!, loginForm.password!!)
            val userDTO = UserDTO().apply {
                accountId = member.accountId
                name = member.name
            }
            val session: HttpSession = request.getSession(true) // 세션이 없으면 생성
            session.setAttribute("userDTO", userDTO) //세션에 로그인한 사용자 정보 저장
        } catch (e :IllegalStateException){
            model.addAttribute("errorMessage", e.message)
            return "login/loginForm"
        }

        return "redirect:/main"
    }

    @PostMapping("/logout-page")
    fun logOut(request: HttpServletRequest, redirectAttributes: RedirectAttributes): String {
        request.getSession(false)?.invalidate() // 세션 삭제
        redirectAttributes.addFlashAttribute("logoutMessage", "로그아웃 되었습니다.")
        return "redirect:/login-page"
    }
}