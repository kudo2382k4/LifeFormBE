package com.fpt.edu.lifeform.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegisterRequest {

    @Pattern(
            regexp = "^[a-zA-ZÀ-ỹ\\s]+$",
            message = "Họ và tên chỉ được chứa chữ cái!"
    )
    @NotBlank(message = "Họ và tên không được bỏ trống!")
    String name;

    @Pattern(
            regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
            message = "Email không đúng định dạng!"
    )
    @NotBlank(message = "Email không được bỏ trống!")
    String email;

    @Pattern(
            regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$",
            message = "Mật khẩu phải chứa ít nhất 8 kí tự, bao gồm chữ cái và số!"
    )
    @NotBlank(message = "Mật khẩu không được để trống!")
    String password;
}
