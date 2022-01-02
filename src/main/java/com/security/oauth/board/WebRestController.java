package com.security.oauth.board;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@RestController
public class WebRestController {

    private BoardService boardService;

    @GetMapping("/hello")
    public ResponseEntity<?> hello() {
        Map<String, Object> resultMap = new HashMap<>();
        List<Board> list = boardService.getBoardList();
        resultMap.put("list", list);
        log.info("리스트 결과" + list.toString());
        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }
}
