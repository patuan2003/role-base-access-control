package vn.robert.rbac.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import vn.robert.rbac.dto.request.UserRequest;
import vn.robert.rbac.dto.response.ResponseData;
import vn.robert.rbac.service.UserService;

@RequestMapping("/api/v1/users")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public String createUser(@Valid @RequestBody UserRequest request) {
        userService.createUser(request);
        return "haha";
    }

    @GetMapping
    public ResponseData<?> getUser(@RequestParam String username) {
        return new ResponseData<>(HttpStatus.OK.value(), "List user", userService.findUserByName(username));
    }


}
