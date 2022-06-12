package com.example.jpatest.dto.board;

import com.example.jpatest.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(description = "게시글 작성 요청")
public class WriteRequestDTO {
    private String title;
    private String content;
    private User writer;

}
