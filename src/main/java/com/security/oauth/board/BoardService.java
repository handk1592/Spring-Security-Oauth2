package com.security.oauth.board;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BoardService implements BoardMapper {

    private BoardMapper mapper;

    @Override
    public List<Board> getBoardList() {
        return mapper.getBoardList();
    }

}
