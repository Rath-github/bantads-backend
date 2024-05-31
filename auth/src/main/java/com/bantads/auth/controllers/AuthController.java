package com.bantads.auth.controllers;

import com.bantads.auth.dtos.DadosAuthDto;
import com.bantads.auth.services.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "login")
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping
    public ResponseEntity<Object> efetuarLogin(@RequestBody @Valid DadosAuthDto dados) {
        try {
            Object token = authService.efetuarLogin(dados);
            return ResponseEntity.status(HttpStatus.OK).body(token);
        }catch (BadCredentialsException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
