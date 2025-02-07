package com.myproject.postproject.service

import com.myproject.postproject.domain.Member
import com.myproject.postproject.repository.MemberRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.validation.BindingResult
import kotlin.IllegalStateException


@Service
@Transactional(readOnly = true)
class MemberService(
    private val memberRepository: MemberRepository,
) {
    /**
     * 회원가입
     * accountId : 영문 숫자 조합 7 ~ 13자
     * password : 영문 숫자 조합 7 ~ 13자
     */
    fun logIn(accountId: String, password: String): Member {

        val findPassword = validateAccount(accountId)
        if (findPassword == password) {
            println("$accountId is login complete")
        }
        return memberRepository.findByAccountId(accountId) ?: throw IllegalStateException("해당 회원이 없습니다")
    }


    private fun validateAccount(accountId: String): String {
        val findMember = memberRepository.findByAccountId(accountId)
            ?: throw IllegalStateException("존재하지 않는 회원입니다")

        return findMember.password!!
    }

    fun logOut(){

    }



    @Transactional
    fun join(member: Member) {
        validateId(member)
        validatePassword(member)

        memberRepository.save(member)
    }

    private fun validatePassword(member: Member) {
        val password = member.password!!
        if (password.length !in 7..13) {
            throw IllegalStateException("Password 조건 만족하지 않습니다")
        }
        var isContainsAlphabet = false
        var isContainsDigit = false

        for (c in password) {
            if (!(c.isDigit() || c.isLetter())) {
                throw IllegalStateException("Password 조건 만족하지 않습니다")
            }
            if (c.isDigit()) {
                isContainsDigit = true
            }
            if (c.isLetter()) {
                isContainsAlphabet = true
            }
        }
        if (!(isContainsAlphabet && isContainsDigit)) {
            throw IllegalStateException("Password 조건 만족하지 않습니다")
        }
    }

    private fun validateId(member: Member) {
        val accountId = member.accountId!!
        if (accountId.length !in 7..13) {
            throw IllegalStateException("ID 조건 만족하지 않습니다")
        }
        var isContainsAlphabet = false
        var isContainsDigit = false

        for (c in accountId) {
            if (!(c.isDigit() || c.isLetter())) {
                throw IllegalStateException("ID 조건 만족하지 않습니다")
            }
            if (c.isDigit()) {
                isContainsDigit = true
            }
            if (c.isLetter()) {
                isContainsAlphabet = true
            }
        }

        if (memberRepository.findByAccountId(accountId) != null) {
            throw IllegalStateException("중복된 ID가 존재합니다")
        }

        if (!(isContainsAlphabet && isContainsDigit)) {
            throw IllegalStateException("ID 조건 만족하지 않습니다")
        }
    }



    @Transactional
    fun withDraw(member: Member) {
        memberRepository.remove(member)
    }


    fun findMembers(): MutableList<Member> {
        return memberRepository.findAll() ?: return mutableListOf()
    }
    fun findOne(id : Long): Member? {
        return memberRepository.findOne(id)
    }
}