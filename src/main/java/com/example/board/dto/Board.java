package com.example.board.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Setter
@Getter
@ToString
public class Board {
    private int boardId;
    private String title;
    private String content;
    private int userId;
    private LocalDateTime regdate;
    private int viewCnt;
}

/*
board_id	int	NO	PRI		auto_increment
title	varchar(100)	NO
content	text	YES
user_id	int	NO	MUL
regdate	timestamp	YES		CURRENT_TIMESTAMP	DEFAULT_GENERATED
view_cnt	int	YES		0
 */
