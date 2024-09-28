package vn.robert.rbac.service;

import org.springframework.security.core.userdetails.UserDetails;
import vn.robert.rbac.util.TokenType;

public interface JwtService {

    String generateToken(UserDetails userDetails);

    String generateRefreshToken(UserDetails userDetails);

    String extractUsername(String token, TokenType type);

    boolean isValid(String token, TokenType type, UserDetails userDetails);

}
