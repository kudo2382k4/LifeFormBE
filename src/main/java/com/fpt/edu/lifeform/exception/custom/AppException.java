package com.fpt.edu.lifeform.exception.custom;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppException extends RuntimeException {
    private String message;

}