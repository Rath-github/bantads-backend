package com.bantads.auth.services;

import com.bantads.auth.dtos.DadosAuthDto;
import com.bantads.auth.dtos.UserResponseDto;
import com.bantads.auth.exeptions.RoleNaoPermitidaException;
import com.bantads.auth.exeptions.UsuarioJaExisteException;
import com.bantads.auth.models.User;
import com.bantads.auth.repositories.UserRepository;
import com.bantads.auth.roles.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserResponseDto create(DadosAuthDto user) throws UsuarioJaExisteException, RoleNaoPermitidaException {
        User existUser = userRepository.findByUsername(user.username());
        if(existUser != null){
            throw new UsuarioJaExisteException("Usuario ja existe!");
        }

        User novoUsuario = new User();
        switch (user.role()){
            case 1:
                novoUsuario.setUserRole(Roles.CLIENT);
                break;
            case 2:
                novoUsuario.setUserRole(Roles.GERENTE);
                break;
            case 3:
                novoUsuario.setUserRole(Roles.ADMIN);
                break;
            default:
                throw new RoleNaoPermitidaException("Role nao permitida!");

        }
        novoUsuario.setUsername(user.username());
        novoUsuario.setPassword(passwordEncoder.encode(user.password()));
        User createdUser = userRepository.save(novoUsuario);
        return new UserResponseDto(createdUser.getId(), createdUser.getUsername(), "ROLE_" + createdUser.getUserRole().getRole());
    }

    public List<User> listar(){
        return userRepository.findAll();
    }
}
