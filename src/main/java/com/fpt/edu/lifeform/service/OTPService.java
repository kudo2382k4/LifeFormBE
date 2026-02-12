package com.fpt.edu.lifeform.service;

import com.fpt.edu.lifeform.dto.response.ApiResponse;
import com.fpt.edu.lifeform.entity.OTPEntity;
import com.fpt.edu.lifeform.entity.UserEntity;
import com.fpt.edu.lifeform.exception.custom.NotFoundException;
import com.fpt.edu.lifeform.repository.OTPRepo;
import com.fpt.edu.lifeform.repository.UserRepo;
import com.fpt.edu.lifeform.utils.BuildResponse;
import com.fpt.edu.lifeform.utils.enums.AccountTypeEnum;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OTPService {

    private static final String CHARACTERS = "0123456789";
    private static final int OTP_LENGTH = 6;
    private final SecureRandom secureRandom = new SecureRandom();
    private final OTPRepo otpRepo;
    private final EmailSenderService emailSenderService;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
    private final UserRepo userRepo;

    public Void generateOTP(UserEntity user, String title) {
        if (user.getOtpEntity() != null) {
            OTPEntity otpCode = user.getOtpEntity();
            user.setOtpEntity(null);
            userRepo.save(user);
            otpRepo.delete(otpCode);
        }
        StringBuilder otp = new StringBuilder();
        for (int i = 0; i < OTP_LENGTH; i++) {
            int index = secureRandom.nextInt(CHARACTERS.length());
            otp.append(CHARACTERS.charAt(index));
        }
        Map<String, Object> otpMap = new HashMap<>();
        otpMap.put("otp", otp.toString());

        OTPEntity otpCode = new OTPEntity();
        otpCode.setCode(otp.toString());
        otpCode.setUser(user);
        otpCode.setExpiredAt(LocalDateTime.now().plusMinutes(5));
        otpRepo.save(otpCode);

        emailSenderService.sendEmail(user.getEmail(), title, "mail-template", otpMap);
        return null;
    }

    public ApiResponse<Void> checkOTP(String code) {
        OTPEntity otpCode = otpRepo.findByCode(code)
                .orElseThrow(() -> new NotFoundException("Mã OTP sai!"));
        if (otpCode.getExpiredAt().isBefore(LocalDateTime.now())) {
            throw new NotFoundException("Mã OTP đã hết hạn!");
        }
        return BuildResponse.buildApiResponse(200, "Mã OTP hợp lệ!", null, null);
    }


    @Transactional
    public Void activeAccount(String code) {
        OTPEntity otp = otpRepo.findByCode(code)
                .orElseThrow(() -> new NotFoundException("Mã OTP sai!"));
        LocalDateTime now = LocalDateTime.now();
        if (otp.getExpiredAt().isBefore(now)) {
            throw new NotFoundException("Mã OTP của bạn đã hết hạn!");
        }
        UserEntity user = otp.getUser();
        user.setActive(true);
        user.setOtpEntity(null);
        userRepo.save(user);
        otp.setUser(null);
        otpRepo.delete(otp);
        return null;
    }
}