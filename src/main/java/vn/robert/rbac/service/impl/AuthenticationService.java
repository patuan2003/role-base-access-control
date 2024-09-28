package vn.robert.rbac.service.impl;

import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import vn.robert.rbac.dto.request.SignInRequest;
import vn.robert.rbac.dto.response.TokenResponse;
import vn.robert.rbac.entity.Token;
import vn.robert.rbac.exception.InvalidDataException;
import vn.robert.rbac.service.JwtService;
import vn.robert.rbac.service.TokenService;
import vn.robert.rbac.service.UserService;

import static vn.robert.rbac.util.TokenType.ACCESS_TOKEN;
import static vn.robert.rbac.util.TokenType.REFRESH_TOKEN;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserService userService;
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public TokenResponse authenticate(SignInRequest request) {
//        dòng 60 AppConfig 
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        var user = userService.findUserByName(request.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("Username or password incorrect."));
        var accessToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        tokenService.createToken(Token.builder()
                .username(user.getUsername())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build());

        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .userId(user.getId())
                .build();
    }

    public TokenResponse refreshToken(HttpServletRequest request) {
        String refreshToken = request.getHeader("x-token");
        if (StringUtils.isBlank(refreshToken)) {
            throw new InvalidDataException("token must not be blank");
        }

        // extract user from token
        final String username = jwtService.extractUsername(refreshToken, REFRESH_TOKEN);

        //check it into db
        var user = userService.findUserByName(username)
                .orElseThrow(() -> new UsernameNotFoundException("username not found"));
        if (!jwtService.isValid(refreshToken, REFRESH_TOKEN, user)) {
            throw new InvalidDataException("token must not be blank");
        }

        // generate new token
        String accessToken = jwtService.generateToken(user);

        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .userId(user.getId())
                .build();
    }

    public String logout(HttpServletRequest request) {
        String refreshToken = request.getHeader("x-token");
        if (StringUtils.isBlank(refreshToken)) {
            throw new InvalidDataException("token must not be blank");
        }

        final String username = jwtService.extractUsername(refreshToken, ACCESS_TOKEN);

        Token currentToken = tokenService.getByUsername(username);

        return tokenService.changeStatus(currentToken);
    }

}
