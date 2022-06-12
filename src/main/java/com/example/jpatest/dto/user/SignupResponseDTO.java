package com.example.jpatest.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "회원가입 응답")
@Getter
@NoArgsConstructor
public class SignupResponseDTO {
    @Schema(description = "응답 메세지", example = "OK")
    private String message;

    @Builder
    public SignupResponseDTO(String message){
        this.message = message;
    }
}
