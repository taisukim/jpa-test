package com.example.jpatest.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * 로직에서 사용하는 에러 정보 enum
 *
 * @author  kimjh
 * @version 1.0
 */
@RequiredArgsConstructor
@Getter
public enum ErrorCode {
    SUCCESS(HttpStatus.OK, HttpStatus.OK.getReasonPhrase()),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    NOT_FOUND_PEOPLE(HttpStatus.NOT_FOUND, "가능한 사용자 정보가 존재하지 않습니다."),
    ALREADY_SIGNUP(HttpStatus.CONFLICT, "이미 가입이 되어 있는 사용자 입니다."),
    NAME_MISMATCH(HttpStatus.CONFLICT, "이름 정보가 다릅니다."),
    NOT_FOUND_USER(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."),
    NEED_TO_LOGIN(HttpStatus.UNAUTHORIZED, "사용자 인증이 필요합니다."),
    NOT_FOUND_ID(HttpStatus.NOT_FOUND, "가입한 회원이 아닙니다."),
    PASSWORD_FAIL(HttpStatus.CONFLICT, "비밀번호가 일치하지 않습니다."),

    JWT_NO_TOKEN(HttpStatus.NOT_FOUND, "토큰정보가 없습니다."),
    JWT_SIGNATURE_ERROR(HttpStatus.CONFLICT, "서명 오류입니다."),
    JWT_MALFORMED_ERROR(HttpStatus.CONFLICT, "옳지 않는 형식입니다."),
    JWT_EXPIRED_ERROR(HttpStatus.UNAUTHORIZED, "인증정보가 만료되었습니다."),
    JWT_UNSUPPORTED_ERROR(HttpStatus.BAD_REQUEST, "지원하지 않는 인증입니다."),
    JWT_ILLEGAL_ARGUMENT_ERROR(HttpStatus.BAD_REQUEST, "잘못된 형식입니다."),
    JWT_EXCEPTION(HttpStatus.BAD_REQUEST, "인증 정보의 예외가 있습니다."),

    AUTH_INFO_FAIL(HttpStatus.BAD_REQUEST, "옳지 않은 인증 정보 입니다."),
    SCRAP_PROTOCOL_ERROR(HttpStatus.BAD_GATEWAY, "데이터 조회 연동 에러입니다."),

    NOT_FOUND_SCRAP(HttpStatus.NOT_FOUND, "스크랩 정보가 존재하지 않습니다."),
    NOT_SCRAP_PARSE(HttpStatus.CONFLICT, "스크랩 정보를 확인 할 수 없습니다."),
    ENCRYPTION_ERROR(HttpStatus.UNAUTHORIZED, "암호화 처리에 문제가 있습니다.");

    private final HttpStatus  status      ;
    private final String      message     ;
}
