package com.fpt.edu.lifeform.controller;

import com.fpt.edu.lifeform.dto.request.EmailRequest;
import com.fpt.edu.lifeform.dto.request.RegisterRequest;
import com.fpt.edu.lifeform.dto.request.ResetPasswordRequest;
import com.fpt.edu.lifeform.exception.custom.NotFoundException;
import com.fpt.edu.lifeform.service.OTPService;
import com.fpt.edu.lifeform.service.UserService;
import com.fpt.edu.lifeform.utils.annotation.ApiMessage;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final OTPService otpService;

    @ApiMessage("Gửi yêu cầu đăng kí tài khoản thành công!")
    @PostMapping("/register-request")
    public ResponseEntity<Void> sendRegisterRequest(@RequestBody @Valid RegisterRequest registerRequest) {
        return ResponseEntity.ok(userService.sendRegisterRequest(registerRequest));
    }

    @ApiMessage("Kích hoạt tài khoản thành công!")
    @GetMapping("/active/{code}")
    public ResponseEntity<Void> activeAccount(@PathVariable String code) throws NotFoundException {
        return ResponseEntity.ok(otpService.activeAccount(code));
    }

    @ApiMessage("Gửi yêu cầu cập nhật mật khẩu thành công!")
    @PostMapping("request-reset-password")
    public ResponseEntity<Void> requestResetPassword(@RequestBody @Valid EmailRequest request) {
        return ResponseEntity.ok(userService.sendRequestResetPassword(request));
    }

    @ApiMessage("Cập nhật mật khẩu thành công!")
    @PostMapping("reset-password")
    public ResponseEntity<Void> resetPassword(@RequestBody @Valid ResetPasswordRequest request) {
        return ResponseEntity.ok(userService.resetPassword(request));
    }

}
