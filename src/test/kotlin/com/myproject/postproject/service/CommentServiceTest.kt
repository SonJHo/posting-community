package com.myproject.postproject.service

import com.myproject.postproject.domain.Board
import com.myproject.postproject.domain.Comment
import com.myproject.postproject.domain.Member
import com.myproject.postproject.domain.Post
import com.myproject.postproject.repository.BoardRepository
import com.myproject.postproject.repository.CommentRepository
import com.myproject.postproject.repository.PostRepository
import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.Rollback
import org.springframework.transaction.annotation.Transactional
import kotlin.test.Test


@SpringBootTest
@Transactional
class CommentServiceTest @Autowired constructor(
    private val em : EntityManager,
    private val commentService: CommentService,
    private val postService: PostService,
    private val memberService: MemberService,
    private val boardRepository: BoardRepository,
    private val commentRepository: CommentRepository

){

    fun createNewMember(
        accountId: String,
        password: String,
        name: String,
    ): Member {
        return Member(accountId = accountId, password = password, name = name)
    }

    @Test
    @Rollback(false)
    fun 댓글_저장 (){
    //given
        val member = createNewMember("wnsgh708282", "wnsgh1598", "손준호")
        memberService.join(member)

        val board = boardRepository.createBoard("자료구조", member.name!!)
        boardRepository.save(board)

        val post = Post()
        post.title = "공지사항"
        post.content = "오늘은 휴강입니다"
        post.member = member
        post.board = board

        postService.postUp(post)

        val comment = Comment()
        comment.post = post
        comment.content ="헐;;;"
        comment.member = member
        commentService.commentUp(comment)

        //when
    //then
        val comments = commentRepository.findAll()

        Assertions.assertThat(comments!!.size).isSameAs(1)
        Assertions.assertThat(post.comments.size).isSameAs(1)
    }
}