package com.fshop.fashionshop.controller;

import com.fshop.fashionshop.model.User;
import com.fshop.fashionshop.model.dto.responseDto.ResponseDto;
import com.fshop.fashionshop.repository.UserRepository;
import com.fshop.fashionshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
//    UserRepository userRepository;
    UserService userService;

    @GetMapping
    List<User> getAll(){

        return userService.getAll();
    }

    @GetMapping("user-id")
    ResponseEntity<ResponseDto> isUserExists(@RequestHeader String userId) {
        ResponseDto responseDto = new ResponseDto("user exists");
        responseDto.addInfo("exists", String.valueOf(userService.isExists(userId)));
        return ResponseEntity.ok(responseDto);
    }
}
