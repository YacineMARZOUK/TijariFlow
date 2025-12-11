package org.example.tjariflow.controller;

import org.example.tjariflow.dto.request.LoginRequestDTO;
import org.example.tjariflow.dto.response.AuthResponseDTO;
import org.example.tjariflow.model.entity.User;
import org.example.tjariflow.service.AuthService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    public static final String USER_ID_KEY = "USER_ID";
    public static final String USER_ROLE_KEY = "USER_ROLE";

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginRequestDTO, HttpSession session) {
        User auth = authService.login(loginRequestDTO);
        session.setAttribute(USER_ID_KEY, auth.getId());
        session.setAttribute(USER_ROLE_KEY, auth.getRole());
        AuthResponseDTO res = new AuthResponseDTO();
        res.setId(auth.getId());
        res.setUseName( auth.getUserName());
        res.setRole(auth.getRole());
        if ("ADMIN".equals(auth.getRole())) {
            res.setMessage( auth.getRole() + " authentifié avec succès !");
        } else {
            res.setMessage( auth.getRole() + " authentifié avec succès !");
        }
        return ResponseEntity.ok(res);
    }
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session){

        session.invalidate();

        return ResponseEntity.ok(Map.of("message", "Déconnexion réussie."));
    }

}
