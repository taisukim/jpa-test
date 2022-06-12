package com.example.jpatest.exception;

import com.example.jpatest.dto.ErrorResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        log.error(e.toString());
        String errorMessage = e.getBindingResult()
                .getAllErrors()
                .get(0).getDefaultMessage();
        CustomException customException = new CustomException(ErrorCode.BAD_REQUEST, errorMessage);
        ErrorResponseDTO  responseDTO = ErrorResponseDTO.builder().error(customException.getMessage()).build();
        return ResponseEntity.status(customException.getErrorCode().getStatus()).body(responseDTO);
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponseDTO> handlerCustomException(CustomException e){
        log.error(e.toString());
        ErrorResponseDTO errorResponseDTO = ErrorResponseDTO.builder().error(e.getMessage()).build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponseDTO);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handlerException(Exception e){
        log.error(e.toString());
        ErrorResponseDTO errorResponseDTO = ErrorResponseDTO.builder().error(e.getMessage()).build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponseDTO);
    }
}
