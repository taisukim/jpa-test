package com.example.jpatest.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ErrorResponseDTO {

    private String error;

    @Builder
    public ErrorResponseDTO(String error) {
        this.error = error;
    }
}
