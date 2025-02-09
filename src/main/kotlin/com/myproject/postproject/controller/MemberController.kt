package com.myproject.postproject.controller

import com.myproject.postproject.domain.Member
import com.myproject.postproject.dto.login.MemberForm
import com.myproject.postproject.dto.login.toMember
import com.myproject.postproject.service.MemberService
import jakarta.validation.Valid
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import java.lang.IllegalStateException


@Controller
class MemberController(
    private val memberService: MemberService,
) {


    @GetMapping("/members/new")
    fun createForm(model: Model): String {
        model.addAttribute("memberForm", MemberForm())
        return "members/createMemberForm"
    }

    @PostMapping("/members/new")
    fun create(@Valid memberForm: MemberForm, result: BindingResult): String {
        //Valid 애노테이션에서 MemberForm 필드의 제약조건 검증후 오류를 result에 담아서 반환
        if (result.hasErrors()) {
            return "members/createMemberForm"
        }

        val member = memberForm.toMember()  //DTO 를 객체로 바꾸는 방법.1
        try {
            memberService.join(member)
        } catch (e: IllegalStateException) {
            result.rejectValue("accountId", "error.accountId", e.message ?: "회원가입 중 오류가 발생했습니다.")
            return "members/createMemberForm"
        }

        return "redirect:/login-page"
    }
}