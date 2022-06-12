package com.example.jpatest.dto.user;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Schema(description = "회원가입 요호")
@Getter
@NoArgsConstructor
public class SignupRequestDTO {
    @Schema(description = "아이디", example = "cookehwk")
    @NotEmpty(message = "아이디는 필수 입력 값입니다.")
    private String userId;
    @Schema(description = "비밀번호", example = "123")
    @NotEmpty(message = "비밀번호는 필수 입력 값입니다.")
    private String password;
    @Schema(description = "이름", example = "박보영")
    @NotEmpty(message = "이름은 필수 입력 값잆니다")
    private String name;
    @Schema(description = "주민번호", example = "860824-1655068")
    @NotEmpty(message = "주민번호는 필수 입력 값입니다.")
    @Pattern(regexp = "\\d{2}([0]\\d|[1][0-2])([0][1-9]|[1-2]\\d|[3][0-1])[-]*[1-4]\\d{6}", message="주민번호 입력이 잘못되었습니다.")
    private String regNo;

    @Builder
    public SignupRequestDTO(String userId, String password ,String name, String regNo){
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.regNo = regNo;
    }

}
