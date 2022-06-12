package com.example.jpatest.dto.user;

import com.example.jpatest.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "내정보 응답")
@Getter
@NoArgsConstructor
public class MyInfoResponseDTO {
    @Schema(description = "아이디", example = "cookehwk")
    private String id;
    @Schema(description = "이름", example = "박보영")
    private String name;
    @Schema(description = "주민번호", example = "860824-1655068")
    private String regNo;


    @Builder
    public MyInfoResponseDTO(String id, String name, String regNo) {
        this.id = id;
        this.name = name;
        this.regNo = regNo;
    }
}
