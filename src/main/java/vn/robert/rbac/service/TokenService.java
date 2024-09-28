package vn.robert.rbac.service;

import org.springframework.stereotype.Service;
import vn.robert.rbac.entity.Token;
import vn.robert.rbac.exception.ResourceNotFoundException;
import vn.robert.rbac.repository.TokenRepository;

import java.util.Optional;

@Service
public record TokenService(TokenRepository tokenRepository) {

    public int createToken(Token token) {
        Optional<Token> optionalToken = tokenRepository.findTokenByUsername(token.getUsername());
        if (optionalToken.isEmpty()) {
            tokenRepository.save(token);
            return token.getId();
        } else {
            Token currentToken = optionalToken.get();
            currentToken.setAccessToken(token.getAccessToken());
            currentToken.setRefreshToken(token.getRefreshToken());
            tokenRepository.save(currentToken);
            return currentToken.getId();
        }
    }

    public String changeStatus(Token token) {
        tokenRepository.delete(token);

        return "deleted !";
    }

    public Token getByUsername(String username) {
        return tokenRepository.findTokenByUsername(username).orElseThrow(() -> new ResourceNotFoundException("Token not found"));
    }

}
