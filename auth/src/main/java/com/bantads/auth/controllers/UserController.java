package com.bantads.auth.controllers;

import com.bantads.auth.dtos.DadosAuthDto;
import com.bantads.auth.exeptions.RoleNaoPermitidaException;
import com.bantads.auth.exeptions.UsuarioJaExisteException;
import com.bantads.auth.models.User;
import com.bantads.auth.services.UserService;
import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(value = "/user")
@RequiredArgsConstructor
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping
    @PermitAll
    public ResponseEntity<Object> create(@RequestBody DadosAuthDto user){
        try {
            Object usuarioCriado  = userService.create(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioCriado);
        }catch (UsuarioJaExisteException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }catch (RoleNaoPermitidaException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    //o listarUsuarios so esta aqui para teste... deve ser removido!
    @GetMapping
    @PreAuthorize("hasRole('ROLE_Client')")
    public ResponseEntity<List<User>> listarUsuarios(){
        try {
            List<User> usuarios = userService.listar();
            return ResponseEntity.status(HttpStatus.OK).body(usuarios);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
