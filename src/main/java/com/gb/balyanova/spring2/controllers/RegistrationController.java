package com.gb.balyanova.spring2.controllers;

import com.gb.balyanova.spring2.converter.ProductConverter;
import com.gb.balyanova.spring2.dto.JwtRequest;
import com.gb.balyanova.spring2.dto.JwtResponse;
import com.gb.balyanova.spring2.entities.User;
import com.gb.balyanova.spring2.exceptions.UserIsAlreadyExistsException;
import com.gb.balyanova.spring2.services.UserService;
import com.gb.balyanova.spring2.utils.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RegistrationController {
    private final JwtTokenUtil jwtTokenUtil;
    private final ProductConverter converter;
    private final UserService userService;

    @GetMapping
    public String registration(Model model) {
        model.addAttribute("userForm", new User());
        return "registration";
    }

    @PostMapping("/registration")
    public ResponseEntity<?> addUser(@RequestBody JwtRequest userRequest) throws UserIsAlreadyExistsException {
        UserDetails userDetails = userService.saveUser(converter.jwtRequestToUser(userRequest));
        String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
        }
    }