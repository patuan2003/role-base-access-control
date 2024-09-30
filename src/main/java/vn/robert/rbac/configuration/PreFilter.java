package vn.robert.rbac.configuration;

import io.micrometer.common.util.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import vn.robert.rbac.service.JwtService;
import vn.robert.rbac.service.TokenService;
import vn.robert.rbac.service.UserService;
import vn.robert.rbac.util.TokenType;

import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class PreFilter extends OncePerRequestFilter {

    private final UserService userService;
    private final JwtService jwtService;
    private final TokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final var authorization = request.getHeader("Authorization");
        if (StringUtils.isBlank(authorization) || !authorization.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String token = authorization.substring("Bearer ".length());
        final String username = jwtService.extractUsername(token, TokenType.ACCESS_TOKEN);
        final var currentToken = tokenService.getByUsername(username);

        if (StringUtils.isEmpty(currentToken.getAccessToken())) {
            filterChain.doFilter(request, response);
            return;
        }

        if (StringUtils.isNotEmpty(username) && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userService.userDetailsService().loadUserByUsername(username);
            if (jwtService.isValid(token, TokenType.ACCESS_TOKEN, userDetails)) {
                // TODO SecurityContext lưu trữ thông tin xác thực của người dùng, bao gồm các role.
                SecurityContext securityContext = SecurityContextHolder.getContext();
                // TODO đại dện cho info các thực người dùng
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
               // TODO người dùng đã được xác thực và lưu trữ
                securityContext.setAuthentication(authenticationToken);
                SecurityContextHolder.setContext(securityContext);
            }
        }

        filterChain.doFilter(request, response);
    }
}
