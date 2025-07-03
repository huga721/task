package pl.hszaba.betacomtask.common.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import pl.hszaba.betacomtask.user.domain.UserRepository;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtManager jwtManager;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);

        String login;
        try {
            login = jwtManager.extractLogin(token);
        } catch (Exception e) {
            filterChain.doFilter(request, response);
            return;
        }

        if (login != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            var user = userRepository.findByLogin(login)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            if (jwtManager.isTokenValid(token, user)) {
                var auth = new UsernamePasswordAuthenticationToken(
                        new Operator(user.getLogin()), null, List.of()
                );
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }

        filterChain.doFilter(request, response);
    }
}
