package com.fpt.edu.lifeform.service.auth;

import com.fpt.edu.lifeform.dto.request.CredentialsLoginRequest;
import com.fpt.edu.lifeform.dto.request.SocialsLoginRequest;
import com.fpt.edu.lifeform.dto.response.LoginResponse;
import com.fpt.edu.lifeform.dto.response.UserResponse;
import com.fpt.edu.lifeform.entity.RoleEntity;
import com.fpt.edu.lifeform.entity.UserEntity;
import com.fpt.edu.lifeform.exception.custom.AccountException;
import com.fpt.edu.lifeform.exception.custom.InvalidTokenException;
import com.fpt.edu.lifeform.exception.custom.RoleException;
import com.fpt.edu.lifeform.helper.AuthServiceHelper;
import com.fpt.edu.lifeform.repository.RoleRepo;
import com.fpt.edu.lifeform.repository.UserRepo;
import com.fpt.edu.lifeform.utils.BuildResponse;
import com.fpt.edu.lifeform.utils.SecurityUtil;
import com.fpt.edu.lifeform.utils.enums.AccountTypeEnum;
import com.fpt.edu.lifeform.utils.enums.RoleEnum;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;


@Service
public class AuthService {


    private final UserRepo userRepo;
    private final SecurityUtil securityUtil;
    private final AuthServiceHelper authServiceHelper;
    private final JwtService jwtService;
    private final ModelMapper modelMapper;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepo userRepo, SecurityUtil securityUtil, AuthServiceHelper authServiceHelper, JwtService jwtService, ModelMapper modelMapper, RoleRepo roleRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.securityUtil = securityUtil;
        this.authServiceHelper = authServiceHelper;
        this.jwtService = jwtService;
        this.modelMapper = modelMapper;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public LoginResponse credentialsLogin(CredentialsLoginRequest loginRequest) {
        String email = authServiceHelper.authenticatedCredentialsLogin(loginRequest);
        UserEntity userEntity = userRepo.findByEmailAndAccountType(email, AccountTypeEnum.CREDENTIAL)
                .orElseThrow(() -> new AccountException("Tài khoản không tồn tại!"));
        if (Boolean.FALSE.equals(userEntity.getActive())) {
            throw new AccountException("Tài khoản không tồn tại!");
        }
        return authServiceHelper.createLoginResponse(userEntity, email);
    }

    public LoginResponse socialsLogin(SocialsLoginRequest socialsLoginRequest) {
        Optional<UserEntity> userOptional = userRepo.findByEmail(socialsLoginRequest.getEmail());
        Instant now = Instant.now();
        Instant expireAt = now.plusSeconds(securityUtil.accessTokenExpiration);
        UserEntity userEntity;

        if (userOptional.isPresent()) {
            userEntity = userOptional.get();
            userEntity.setAccountType(socialsLoginRequest.getAccountType());
            if (socialsLoginRequest.getFullname() != null) {
                userEntity.setFullname(socialsLoginRequest.getFullname());
            }
            if (socialsLoginRequest.getAvatar() != null) {
                userEntity.setAvatar(socialsLoginRequest.getAvatar());
            }
        } else {
            userEntity = modelMapper.map(socialsLoginRequest, UserEntity.class);
            RoleEntity roleEntity = roleRepo.findByName(RoleEnum.USER)
                    .orElseThrow(() -> new RoleException("Role not found!"));
            userEntity.setRole(roleEntity);
            String randomPassword = java.util.UUID.randomUUID().toString();
            userEntity.setPassword(passwordEncoder.encode(randomPassword));
        }
        String accessToken = jwtService.createJWTToken(
                userEntity.getEmail(),
                userEntity.getAccountType().name(),
                securityUtil.accessTokenExpiration
        );
        String refreshToken = jwtService.createJWTToken(
                userEntity.getEmail(),
                userEntity.getAccountType().name(),
                securityUtil.refreshTokenExpiration
        );
        userEntity.setRefreshToken(refreshToken);
        userEntity.setActive(true);
        UserEntity savedUser = userRepo.save(userEntity);
        UserResponse userResponse = modelMapper.map(savedUser, UserResponse.class);
        return BuildResponse.buildLoginResponse(userResponse, accessToken, expireAt, refreshToken);
    }

    public void logout(String refreshToken) {
        NimbusJwtDecoder jwtDecoder = NimbusJwtDecoder
                .withSecretKey(securityUtil.getSecretKey())
                .macAlgorithm(securityUtil.JWT_ALGORITHMS)
                .build();

        try {
            Jwt jwt = jwtDecoder.decode(refreshToken);
            String email = jwt.getSubject();
            String accountType = jwt.getClaim("accountType").toString();
            UserEntity userEntity = userRepo.findByEmailAndAccountType(email, AccountTypeEnum.valueOf(accountType))
                    .orElseThrow(() -> new AccountException("User not found!"));
            userEntity.setRefreshToken(null);
            userRepo.save(userEntity);
        } catch (Exception e) {
            throw new InvalidTokenException("Refresh token is invalid!");
        }
    }

}
