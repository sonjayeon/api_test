package com.example.api_test.controller;

import com.example.api_test.domain.*;
import com.example.api_test.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
@Slf4j
public class BoardController {
    private final BoardService boardService;

    @GetMapping(value = "", produces = "application/json")
    public ResponseEntity<Object> getBoard() {
        List<BoardEntity> dto = boardService.selectBoard();
        return ResultEntity.SUCCESS.response(dto);
    }

    @PostMapping(value = "", produces = "application/json")
    public ResponseEntity<Object> insertBoard(@PathVariable("cmn") int cmn, String title, String payload, HttpServletRequest request) {
        if (cmn != ((MemberEntity) request.getAttribute("memberEntity")).getCmn()) {
            throw new IllegalStateException("invalidCmn");
        }
        BoardDto dto = boardService.insertBoard(cmn, title, payload);
        return ResultEntity.SUCCESS.response(dto);
    }

    @GetMapping(value = "/{seq}", produces = "application/json")
    public ResponseEntity<Object> detailBoard(@PathVariable("seq") int seq) {
        BoardDto dto = boardService.detailBoard(seq);
        return ResultEntity.SUCCESS.response(dto);
    }
}
