package vn.robert.rbac.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import vn.robert.rbac.util.*;

import java.io.Serializable;
import java.util.Date;

import static vn.robert.rbac.util.Gender.*;

@Getter
@Setter
public class UserRequest implements Serializable {

    @NotBlank(message = "firstName not null")
    private String firstName;

    @NotBlank(message = "lastName not null")
    private String lastName;

    @NotNull(message = "dateOfBirth not null")
    @JsonFormat(pattern = "MM/dd/yyyy")
    private Date dateOfBirth;

    @EnumPattern(name = "status", regexp = "ACTIVE|INACTIVE|NONE")
    private UserStatus status;

    @GenderSubset(anyOf = {MALE, FEMALE, OTHER})
    private Gender gender;

    @PhoneNumber
    private String phone;

    @NotNull(message = "type must not be null")
    @EnumValue(name = "type", enumClass = UserType.class)
    private String type;

    @Email(message = "email invalid format")
    private String email;

    private String username;

    private String password;

}
