package com.myproject.postproject.service

import com.myproject.postproject.domain.Member
import com.myproject.postproject.repository.MemberRepository
import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

import org.springframework.transaction.annotation.Transactional
import java.lang.IllegalStateException


@SpringBootTest
@Transactional
class MemberServiceTest @Autowired constructor(
    private val memberService: MemberService,
    private val memberRepository: MemberRepository,
    private val em : EntityManager
){



    fun createNewMember(
        accountId: String,
        password: String,
        name: String,
        studentId: Long
    ): Member {
        return Member(accountId = accountId, password = password, name = name, studentId = studentId)
    }



    @Test
    fun 로그인_통과(){
    //given
        val member = createNewMember("wnsgh7082", "wnsgh1234", "손준호", 1234L)
        //when
        memberService.join(member)
        memberService.logIn(member.accountId!!, member.password!!)

    }

    @Test
    fun 로그인_실패(){
        //given
        val member = createNewMember("wnsgh7082", "wnsgh1234", "손준호", 1234L)
        //when
        assertThrows<IllegalStateException>{
            memberService.logIn(member.accountId!!, member.password!!)
        }

    }
    @Test
    fun 정상_회원가입(){
    //given
        val member = createNewMember("wnsgh7082", "wnsgh123", "손준호", 12321L)
        //when
        memberService.join(member)
        em.flush()
        val findMember = memberRepository.findByAccountId("wnsgh7082")
        //then
        Assertions.assertThat(member).isEqualTo(findMember)
    }


    @Test
    fun ID_숫자X_회원가입(){
        //given
        val member = createNewMember("wnsghsdfsd", "wnsgh123", "손준호", 12321L)
        //when
        assertThrows<IllegalStateException>{
            memberService.join(member)
        }
    }

    @Test
    fun ID_알파벳X_회원가입(){
        //given
        val member = createNewMember("2342343", "wnsgh123", "손준호", 12321L)
        //when
        assertThrows<IllegalStateException>{
            memberService.join(member)
        }
    }

    @Test
    fun ID_lenghX_회원가입(){
        //given
        val member = createNewMember("1w1w1wwwwwwwww", "wnsgh123", "손준호", 12321L)
        //when
        assertThrows<IllegalStateException>{
            memberService.join(member)
        }
    }
    @Test
    fun PW_알파벳X_회원가입(){
        //given
        val member = createNewMember("wnsgh7082", "1234455", "손준호", 12321L)
        //when
        assertThrows<IllegalStateException>{
            memberService.join(member)
        }
    }

    @Test
    fun PW_숫자X_회원가입(){
        //given
        val member = createNewMember("wnsgh7082", "wnsghwnsgh", "손준호", 12321L)
        //when
        assertThrows<IllegalStateException>{
            memberService.join(member)
        }
    }














}