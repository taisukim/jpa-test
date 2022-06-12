package com.example.jpatest.service;

import com.example.jpatest.common.AES256Provider;
import com.example.jpatest.common.JwtProvider;
import com.example.jpatest.domain.User;
import com.example.jpatest.domain.UserRepository;
import com.example.jpatest.dto.user.*;
import com.example.jpatest.exception.CustomException;
import com.example.jpatest.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
//필수인자 생성자 자동생성하는것같은데 Autowired 와 private final 로 필드로 넣어놓는것이 어떤차이인지 모르겠음
@RequiredArgsConstructor
public class UserService {

    private final JwtProvider jwtProvider;
    private final AES256Provider aes256Provider;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(rollbackFor=Exception.class)
    public SignupResponseDTO signUp(SignupRequestDTO signupRequestDTO) throws Exception{
        String userId = signupRequestDTO.getUserId();

        String password = signupRequestDTO.getPassword();

        String regNo = signupRequestDTO.getRegNo();

        String name = signupRequestDTO.getName();

        Optional<User> existUser = userRepository.findById(userId);
        if(existUser.isPresent()){
            throw new CustomException(ErrorCode.ALREADY_SIGNUP);
        }
        //요청받은 비밀번호를 암호화해서 다시넣음
        password = passwordEncoder.encode(password);

        //여기서 생성해서 계정테이블에다가 집어넣는것같음.
        String key = aes256Provider.generateAESKey();

        //aes256provider 에서 만들 메서드로 주민번호와 생성한 키로 암호화 하는것같음.
        //그래서 키를 계정테이블에다가 집어넣는것같음 나중에 주민번호 출력할때 해독하려고
        String saveRegNo = aes256Provider.encrypt(regNo, key);

        User user = User.builder().
                id(userId).password(password)
                .name(name).secret(key)
                .regNo(saveRegNo).build();

        userRepository.save(user);

        return SignupResponseDTO.builder().message(HttpStatus.OK.name()).build();

    }

    @Transactional(readOnly = true)
    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {
        String id = loginRequestDTO.getId();
        String password = loginRequestDTO.getPassword();

        Optional<User> existUser = userRepository.findById(id);
        if(existUser.isEmpty()){
            throw new CustomException(ErrorCode.NOT_FOUND_ID);
        }
        User user = existUser.get();
        if(!passwordEncoder.matches(password, user.getPassword())){
            throw new CustomException(ErrorCode.PASSWORD_FAIL);
        }
        //jwt 토큰생성!
        String token = this.jwtProvider.createToken(id);

        return LoginResponseDTO.builder().token(token).build();
    }

    @Transactional(readOnly = true)
    public MyInfoResponseDTO myInfo(String userId) throws Exception{

        String id = userId;
        Optional<User> info = userRepository.findById(id);
        if(info.isEmpty()){
            throw new CustomException(ErrorCode.NOT_FOUND_ID);
        }
        User user = info.get();
        String name = user.getName();
        String key = user.getSecret();
        String regNo = user.getRegNo();

        regNo = aes256Provider.decrypt(regNo, key);

        MyInfoResponseDTO response = MyInfoResponseDTO.builder().id(id).name(name).regNo(regNo).build();

        return response;
    }
}
