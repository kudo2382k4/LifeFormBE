package com.fpt.edu.lifeform.service;

import com.fpt.edu.lifeform.dto.request.RegisterRequest;
import com.fpt.edu.lifeform.entity.OTPEntity;
import com.fpt.edu.lifeform.entity.RoleEntity;
import com.fpt.edu.lifeform.entity.UserEntity;
import com.fpt.edu.lifeform.exception.custom.AccountException;
import com.fpt.edu.lifeform.exception.custom.RoleException;
import com.fpt.edu.lifeform.repository.OTPRepo;
import com.fpt.edu.lifeform.repository.RoleRepository;
import com.fpt.edu.lifeform.repository.UserRepo;
import com.fpt.edu.lifeform.utils.enums.AccountTypeEnum;
import com.fpt.edu.lifeform.utils.enums.RoleEnum;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepo userRepo;
    private final OTPRepo otpRepo;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final OTPService otpService;

    public Void sendRegisterRequest(RegisterRequest registerRequest) {
        Optional<UserEntity> optionalUserEntity = userRepo.findByEmailAndAccountType(registerRequest.getEmail(), AccountTypeEnum.CREDENTIAL);
        if (optionalUserEntity.isPresent()) {
            UserEntity existedUserEntity = optionalUserEntity.get();
            if (Boolean.TRUE.equals(existedUserEntity.getActive())) {
                throw new AccountException("Email này đã được sử dụng!");
            } else {
                OTPEntity otpEntity = existedUserEntity.getOtpEntity();
                existedUserEntity.setOtpEntity(null);
                UserEntity savedUserEntity = userRepo.save(existedUserEntity);
                otpRepo.delete(otpEntity);
                userRepo.delete(savedUserEntity);
            }
        }

        RoleEntity roleEntity = roleRepository.findByName(RoleEnum.USER)
                .orElseThrow(() -> new RoleException("Role not found!"));
        UserEntity accountEntity = modelMapper.map(registerRequest, UserEntity.class);
        accountEntity.setAccountType(AccountTypeEnum.CREDENTIAL);
        accountEntity.setActive(false);
        accountEntity.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        accountEntity.setRole(roleEntity);
        UserEntity savedAccount = userRepo.save(accountEntity);
        otpService.generateOTP(savedAccount, "Yêu cầu xác thực email!");
        return null;
    }

}
