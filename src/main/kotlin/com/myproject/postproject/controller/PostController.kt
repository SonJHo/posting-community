package com.myproject.postproject.controller

import com.myproject.postproject.domain.Comment
import com.myproject.postproject.domain.Post
import com.myproject.postproject.dto.user.UserDTO
import com.myproject.postproject.repository.BoardRepository
import com.myproject.postproject.repository.MemberRepository
import com.myproject.postproject.repository.PostRepository
import com.myproject.postproject.service.CommentService
import com.myproject.postproject.service.PostService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.http.HttpStatus


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
        val board = boardRepository.findById(boardId) ?: return "redirect:/main"
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
        val post = postRepository.findById(id) ?: return "redirect:/main"
        model.addAttribute("userName", userDTO.name)
        model.addAttribute("userAccountId", userDTO.accountId)
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
        val post = postRepository.findById(id) ?: return "redirect:/main"
        val member = memberRepository.findByAccountId(userDTO.accountId!!)
        model.addAttribute("post", post)
        model.addAttribute("userName", userDTO.name)

        val comment = Comment(content = content, post = post, member = member, createBy = member!!.name)
        commentService.commentUp(comment)

        return "redirect:/post/$id"
    }

    @DeleteMapping("/post/{id}/delete")
    fun deletePost(@PathVariable id: Long, request: HttpServletRequest): ResponseEntity<Map<String, String>> {
        val session = request.getSession(false)
        val userDTO = session.getAttribute("userDTO") as? UserDTO ?: return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(mapOf("message" to "로그인이 필요합니다."))

        val post = postRepository.findById(id)
            ?: return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mapOf("message" to "게시글을 찾을 수 없습니다."))

        if (post.member!!.accountId != userDTO.accountId) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(mapOf("message" to "본인이 작성한 게시글만 삭제할 수 있습니다."))
        }

        val boardId = post.board!!.id
        postService.postDown(post)

        // 리다이렉트할 URI와 메시지를 JSON으로 반환
        return ResponseEntity.ok(mapOf("message" to "게시글이 삭제되었습니다.",
            "redirectUrl" to "/board/$boardId"))
    }


}
