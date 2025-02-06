package com.myproject.postproject.controller

import com.myproject.postproject.domain.Member
import com.myproject.postproject.domain.Post
import com.myproject.postproject.repository.BoardRepository
import com.myproject.postproject.repository.PostRepository
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


@Controller
class PostController (
    private val boardRepository: BoardRepository,
    private val postService: PostService,
    private val postRepository: PostRepository
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

        val member = session.getAttribute("loginUser") as? Member ?: return "redirect:/login"
        val post = Post(title = title, content = content, board = board, member = member, createBy = loginUser.name)
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
}
