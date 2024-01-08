package com.example.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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
        return "list";
    }

    @GetMapping("/board")
    public String board(
            @RequestParam("id") int id
    ) {
        System.out.println("id = " + id);
        return "board";
    }
}
