package com.myproject.postproject.controller

import com.myproject.postproject.domain.Comment
import com.myproject.postproject.domain.Member
import com.myproject.postproject.domain.Post
import com.myproject.postproject.dto.user.UserDTO
import com.myproject.postproject.repository.BoardRepository
import com.myproject.postproject.repository.MemberRepository
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
    private val commentService: CommentService,
    private val memberRepository: MemberRepository
){

    @GetMapping("/post/create/")
    fun showPostCreatePage(@RequestParam("boardId") boardId: Long, model: Model, request: HttpServletRequest): String {
        val session = request.getSession(false)
        val userDTO = session.getAttribute("userDTO") as? UserDTO ?: return "redirect:/login-page"

        model.addAttribute("userName", userDTO.name)
        model.addAttribute("boardId", boardId)
        return "posts/create-post"
    }

    @PostMapping("/post/create")
    fun createPost(@RequestParam("boardId") boardId: Long,
                   @RequestParam("title") title: String,
                   @RequestParam("content") content: String,
                   request: HttpServletRequest,
                   model: Model): String {
        val board = boardRepository.findOne(boardId) ?: return "redirect:/main"
        val session = request.getSession(false)
        val userDTO = session.getAttribute("userDTO") as? UserDTO ?: return "redirect:/login-page"
        model.addAttribute("userName", userDTO.name)

        val member = memberRepository.findByAccountId(userDTO.accountId!!)
        val post = Post(title = title, content = content, board = board, member = member, createBy = member!!.name)
        postService.postUp(post)

        return "redirect:/board/$boardId"
    }

    @GetMapping("/post/{id}")
    fun viewPost(@PathVariable id: Long, model: Model, request: HttpServletRequest): String {
        val session = request.getSession(false)
        val userDTO = session.getAttribute("userDTO") as? UserDTO ?: return "redirect:/login-page"
        val post = postRepository.findOne(id) ?: return "redirect:/main"
        model.addAttribute("userName", userDTO.name)
        model.addAttribute("post", post)

        return "posts/post-detail" //
    }

    @PostMapping("/post/{id}/comment")
    fun createComment(@PathVariable id: Long,
                      @RequestParam("commentText") content: String,
                      request: HttpServletRequest,
                      model: Model): String {
        val session = request.getSession(false)
        val userDTO = session.getAttribute("userDTO") as? UserDTO ?: return "redirect:/login-page"
        val post = postRepository.findOne(id) ?: return "redirect:/main"
        val member = memberRepository.findByAccountId(userDTO.accountId!!)
        model.addAttribute("post", post)
        model.addAttribute("userName", userDTO.name)

        val comment = Comment(content = content, post = post, member = member, createBy = member!!.name)
        commentService.commentUp(comment)

        return "redirect:/post/$id"
    }



}
