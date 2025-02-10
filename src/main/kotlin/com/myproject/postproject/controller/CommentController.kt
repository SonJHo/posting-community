package com.myproject.postproject.controller

import com.myproject.postproject.dto.user.UserDTO
import com.myproject.postproject.repository.CommentRepository
import com.myproject.postproject.service.CommentService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable


@Controller
class CommentController(
    private val commentService: CommentService,
    private val commentRepository: CommentRepository
) {

    @DeleteMapping("/comment/{commentId}")
    fun deleteComment(@PathVariable commentId: Long, request: HttpServletRequest): ResponseEntity<String> {
        val session = request.getSession(false)
        val userDTO = session.getAttribute("userDTO") as? UserDTO ?: return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.")

        val comment = commentRepository.findById(commentId)
            ?: return ResponseEntity.status(HttpStatus.NOT_FOUND).body("댓글을 찾을 수 없습니다.")

        // 현재 로그인한 사용자가 해당 댓글의 작성자인지 확인
        if (comment.member!!.accountId != userDTO.accountId) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("본인이 작성한 댓글만 삭제할 수 있습니다.")
        }

        commentService.commentDown(comment)
        return ResponseEntity.ok("댓글이 삭제되었습니다.")
    }



}