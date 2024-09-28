package vn.robert.rbac.dto.response;

import lombok.Builder;
import lombok.Setter;
import vn.robert.rbac.util.Gender;
import vn.robert.rbac.util.UserStatus;
import vn.robert.rbac.util.UserType;

import java.util.Date;

@Setter
@Builder
public class UserResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String phone;
    private Date dob;
    private Gender gender;
    private UserStatus status;
    private UserType type;

}
