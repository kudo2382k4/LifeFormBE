package com.fpt.edu.lifeform.utils;


import com.fpt.edu.lifeform.dto.response.ApiResponse;
import com.fpt.edu.lifeform.dto.response.LoginResponse;
import com.fpt.edu.lifeform.dto.response.PageDetailsResponse;
import com.fpt.edu.lifeform.dto.response.UserResponse;

import java.time.Instant;

public class BuildResponse {
    public static <T> ApiResponse<T> buildApiResponse(Integer status, Object message, String errorMessage, T data) {
        return ApiResponse.<T>builder()
                .status(status)
                .message(message)
                .errorMessage(errorMessage)
                .data(data)
                .build();
    }

    public static LoginResponse buildLoginResponse(UserResponse userResponse, String accessToken, Instant expireAt, String refreshToken) {
        return LoginResponse.builder()
                .user(userResponse)
                .accessToken(accessToken)
                .expireAt(expireAt)
                .refreshToken(refreshToken)
                .build();
    }

    public static <T> PageDetailsResponse<T> buildPageDetailsResponse(
            Integer currentPage,
            Integer pageSize,
            Integer totalPages,
            Long totalElements,
            T content
    ) {
        return PageDetailsResponse.<T>builder()
                .currentPage(currentPage)
                .pageSize(pageSize)
                .totalPages(totalPages)
                .totalElements(totalElements)
                .content(content)
                .build();
    }
}
