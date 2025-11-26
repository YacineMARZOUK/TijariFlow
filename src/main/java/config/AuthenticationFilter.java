package config;

import controller.AuthController;
import enums.UserRole;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import jakarta.servlet.Filter;

public class AuthenticationFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletRequest;
        String path = request.getRequestURI();
        HttpSession session = request.getSession(false);

        // the endpoints publiccc
        if(path.startsWith("/api/auth/") || path.equals("/") || path.endsWith("/swagger-ui.html") || path.contains("webjars") || path.contains("v3/api-docs")){
            filterChain.doFilter(request,response);
            return;
        }
        // session verificationn
        if (session == null || session.getAttribute(AuthController.SESSION_USER_KEY) == null){
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json");
            response.getWriter().write("{\\\"status\\\": 401, \\\"errorType\\\": \\\"Unauthorized\\\", \\\"message\\\": \\\"Authentification requise.\\\"}\"");
            return;
        }

        UserRole userRole = (UserRole) session.getAttribute("USER_ROLE");
        if (path.startsWith("/api/admin/") && userRole != UserRole.ADMIN) {
            response.setStatus(HttpStatus.FORBIDDEN.value()); // 403
            response.setContentType("application/json");
            response.getWriter().write("{\"status\": 403, \"errorType\": \"Forbidden\", \"message\": \"Accès refusé. Rôle ADMIN requis.\"}");
            return;
        }
    }

}
