package com.fpt.edu.lifeform.service.auth;

import com.fpt.edu.lifeform.dto.request.CredentialsLoginRequest;
import com.fpt.edu.lifeform.dto.response.LoginResponse;
import com.fpt.edu.lifeform.entity.UserEntity;
import com.fpt.edu.lifeform.exception.custom.AccountException;
import com.fpt.edu.lifeform.exception.custom.InvalidTokenException;
import com.fpt.edu.lifeform.helper.AuthServiceHelper;
import com.fpt.edu.lifeform.repository.UserRepo;
import com.fpt.edu.lifeform.utils.SecurityUtil;
import com.fpt.edu.lifeform.utils.enums.AccountTypeEnum;
import com.fpt.edu.lifeform.utils.enums.RoleEnum;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Service;


@Service
public class AuthService {


    private final UserRepo userRepo;
    private final SecurityUtil securityUtil;
    private final AuthServiceHelper authServiceHelper;

    public AuthService(UserRepo userRepo, SecurityUtil securityUtil, AuthServiceHelper authServiceHelper) {
        this.userRepo = userRepo;
        this.securityUtil = securityUtil;
        this.authServiceHelper = authServiceHelper;
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
