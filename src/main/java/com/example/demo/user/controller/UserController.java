package com.example.demo.user.controller;

import com.example.demo.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public String user(@AuthenticationPrincipal OAuth2User principal) {
        return principal.getAttribute("name");
    }

//    @PostMapping("/login")
//    public ResponseEntity<JwtAuthenticationToken> loginSuccess(@RequestBody Map<String, String> loginForm) {
//        JwtAuthenticationToken token = userService.login(loginForm.get("username"). loginForm.get("password"));
//        return ResponseEntity.ok(token);
//    }

}