package com.example.wini.global.security;

import static com.example.wini.global.common.constant.SecurityConstants.ACCESS_TOKEN;
import static com.example.wini.global.common.constant.SecurityConstants.BEARER_TOKEN_PREFIX;
import static com.example.wini.global.common.constant.SecurityConstants.HEADER_AUTHORIZATION;
import static com.example.wini.global.common.constant.SecurityConstants.REFRESH_TOKEN;
import static com.example.wini.global.common.constant.SecurityConstants.REISSUE_URL;
import static com.example.wini.global.error.exception.ErrorCode.BLACKLISTED_TOKEN;

import com.example.wini.domain.auth.service.TokenService;
import com.example.wini.global.error.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final ObjectMapper objectMapper;
    private final TokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        final String uri = request.getServletPath();
        final boolean isReissue = uri.equals(REISSUE_URL);

        String authorization = request.getHeader(HEADER_AUTHORIZATION);
        if (authorization == null || !authorization.startsWith(BEARER_TOKEN_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String token = jwtProvider.substringToken(authorization);
            if (isReissue) {
                jwtProvider.validToken(token, REFRESH_TOKEN);
            } else {
                boolean isBlacklisted = tokenService.isAccessTokenBlacklisted(token);
                if (isBlacklisted) {
                    throw new JwtAuthenticationException(BLACKLISTED_TOKEN);
                }

                if (jwtProvider.validToken(token, ACCESS_TOKEN)) {
                    AuthMember authMember = jwtProvider.getAuthentication(token, ACCESS_TOKEN);
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(authMember, null, authMember.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        } catch (JwtAuthenticationException e) {
            setErrorResponse(response, e);
            return;
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }
        filterChain.doFilter(request, response);
    }

    private void setErrorResponse(HttpServletResponse response, JwtAuthenticationException e) throws IOException {
        response.setStatus(e.getHttpStatus().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");

        ErrorResponse errorResponse = ErrorResponse.of(e.getClass().getName(), e.getMessage());

        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
        response.getWriter().flush();
    }
}
