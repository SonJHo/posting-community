package com.myproject.postproject.controller

import com.myproject.postproject.domain.Member
import com.myproject.postproject.dto.user.UserDTO
import com.myproject.postproject.repository.BoardRepository
import com.myproject.postproject.repository.MemberRepository
import com.myproject.postproject.repository.PostRepository
import com.myproject.postproject.service.PostService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*


@Controller
class BoardController (
    private val boardRepository: BoardRepository,
    private val postRepository: PostRepository,
    private val postService: PostService,
    private val memberRepository: MemberRepository
){

    @GetMapping("/board/{id}")
    fun viewBoard(@PathVariable id: Long, model: Model, request: HttpServletRequest): String {
        val session = request.getSession(false) // 세션이 없으면 null 반환
        val userDTO = session.getAttribute("userDTO") as? UserDTO ?: return "redirect:/login-page"

        val board = boardRepository.findById(id) ?: return "redirect:/main" // 게시판을 찾을 수 없으면 메인으로 리디렉션
        model.addAttribute("userAccountId", userDTO.accountId)
        model.addAttribute("userName", userDTO.name)
        model.addAttribute("board", board)
        return "boards/board" // board.html 페이지로 이동
    }


    @GetMapping("/board/create")
    fun createBoardPage(model: Model, request: HttpServletRequest): String {
        val session = request.getSession(false) // 세션이 없으면 null 반환
        val userDTO = session.getAttribute("userDTO") as? UserDTO ?: return "redirect:/login-page"

        model.addAttribute("userName", userDTO.name)

        return "boards/create-board" // create-board.html 페이지로 이동
    }


    @PostMapping("/board/create")
    fun createBoard(@RequestParam name: String, request: HttpServletRequest): String {
        val session = request.getSession(false) // 세션이 없으면 null 반환
        val userDTO = session.getAttribute("userDTO") as? UserDTO ?: return "redirect:/login-page"

        val member = memberRepository.findByAccountId(userDTO.accountId!!) as Member
        // 게시판 생성
        val board = boardRepository.createBoard(name, member)


        boardRepository.save(board)  // 게시판 저장

        return "redirect:/main"  // 메인 페이지로 리디렉션
    }


    @DeleteMapping("/board/delete/{id}")
    fun deleteBoard(@PathVariable id: Long, request: HttpServletRequest): ResponseEntity<String> {
        val session = request.getSession(false)
        val userDTO = session.getAttribute("userDTO") as? UserDTO ?: return ResponseEntity.status(HttpStatus.FORBIDDEN).body("로그인 필요")

        val board = boardRepository.findById(id) ?: return ResponseEntity.status(HttpStatus.NOT_FOUND).body("게시판을 찾을 수 없습니다.")

        // 현재 로그인한 사용자가 게시판 생성자인지 확인
        if (board.member!!.accountId != userDTO.accountId) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("권한이 없습니다.")
        }

        boardRepository.remove(board) // 게시판 삭제

        return ResponseEntity.ok("게시판이 삭제되었습니다.") // 정상 삭제 후 응답
    }





}