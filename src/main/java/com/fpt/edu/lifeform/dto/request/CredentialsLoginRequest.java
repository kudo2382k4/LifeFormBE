package com.fpt.edu.lifeform.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CredentialsLoginRequest {

    @NotBlank(message = "Email không được bỏ trống!")
    String email;

    @NotBlank(message = "Mật khẩu không được bỏ trống!")
    String password;
}
