package com.myproject.postproject.service

import com.myproject.postproject.domain.Member
import com.myproject.postproject.repository.MemberRepository
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.IllegalStateException


@Service
@Transactional(readOnly = true)
class MemberService(
    private val memberRepository: MemberRepository,
    private val passwordEncoder: BCryptPasswordEncoder,
) {
    /**
     * 회원가입
     * accountId : 영문 숫자 조합 7 ~ 13자
     * password : 영문 숫자 조합 7 ~ 13자
     */
    fun logIn(accountId: String, rawPassword: String): Member {
        return findMemberWithAuthenticate(accountId, rawPassword)
    }

    private fun findMemberWithAuthenticate(accountId: String, rawPassword: String): Member {
        val findMember = memberRepository.findByAccountId(accountId)
            ?: throw IllegalStateException("존재하지 않는 회원입니다")

        val isSuccess = passwordEncoder.matches(rawPassword, findMember.password)

        if (isSuccess){
            return findMember
        }else{
            throw IllegalStateException("비밀번호가 틀렸습니다")
        }
    }


    fun logOut() {

    }


    @Transactional
    fun join(member: Member) {
        validateId(member)
        validatePassword(member)
        memberRepository.save(member)
    }

    private fun validatePassword(member: Member) {
        val rawPassword = member.password!!
        if (rawPassword.length !in 7..13) {
            throw IllegalStateException("Password 조건 만족하지 않습니다")
        }
        var isContainsAlphabet = false
        var isContainsDigit = false

        for (c in rawPassword) {
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

        member.password = passwordEncoder.encode(rawPassword)// 해싱 + 솔트값 암호화
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

    fun findOne(id: Long): Member? {
        return memberRepository.findOne(id)
    }
}