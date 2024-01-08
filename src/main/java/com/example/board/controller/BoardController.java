package com.example.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

//HTTP 요청 받아서 응답을 하는 컴포넌트. 스프링 부트가 자동으로 빈 생성한다.
@Controller
public class BoardController {

    // 게시물 목록 보여준다.
    // 컨트롤러 메소드가 리턴하는 문자열은 템플릿 이름이다.
    // http://localhost:8080/ ---> "list" 라는 템플릿을 사용(forward)하여 화면에 출력
    // list를 리턴한다는 것은 classpath:/templtes/list.html 을 사용한다는 것이다.
    @GetMapping("/")
    public String list() {
        // 게시물 목록을 읽어온다. 페이징 처리한다.
        return "list";
    }

    @GetMapping("/board")
    public String board(
            @RequestParam("id") int id
    ) {
        System.out.println("id = " + id);

        // id에 해당하는 게시물을 읽어온다.
        // id에 해당하는 게시물의 조회수도 1증가한다.

        return "board";
    }

    // 삭제한다. 관리자는 모든 글을 삭제할 수 있다.
    // 수정한다. 관리자는 모든 글을 수정할 수 있다.

    @GetMapping("/writeForm")
    public String writeForm() {
        // 로그인한 사용자만 글을 써야한다.
        // 세션에서 로그인 정보를 읽어들인다. 로그인 하지 않으면 리스트로 이동시킨다.
        return "writeForm";
    }

    @PostMapping("/write")
    public String write(
            @RequestParam("title") String title,
            @RequestParam("content") String content
    ) {
        // 로그인한 사용자만 글을 써야한다.
        // 세션에서 로그인한 정보를 읽어들인다. 로그인을 하지 않았다면 리스트로 이동시킨다.
        System.out.println("title = " + title);
        // 로그인 회원 정보 + 제목, 내용을 저장한다.
        return "redirect:/";
    }
}
