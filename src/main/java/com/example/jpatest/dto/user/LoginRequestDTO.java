package com.example.jpatest.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor
@Schema(description = "로그인 요청")
public class LoginRequestDTO {
    @Schema(description = "아이디", example = "cookehwk")
    @NotEmpty(message = "아이디는 필수 입력 값입니다.")
    private String id;
    @Schema(description = "비밀번호", example = "123")
    @NotEmpty(message = "비밀번호는 필수 입력 값입니다.")
    private String password;

    @Builder
    public LoginRequestDTO(String id, String password) {
        this.id = id;
        this.password = password;
    }
}
