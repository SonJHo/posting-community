package com.myproject.postproject.controller

import com.myproject.postproject.domain.Board
import com.myproject.postproject.domain.Member
import com.myproject.postproject.repository.BoardRepository
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpSession
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import java.time.LocalDateTime


@Controller
class BoardController (
    private val boardRepository: BoardRepository
){

    @GetMapping("/board/{id}")
    fun viewBoard(@PathVariable id: Long, model: Model, request: HttpServletRequest): String {
        val session = request.getSession(false) // 세션이 없으면 null 반환
        val loginUser = session?.getAttribute("loginUser") as? Member ?: return "redirect:/login"
        model.addAttribute("loginUser", loginUser)
        val board = boardRepository.findOne(id) ?: return "redirect:/main" // 게시판을 찾을 수 없으면 메인으로 리디렉션
        model.addAttribute("board", board)
        // 로그인된 사용자 정보 추가
        return "boards/board" // board.html 페이지로 이동
    }


    @GetMapping("/board/create")
    fun createBoardPage(model: Model, session: HttpSession): String {
        val loginUser = session.getAttribute("loginUser") as? Member ?: return "redirect:/login"
        model.addAttribute("loginUser", loginUser)
        return "boards/create-board" // create-board.html 페이지로 이동
    }


    @PostMapping("/board/create")
    fun createBoard(@RequestParam name: String, request: HttpServletRequest): String {
        // 세션에서 로그인된 사용자 정보 추출
        val session = request.getSession(false)
        val loginUser = session?.getAttribute("loginUser") as? Member
            ?: return "redirect:/login"  // 로그인되지 않은 경우 로그인 페이지로 리디렉션

        // 게시판 생성
        val board = boardRepository.createBoard(name, loginUser.name!!)
        boardRepository.save(board)  // 게시판 저장

        return "redirect:/main"  // 메인 페이지로 리디렉션
    }
}