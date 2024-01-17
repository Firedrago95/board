package com.example.board.controller;

import com.example.board.dto.Board;
import com.example.board.dto.LoginInfo;
import com.example.board.service.BoardService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/")
    public String list(HttpSession session, Model model) {
        LoginInfo loginInfo = (LoginInfo) session.getAttribute("loginInfo");
        model.addAttribute("loginInfo", loginInfo);

        int page = 1;
        int totalCount = boardService.getTotalCount();
        List<Board> list = boardService.getBoards(page);
        int pageCount = totalCount / 10;
        if (totalCount % 10 > 0) {
            pageCount++;
        }
        int currentPage = page;

        model.addAttribute("list", list);
        model.addAttribute("pageCount", pageCount);
        model.addAttribute("currentPage", currentPage);


        return "list";
    }

    @GetMapping("/writeForm")
    public String writeForm() {
        return "writeForm";
    }

    @PostMapping("/write")
    public String write(
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            HttpSession session
    ) {
        LoginInfo loginInfo = (LoginInfo)session.getAttribute("loginInfo");
        if (loginInfo == null) {
            return "redirect:/loginform";
        }

        boardService.addBoard(loginInfo.getUserId(), title, content);

        return "redirect:/";
    }
}
