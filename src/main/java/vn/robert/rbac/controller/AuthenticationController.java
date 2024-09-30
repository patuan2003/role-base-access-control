package vn.robert.rbac.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.robert.rbac.dto.request.SignInRequest;
import vn.robert.rbac.dto.response.ResponseData;
import vn.robert.rbac.service.impl.AuthenticationService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/access-token")
    public ResponseData<?> login(@RequestBody SignInRequest request) {
        return new ResponseData<>(HttpStatus.OK.value(), "access_token", authenticationService.authenticate(request));
    }

    @PostMapping("/refresh-token")
    public ResponseData<?> refresh(HttpServletRequest request) {
        return new ResponseData<>(HttpStatus.OK.value(), "refresh_token", authenticationService.refreshToken(request));
    }

    @PostMapping("/remove-token")
    public ResponseData<String> logout(HttpServletRequest request) {
        return new ResponseData<>(HttpStatus.OK.value(), "logout", authenticationService.logout(request));
    }

    @PostMapping("/forgot-password")
    public ResponseData<String> forgotPassword(@RequestBody String email) {
        // implement forgot password logic
        return new ResponseData<>(HttpStatus.OK.value(), "forgot_password", "Send email success");
    }

}
