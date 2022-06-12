package com.example.jpatest.controller;

import com.example.jpatest.dto.user.*;
import com.example.jpatest.service.UserService;
import io.jsonwebtoken.Claims;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Slf4j
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "회원가입", description = "회원의 정보를 입력 받아 회원으로 등록한다."
            , responses = {@ApiResponse(responseCode = "200", description = "회원가입성공"
            , content = @Content(mediaType ="application/json"
            , schema = @Schema(implementation = SignupResponseDTO.class)))})
    @PostMapping("/signup")
    public ResponseEntity<SignupResponseDTO> signup(@Valid @RequestBody SignupRequestDTO signupRequestDTO) throws Exception{
        System.out.println("signup------");
        SignupResponseDTO response = userService.signUp(signupRequestDTO);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "로그인", description = "회원의 정보를 받아서 DB에 일치하는값이 존재하는지 확"
            , responses = {@ApiResponse(responseCode = "200", description = "로그인 성공"
            , content = @Content(mediaType ="application/json"
            , schema = @Schema(implementation = LoginResponseDTO.class)))})
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginRequestDTO) throws Exception{
        System.out.println("login------");
        LoginResponseDTO response = userService.login(loginRequestDTO);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "내정보", description = "로그인한 정보를가지고 내정보를 찾음"
            , security = @SecurityRequirement(name = "basicAuth")
            , responses = {@ApiResponse(responseCode = "200", description = "회원정보 조회 성공"
            , content = @Content(mediaType ="application/json"
            , schema = @Schema(implementation = MyInfoResponseDTO.class)))})
    @GetMapping("/me")
    public ResponseEntity<MyInfoResponseDTO> myInfo(Authentication authentication) throws Exception{
        //디버그도 안잡히고 아래잇는 프린트도 출력이 안됩니다
        System.out.println("info------");
        Claims claims = (Claims)authentication.getPrincipal();
        String userId = claims.getId();

        MyInfoResponseDTO response = userService.myInfo(userId);
        return ResponseEntity.ok().body(response);
    }



}