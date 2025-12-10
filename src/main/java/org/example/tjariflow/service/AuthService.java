package org.example.tjariflow.service;
import org.example.tjariflow.dto.request.LoginRequestDTO;
import org.example.tjariflow.model.entity.User;

public interface AuthService {
    User login(LoginRequestDTO loginRequestDTO);
}
