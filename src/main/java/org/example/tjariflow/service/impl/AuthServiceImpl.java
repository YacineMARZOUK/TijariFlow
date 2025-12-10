package org.example.tjariflow.service.impl;
import org.example.tjariflow.dto.request.LoginRequestDTO;
import org.example.tjariflow.exception.InvalidCredentialsException;
import org.example.tjariflow.exception.ResourceNotFoundException;
import org.example.tjariflow.model.entity.User;
import org.example.tjariflow.repository.UserRepository;
import org.example.tjariflow.service.AuthService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.mindrot.jbcrypt.BCrypt;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;

    @Override
    @Transactional
    public User login(LoginRequestDTO loginRequestDTO){
        User user = userRepository.findByUserName(loginRequestDTO.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("userName not found"));
        boolean passwordMatches = BCrypt.checkpw(loginRequestDTO.getPassword(),user.getPassword());
        if(!passwordMatches){
            throw new InvalidCredentialsException("Invalid username or password");
        }
        return user;
    }


}