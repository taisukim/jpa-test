package com.example.jpatest.controller;


import com.example.jpatest.dto.board.WriteRequestDTO;
import com.example.jpatest.dto.user.SignupRequestDTO;
import com.example.jpatest.dto.user.SignupResponseDTO;
import com.example.jpatest.service.BoardService;
import com.example.jpatest.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Slf4j
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @Operation(summary = "게시글 작성", description = "로그인한 정보와 게시글에대한 정보로 게시글 등록"
            , responses = {@ApiResponse(responseCode = "200", description = "등록 성공"
            , content = @Content(mediaType ="application/json"
            , schema = @Schema(implementation = WriteRequestDTO.class)))})
    @PostMapping("/write")
    public ResponseEntity<WriteRequestDTO> signup(@Valid @RequestBody WriteRequestDTO writeRequestDTO) throws Exception{
        WriteRequestDTO response = boardService.insertBoard(writeRequestDTO);
        return ResponseEntity.ok().body(response);
    }
}
