package com.example.board.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Board {
    private int boardId;
    private String title;
    private String content;
    private String name; // join을 위한 컬럼 추가
    private int userId;
    private LocalDateTime regdate;
    private int ViewCnt;
}
