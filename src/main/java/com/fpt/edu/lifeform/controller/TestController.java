package com.fpt.edu.lifeform.controller;

import com.fpt.edu.lifeform.Service.TestService;
import com.fpt.edu.lifeform.entity.UserEntity;
import com.fpt.edu.lifeform.utils.annotation.ApiMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api/v1/test")
@RequiredArgsConstructor
public class TestController {

    private final TestService testService;


    @ApiMessage("Lấy thành công!")
    @GetMapping
    public ResponseEntity<List<UserEntity>> getUsers() {
    return  ResponseEntity.ok(testService.getUser());
}


}
