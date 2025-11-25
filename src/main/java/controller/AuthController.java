package controller;

import dto.LoginRequest;
import entity.User;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.impl.AuthService;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    public static final String SESSION_USER_KEY = "CURRENT_USER";

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequest request, HttpSession session) {
        User user = authService.authenticate(request);

        // Stocke les informations critiques dans la session (ID et Rôle)
        session.setAttribute(SESSION_USER_KEY, user.getId());
        session.setAttribute("USER_ROLE", user.getRole());

        return ResponseEntity.ok("Connexion réussie. Bienvenue " + user.getUsername() + " (" + user.getRole() + ")");
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        session.invalidate(); // Invalide la session
        return ResponseEntity.ok("Déconnexion réussie.");
    }



}
