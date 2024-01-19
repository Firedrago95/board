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

    @Transactional
    public int getTotalCount() {
        return boardDao.getTotalCount();
    }

    @Transactional
    public List<Board> getBoards(int page) {
        return boardDao.getBoards(page);
    }

    @Transactional
    public Board getBoard(int boardId) {
        return getBoard(boardId, true);
    }

    @Transactional
    public Board getBoard(int boardId, boolean countViewCnt) {
        Board board = boardDao.getBoard(boardId);
        if (countViewCnt) {
            boardDao.increaseViewCnt(boardId);
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

    @Transactional
    public void deleteBoard(int boardId) {
        boardDao.deleteBoard(boardId);
    }
}
