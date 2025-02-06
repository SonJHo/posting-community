package com.myproject.postproject.service

import com.myproject.postproject.domain.Member
import com.myproject.postproject.repository.MemberRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
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

        return memberRepository.findByAccountId(accountId) ?: throw NoSuchElementException("해당 회원이 없습니다")
    }


    private fun validateAccount(accountId: String): String {
        val findMember = memberRepository.findByAccountId(accountId)
            ?: throw IllegalStateException("존재하지 않는 회원입니다")

        return findMember.password!!
    }

    fun logOut(){

    }

    @Transactional
    fun join(member :Member): Long {
        validateId(member)
        validatePassword(member)
        validateDuplicateMember(member)

        memberRepository.save(member)
        return member.id!!
    }

    private fun validatePassword(member: Member) {
        val password = member.password!!
        if(password.length !in 7..13){
            throw IllegalStateException("password 조건 만족하지 않습니다")
        }
        var isContainsAlphabet = false
        var isContainsDigit = false

        for (c in password) {
            if(!(c.isDigit() || c.isLetter())){
                throw IllegalStateException("password 조건 만족하지 않습니다")
            }
            if (c.isDigit()){
                isContainsDigit = true
            }
            if(c.isLetter()){
                isContainsAlphabet = true
            }
        }
        if (!(isContainsAlphabet && isContainsDigit)){
            throw IllegalStateException("password 조건 만족하지 않습니다")
        }
    }

    private fun validateId(member: Member){
        val accountId = member.accountId!!
        if(accountId.length !in 7..13){
            throw IllegalStateException("ID 조건 만족하지 않습니다")
        }
        var isContainsAlphabet = false
        var isContainsDigit = false

        for (c in accountId) {
            if(!(c.isDigit() || c.isLetter())){
                throw IllegalStateException("ID 조건 만족하지 않습니다")
            }
            if (c.isDigit()){
                isContainsDigit = true
            }
            if(c.isLetter()){
                isContainsAlphabet = true
            }
        }

        if(memberRepository.findByAccountId(accountId) != null){
            throw IllegalStateException("중복된 ID가 존재합니다")
        }

        if (!(isContainsAlphabet && isContainsDigit)){
            throw IllegalStateException("ID 조건 만족하지 않습니다")
        }
    }

    private fun validateDuplicateMember(member: Member) {
        val findMember = memberRepository.findByAccountId(member.accountId!!)
        if(findMember != null){
            throw IllegalStateException("이미 존재하는 회원입니다")
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