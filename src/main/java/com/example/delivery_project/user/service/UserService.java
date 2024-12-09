package com.example.delivery_project.user.service;

import com.example.delivery_project.common.config.PasswordEncoder;
import com.example.delivery_project.common.util.ValidationUtil;
import com.example.delivery_project.user.dto.CreateUserRequestDto;
import com.example.delivery_project.user.dto.CreateUserResponseDto;
import com.example.delivery_project.user.dto.DeleteUserRequestDto;
import com.example.delivery_project.user.dto.LoginRequestDto;
import com.example.delivery_project.user.dto.LoginUserDto;
import com.example.delivery_project.user.entity.User;
import com.example.delivery_project.user.entity.UserWithdraw;
import com.example.delivery_project.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public CreateUserResponseDto createUser(CreateUserRequestDto requestDto) {

        if (!ValidationUtil.isValidPasswordFormat(requestDto.getPassword())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                "비밀번호는 최소 8자, 대소문자 포함한 영문, 숫자, 특수문자를 포함해야합니다.");
        }

        User user = new User(
            requestDto.getUserName(),
            requestDto.getUserEmail(),
            passwordEncoder.encode(requestDto.getPassword()),
            requestDto.getAuthority()
        );

        User userByEmail = userRepository.findByEmail(requestDto.getUserEmail());

        if (userByEmail != null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "중복된 이메일 입니다.");
        }

        User savedUser = userRepository.save(user);

        return new CreateUserResponseDto(
            savedUser.getId(),
            savedUser.getName(),
            savedUser.getEmail(),
            savedUser.getCreatedAt()
        );
    }

    public LoginUserDto login(LoginRequestDto requestDto) {

        User findUser = userRepository.findByEmail(requestDto.getUserEmail());

        if (findUser == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 유저 이메일 입니다");
        }

        if (!passwordEncoder.matches(requestDto.getPassword(), findUser.getPassword())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "비밀번호가 일치하지 않습니다");
        }

        if (findUser.getUserWithdraw().equals(UserWithdraw.WITHDRAWN)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "탈퇴된 회원입니다.");
        }

        return new LoginUserDto(findUser.getId(), findUser.getAuthority());
    }

    public void deleteUser(Long userId, DeleteUserRequestDto requestDto) {
        User findUser = userRepository.findByIdOrElseThrow(userId);

        if (!passwordEncoder.matches(requestDto.getPassword(), findUser.getPassword())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "비밀번호가 일치하지 않습니다");
        }

        findUser.deleteUser();

        userRepository.save(findUser);
    }
    public User findById(Long userId){
        return userRepository.findByIdOrElseThrow(userId);
    }
}
