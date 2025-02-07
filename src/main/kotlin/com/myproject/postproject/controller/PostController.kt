package com.myproject.postproject.controller

import com.myproject.postproject.domain.Comment
import com.myproject.postproject.domain.Member
import com.myproject.postproject.domain.Post
import com.myproject.postproject.repository.BoardRepository
import com.myproject.postproject.repository.PostRepository
import com.myproject.postproject.service.CommentService
import com.myproject.postproject.service.MemberService
import com.myproject.postproject.service.PostService
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpSession
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.servlet.mvc.support.RedirectAttributes


@Controller
class PostController (
    private val boardRepository: BoardRepository,
    private val postService: PostService,
    private val postRepository: PostRepository,
    private val commentService: CommentService
){

    @GetMapping("/post/create/")
    fun showPostCreatePage(@RequestParam("boardId") boardId: Long, model: Model, session: HttpSession): String {
        val loginUser = session.getAttribute("loginUser") as? Member ?: return "redirect:/login"
        model.addAttribute("loginUser", loginUser)

        model.addAttribute("boardId", boardId)
        return "posts/create-post" // create-post.html로 이동
    }

    @PostMapping("/post/create")
    fun createPost(@RequestParam("boardId") boardId: Long,
                   @RequestParam("title") title: String,
                   @RequestParam("content") content: String,
                   session: HttpSession,
                   model: Model): String {
        val board = boardRepository.findOne(boardId) ?: return "redirect:/main" // 게시판을 찾을 수 없으면 메인으로 리디렉션
        val loginUser = session.getAttribute("loginUser") as? Member ?: return "redirect:/login"
        model.addAttribute("loginUser", loginUser)
        val post = Post(title = title, content = content, board = board, member = loginUser, createBy = loginUser.name)
        postService.postUp(post)

        return "redirect:/board/$boardId" // 게시판 페이지로 리디렉션
    }

    @GetMapping("/post/{id}")
    fun viewPost(@PathVariable id: Long, model: Model, session: HttpSession): String {
        val loginUser = session.getAttribute("loginUser") as? Member ?: return "redirect:/login"
        model.addAttribute("loginUser", loginUser)

        val post = postRepository.findOne(id) ?: return "redirect:/main" // 게시글을 찾을 수 없으면 메인으로 리디렉션
        model.addAttribute("post", post) // 게시글 정보를 모델에 추가
        return "posts/post-detail" //
    }

    @PostMapping("/post/{id}/comment")
    fun createComment(@PathVariable id: Long,
                      @RequestParam("commentText") content: String,
                      session: HttpSession,
                      model: Model): String {
        // 로그인된 사용자 확인
        val loginUser = session.getAttribute("loginUser") as? Member ?: return "redirect:/login"
        model.addAttribute("loginUser", loginUser)

        // 해당 게시글 찾기
        val post = postRepository.findOne(id) ?: return "redirect:/main" // 게시글이 없으면 메인 페이지로 리디렉션
        model.addAttribute("post", post)


        println("--------------------------")
        println("content = ${content}")
        // 댓글 생성
        val comment = Comment(content = content, post = post, member = loginUser, createBy = loginUser.name)
        commentService.commentUp(comment) // 댓글 저장 (서비스 클래스에서 처리)

        // 댓글 작성 후 해당 게시글 페이지로 리디렉션
        return "redirect:/post/$id" // 해당 게시글 페이지로 리디렉션
    }



}
