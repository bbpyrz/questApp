package com.project.questApp.controllers;

import com.project.questApp.entities.User;
import com.project.questApp.request.UserRequest;
import com.project.questApp.security.JwtTokenProvider;
import com.project.questApp.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private AuthenticationManager authenticationManager;
    private JwtTokenProvider jwtTokenProvider;
    private UserService userService;
    private PasswordEncoder passwordEncoder;

    public AuthController(AuthenticationManager authenticationManager,JwtTokenProvider jwtTokenProvider,UserService userService,PasswordEncoder passwordEncoder){
        this.authenticationManager=authenticationManager;
        this.jwtTokenProvider=jwtTokenProvider;
        this.userService=userService;
        this.passwordEncoder=passwordEncoder;
    }
    @PostMapping("/login")
    public String login(@RequestBody UserRequest loginRequest){

        UsernamePasswordAuthenticationToken authToken= new UsernamePasswordAuthenticationToken(loginRequest.getUserName(),loginRequest.getPassword());
        Authentication auth=authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(auth);
        String jwtToken=jwtTokenProvider.generateJwtToken(auth);

        return "Bearer "+jwtToken;
    }
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserRequest registerRequest){
        if(userService.getOneUserByUserName(registerRequest.getUserName())!=null)
            return new ResponseEntity<>("User Name already exist ", HttpStatus.BAD_REQUEST);
        User user=new User();
        user.setUserName(registerRequest.getUserName());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        userService.saveOneUser(user);

        return new ResponseEntity<>("User registered ", HttpStatus.CREATED);
    }

}
