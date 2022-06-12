package com.example.jpatest.common;

import com.example.jpatest.exception.ErrorCode;
import io.jsonwebtoken.*;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.xml.bind.DatatypeConverter;
import java.util.Date;


@Slf4j
@Component
public class JwtProvider {
    private final String secretKey;
    private final long validityInMilliseconds;

    //빌더로 생성자 만들고 yaml에서 지정한 값들 가져와서 변수에 넣고생성
    @Builder
    public JwtProvider(
            @Value("${security.jwt.token.secret-key}") String secretKey
            ,@Value("${security.jwt.token.expire-length}") long validityInMilliseconds) {
        this.secretKey = secretKey;
        this.validityInMilliseconds = validityInMilliseconds;
    }

    public String createToken(String userId){


        Claims claims = Jwts.claims().setId(userId);

        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder().setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    /**
     * JWT 로 부터 Claim 를 구하는 메소드
     * @param token JWT 문자열
     * @return 설정된 Claims
     * @see Claims
     */
    public Claims getClaims(String token) {
        return Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(secretKey))
                .parseClaimsJws(token).getBody();
    }

    /**
     * JWT 이 유효한지를 체크 하는 메소드
     * @param token JWT 문자열
     * @return 에러 코드 enum
     * @see ErrorCode
     */
    public ErrorCode validateToken(String token){

        // 최초 정상 응답 설정
        ErrorCode errorCode = ErrorCode.SUCCESS;

        // token 이 없을 때
        if ( token == null || token.isEmpty()) {
            return ErrorCode.JWT_NO_TOKEN ;
        }

        try {
            // JWT 검증
            Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(secretKey)).parseClaimsJws(token);
        }
        catch (SignatureException e) {
            log.error("Invalid JWT signature");
            errorCode = ErrorCode.JWT_SIGNATURE_ERROR;
        }
        catch (MalformedJwtException e) {
            log.error("Invalid JWT token", e);
            errorCode = ErrorCode.JWT_MALFORMED_ERROR ;
        }
        catch (ExpiredJwtException e) {
            log.error("Expired JWT token", e);
            errorCode = ErrorCode.JWT_EXPIRED_ERROR ;
        }
        catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token", e);
            errorCode = ErrorCode.JWT_UNSUPPORTED_ERROR ;
        }
        catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty.", e);
            errorCode = ErrorCode.JWT_ILLEGAL_ARGUMENT_ERROR;
        }
        catch (Exception e){
            log.error("JWT exception.", e);
            errorCode = ErrorCode.JWT_EXCEPTION ;
        }

        return errorCode;
    }
}
