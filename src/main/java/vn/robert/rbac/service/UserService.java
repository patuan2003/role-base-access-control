package vn.robert.rbac.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import vn.robert.rbac.dto.request.UserRequest;
import vn.robert.rbac.entity.User;

import java.util.Optional;

public interface UserService {

    UserDetailsService userDetailsService();

    void createUser(UserRequest request);

    Optional<User> findUserByName(String username);

}

