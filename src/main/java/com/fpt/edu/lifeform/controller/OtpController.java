package com.fpt.edu.lifeform.controller;

import com.fpt.edu.lifeform.service.OTPService;
import com.fpt.edu.lifeform.utils.annotation.ApiMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/api/v1/otp")
@RequiredArgsConstructor
public class OtpController {

    private final OTPService otpService;

    @ApiMessage("Kiểm tra OTP thành công!")
    @PostMapping("/{code}")
    public ResponseEntity<Boolean> checkOtp(@PathVariable("code") String code) {
        return ResponseEntity.ok(otpService.checkOtp(code));
    }

}
