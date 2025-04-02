package hello.hello_spring.filter;

import hello.hello_spring.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            System.out.println("→ 추출된 토큰: " + token);
            try {
                Long memberId = jwtUtil.validateAndGetMemberId(token);
                System.out.println("→ 인증된 memberId: " + memberId);
                request.setAttribute("memberId", memberId);
            } catch (Exception e) {
                System.out.println("→ 토큰 검증 실패: " + e.getMessage());
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT");
                return;
            }
        } else {
            System.out.println("→ Authorization 헤더가 없거나 형식이 잘못됨");
        }
        System.out.println("[JwtAuthFilter] 실행됨");
        System.out.println("Authorization 헤더: " + authHeader);
        filterChain.doFilter(request, response);
    }
}

