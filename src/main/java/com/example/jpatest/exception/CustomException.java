package com.example.jpatest.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException{
    private final ErrorCode errorCode;

    //에러코드값에 생성자로 ErrorCode를 생성시켜서 에러코드? 와 메세지를 둘다 갖는다 하지만
    //여기는 해당 메세지가 아닌 다른 메세지를 넣고싶을때 ErrorCode 와같이 오버로드해서 메세지를 직접 집어넣어서
    //ErrorCode에있는 메세지가아니고 직접 입력한 메세지를 넣어서 CustomException을 생성한다.
    public CustomException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public CustomException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
