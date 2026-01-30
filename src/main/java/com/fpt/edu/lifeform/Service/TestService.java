package com.fpt.edu.lifeform.Service;

import com.fpt.edu.lifeform.entity.UserEntity;
import com.fpt.edu.lifeform.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TestService {

    private final UserRepo userRepository;

    public List<UserEntity> getUser() {
    List<UserEntity> list = userRepository.findAll();
    return list;
}

}
