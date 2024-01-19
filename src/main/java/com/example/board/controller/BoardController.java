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

//HTTP 요청 받아서 응답을 하는 컴포넌트. 스프링 부트가 자동으로 빈 생성한다.
@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    // 게시물 목록 보여준다.
    // 컨트롤러 메소드가 리턴하는 문자열은 템플릿 이름이다.
    // http://localhost:8080/ ---> "list" 라는 템플릿을 사용(forward)하여 화면에 출력
    // list를 리턴한다는 것은 classpath:/templtes/list.html 을 사용한다는 것이다.
    @GetMapping("/")
    public String list(@RequestParam(name = "page", defaultValue = "1") int page,
                       HttpSession session, Model model) {
        // 게시물 목록을 읽어온다. 페이징 처리한다.
        LoginInfo loginInfo = (LoginInfo) session.getAttribute("loginInfo");
        model.addAttribute("loginInfo", loginInfo);

        int totalCount = boardService.getTotalCount(); // 11
        List<Board> list = boardService.getBoards(page); // page가 1,2,3,4...
        int pageCount = totalCount / 10; // 1
        if (totalCount % 10 > 0) {
            pageCount++;
        }
        int currentPage = page;
//        System.out.println("totalCount = " + totalCount);
//        for(Board board : list) {
//            System.out.println(board);
//        }
        model.addAttribute("list", list);
        model.addAttribute("pageCount", pageCount);
        model.addAttribute("currentPage", currentPage);

        return "list";
    }

    @GetMapping("/board")
    public String board(
            @RequestParam("boardId") int boardId,
            Model model
    ) {
        System.out.println("boardId = " + boardId);

        // id에 해당하는 게시물을 읽어온다.
        // id에 해당하는 게시물의 조회수도 1증가한다.

        Board board = boardService.getBoard(boardId);
        model.addAttribute("board", board);
        return "board";
    }

    // 삭제한다. 관리자는 모든 글을 삭제할 수 있다.
    // 수정한다. 관리자는 모든 글을 수정할 수 있다.

    @GetMapping("/writeForm")
    public String writeForm(HttpSession session, Model model) {
        // 로그인한 사용자만 글을 써야한다.
        // 세션에서 로그인 정보를 읽어들인다. 로그인 하지 않으면 리스트로 이동시킨다.
        LoginInfo loginInfo = (LoginInfo)session.getAttribute("loginInfo");
        if(loginInfo == null) { // 세션에 로그인 정보가 없으면 /loginform으로 redirect
            return "redirect:/loginform";
        }

        model.addAttribute("loginInfo",loginInfo);
        return "writeForm";
    }

    @PostMapping("/write")
    public String write(
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            HttpSession session
    ) {
        LoginInfo loginInfo = (LoginInfo)session.getAttribute("loginInfo");
        if(loginInfo == null) { // 세션에 로그인 정보가 없으면 /loginform으로 redirect
            return "redirect:/loginform";
        }
        // 로그인한 사용자만 글을 써야한다.
        // 세션에서 로그인한 정보를 읽어들인다. 로그인을 하지 않았다면 리스트로 이동시킨다.
        System.out.println("title = " + title);
        // 로그인 회원 정보 + 제목, 내용을 저장한다.

        boardService.addBoard(loginInfo.getUserId(), title, content);

        return "redirect:/";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("boardId") int boardId,
                         HttpSession session) {
        LoginInfo loginInfo = (LoginInfo)session.getAttribute("loginInfo");
        if(loginInfo == null) {return "redirect:/loginform";}

        // loginInfo.getUserId() 사용자가 쓴 글일 경우에만 삭제한다.
        List<String> roles = loginInfo.getRoles();
        if (roles.contains("ROLE_ADMIN")) {
            boardService.deleteBoard(boardId);
        } else {
            boardService.deleteBoard(loginInfo.getUserId(), boardId);
        }

        return "redirect:/";
    }

    @GetMapping("/updateform")
    public String updateForm(@RequestParam("boardId") int boardId,
                             Model model,
                             HttpSession session) {
        LoginInfo loginInfo = (LoginInfo)session.getAttribute("loginInfo");
        if(loginInfo == null) {return "redirect:/loginform";}

        // boardId 정보를 읽어와서 updatefform 템플릿에게 전달한다.
        Board board = boardService.getBoard(boardId, false);
        model.addAttribute("board", board);
        model.addAttribute("loginInfo", loginInfo);
        return "updateform";
    }

    @PostMapping("/update")
    public String update(@RequestParam("boardId")int boardId,
                         @RequestParam("title")String title,
                         @RequestParam("content")String content,
                         HttpSession session) {
        LoginInfo loginInfo = (LoginInfo)session.getAttribute("loginInfo");
        if(loginInfo == null) {return "redirect:/loginform";}

        Board board = boardService.getBoard(boardId, false);
        if (board.getUserId() != loginInfo.getUserId()) {
            return "redirect:/board?boardId" + boardId; // 본인 아닐경우 글 상세보기로
        }

        // boardId에 해당하는 글의 제목과 내용을 수정한다.
        boardService.updateBoard(boardId, title, content);

        return "redirect:/board?boardId=" + boardId; // 수정한 글로 리다이렉트한다.
    }
}
