package com.example.board.service;

import com.example.board.dao.BoardDao;
import com.example.board.dto.Board;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardDao boardDao;

    @Transactional
    public void addBoard(int userId, String title, String content) {
        boardDao.addBoard(userId, title, content);
    }

    @Transactional(readOnly = true) // select 만 할때
    public int getTotalCount() {
        return boardDao.getTotalCount();
    }

    @Transactional(readOnly = true)
    public List<Board> getBoards(int page) {
        return boardDao.getBoards(page);
    }

    @Transactional
    public Board getBoard(int boardId) {
        // id에 해당하는 게시물을 읽어온다.
        // id에 해당하는 게시물의 조회수도 1증가한다.
        return getBoard(boardId, true);
    }

    // updateViewCnt = true 면 조회수 증가, false 면 증가하지 않음
    @Transactional
    public Board getBoard(int boardId, boolean updateViewCnt) {
        Board board = boardDao.getBoard(boardId);
        if (updateViewCnt) {
            boardDao.updateViewCnt(boardId);
        }
        return board;
    }

    @Transactional
    public void deleteBoard(int userId, int boardId) {
        Board board = boardDao.getBoard(boardId);
        if (board.getUserId() == userId) {
            boardDao.deleteBoard(boardId);
        }
    }

    @Transactional
    public void updateBoard(int boardId, String title, String content) {
        boardDao.updateBoard(boardId, title, content);
    }
}
