package vn.nht.social.Config;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class jwtValidator extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String jwt = request.getHeader(JwtConstant.JWT_HEADER);
        if(jwt != null){
            try {
                String email = JwtProvider.getEmailFromJwtToken(jwt);
                List<GrantedAuthority> authorities = new ArrayList<>();// Lấy danh sách quyền từ mã thông báo JWT
                Authentication authentication = new UsernamePasswordAuthenticationToken(email, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (ExpiredJwtException e) {
                // Xử lý token đã hết hạn
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token has expired");
                return;
            } catch (MalformedJwtException | UnsupportedJwtException | IllegalArgumentException e) {
                // Xử lý token không hợp lệ hoặc không được hỗ trợ
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
                return;
            }
        }
        filterChain.doFilter(request, response);
    }
}
