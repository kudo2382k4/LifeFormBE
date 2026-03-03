package com.fpt.edu.lifeform.helper;

import com.fpt.edu.lifeform.dto.request.CredentialsLoginRequest;
import com.fpt.edu.lifeform.dto.response.LoginResponse;
import com.fpt.edu.lifeform.dto.response.UserResponse;
import com.fpt.edu.lifeform.entity.UserEntity;
import com.fpt.edu.lifeform.repository.UserRepo;
import com.fpt.edu.lifeform.service.auth.JwtService;
import com.fpt.edu.lifeform.utils.BuildResponse;
import com.fpt.edu.lifeform.utils.SecurityUtil;
import com.fpt.edu.lifeform.utils.enums.AccountTypeEnum;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class AuthServiceHelper {

    private final AuthenticationManager authenticationManager;
    private final ModelMapper modelMapper;
    private final JwtService jwtService;
    private final UserRepo userRepo;
    private final SecurityUtil securityUtil;

    public AuthServiceHelper(AuthenticationManager authenticationManager, ModelMapper modelMapper, JwtService jwtService, UserRepo userRepo, SecurityUtil securityUtil) {
        this.authenticationManager = authenticationManager;
        this.modelMapper = modelMapper;
        this.jwtService = jwtService;
        this.userRepo = userRepo;
        this.securityUtil = securityUtil;
    }

    public String authenticatedCredentialsLogin(CredentialsLoginRequest loginRequest) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        return authentication.getName();
    }

    public LoginResponse createLoginResponse(UserEntity userEntity, String email) {
        UserResponse userResponse = modelMapper.map(userEntity, UserResponse.class);
        userResponse.setRole(userEntity.getRole().getName());

        String accessToken = jwtService.createJWTToken(email, AccountTypeEnum.CREDENTIAL.name(), securityUtil.accessTokenExpiration);
        String refreshToken = jwtService.createJWTToken(email, AccountTypeEnum.CREDENTIAL.name(), securityUtil.refreshTokenExpiration);

        userEntity.setRefreshToken(refreshToken);
        UserEntity newAccount = userRepo.save(userEntity);
        UserResponse newUserResponse = modelMapper.map(newAccount, UserResponse.class);
        newUserResponse.setRole(userEntity.getRole().getName());

        Instant now = Instant.now();
        Instant expireAt = now.plusSeconds(securityUtil.accessTokenExpiration);
        return BuildResponse.buildLoginResponse(newUserResponse, accessToken, expireAt, refreshToken);
    }
}
