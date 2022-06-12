package com.example.jpatest.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "로그인 응답")
@Getter
@NoArgsConstructor
public class LoginResponseDTO {

    @Schema(description = "Json Web Token", example = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJjb29rZWh3ayIsImlhdCI6MTY1NDg0NjY3NywiZXhwIjoxNjg0ODQ2Njc3fQ.BLdJ_2NCP4kElFDqdpL-021uUuoYKVOeFtNYrXGsQRo")
    private String token;

    @Builder
    public LoginResponseDTO(String token) {
        this.token = token;
    }
}
