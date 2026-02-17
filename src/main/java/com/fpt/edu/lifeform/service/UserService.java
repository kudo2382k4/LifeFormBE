package com.fpt.edu.lifeform.service;

import com.fpt.edu.lifeform.dto.request.RegisterRequest;
import com.fpt.edu.lifeform.dto.response.PageDetailsResponse;
import com.fpt.edu.lifeform.dto.response.UserResponse;
import com.fpt.edu.lifeform.entity.OTPEntity;
import com.fpt.edu.lifeform.entity.RoleEntity;
import com.fpt.edu.lifeform.entity.UserEntity;
import com.fpt.edu.lifeform.exception.custom.AccountException;
import com.fpt.edu.lifeform.exception.custom.RoleException;
import com.fpt.edu.lifeform.exception.custom.UserException;
import com.fpt.edu.lifeform.repository.OTPRepo;
import com.fpt.edu.lifeform.repository.RoleRepo;
import com.fpt.edu.lifeform.repository.UserRepo;
import com.fpt.edu.lifeform.utils.BuildResponse;
import com.fpt.edu.lifeform.utils.enums.AccountTypeEnum;
import com.fpt.edu.lifeform.utils.enums.RoleEnum;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepo userRepo;
    private final OTPRepo otpRepo;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepo roleRepo;
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

        RoleEntity roleEntity = roleRepo.findByName(RoleEnum.USER)
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

    //get all user
    public PageDetailsResponse<List<UserResponse>> getAllUsers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<UserEntity> userEntityPage = userRepo.findAll(pageable);
        List<UserResponse> userResponses = userEntityPage.map(userEntity -> modelMapper.map(userEntity, UserResponse.class)).toList();
        return BuildResponse.buildPageDetailsResponse(
                userEntityPage.getNumber() + 1,
                userEntityPage.getSize(),
                userEntityPage.getTotalPages(),
                userEntityPage.getTotalElements(),
                userResponses
        );
    }

}
