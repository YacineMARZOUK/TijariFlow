package org.example.tjariflow.config;
import org.example.tjariflow.exception.AuthorizationException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;


@Component
public class AuthInterceptor implements HandlerInterceptor {

    public static final String USER_ID_KEY = "USER_ID";
    public static final String USER_ROLE_KEY = "USER_ROLE";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute(USER_ID_KEY) == null) {
            throw new AuthorizationException(
                    "Session expired or user not authenticated. Please log in."
            );
        }

        String role = session.getAttribute(USER_ROLE_KEY).toString(); // ADMIN ou CLIENT
        String path = request.getRequestURI();

        if ("CLIENT".equals(role)) {
            if (path.startsWith("/api/products/admin") ||
                    path.startsWith("/api/orders/admin") ||
                    path.startsWith("/api/client/admin") ||
                    path.startsWith("/api/payments/admin"))
            {
                throw new AuthorizationException(
                        "Access denied. This operation is restricted to administrators."
                );
            }


        }

        return true;
    }
}
