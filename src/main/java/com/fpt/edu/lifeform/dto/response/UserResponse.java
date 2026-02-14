package com.fpt.edu.lifeform.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fpt.edu.lifeform.utils.enums.AccountTypeEnum;
import com.fpt.edu.lifeform.utils.enums.GenderEnum;
import com.fpt.edu.lifeform.utils.enums.RoleEnum;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Collection;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {

    String fullname;

    String email;

    String phone;

    String avatar;

    String refreshToken;

    @Enumerated(EnumType.STRING)
    GenderEnum gender;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+7")
    LocalDate dob;

    Boolean active;

    @Enumerated(EnumType.STRING)
    AccountTypeEnum accountType;

    @Enumerated(EnumType.STRING)
    RoleEnum role;

}
