package com.bantads.auth.services;

import com.bantads.auth.dtos.DadosAuthDto;
import com.bantads.auth.dtos.DadosTokenJwtDto;
import com.bantads.auth.models.User;
import com.bantads.auth.repositories.UserRepository;
import com.bantads.auth.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class AuthService implements UserDetailsService {
    @Autowired
    private ApplicationContext context;
    @Autowired
    private UserRepository userRepository;
    private AuthenticationManager manager;
    @Autowired
    private TokenService tokenService;

    public DadosTokenJwtDto efetuarLogin(@RequestBody @Valid DadosAuthDto dados) {
        manager = context.getBean(AuthenticationManager.class);
        var authenticationToken = new UsernamePasswordAuthenticationToken(dados.username(), dados.password());
        var authentication = this.manager.authenticate(authenticationToken);
        var tokenJWT = tokenService.gerarToken((User) authentication.getPrincipal());

        System.out.println("Token gerado: " + tokenJWT);
        return new DadosTokenJwtDto(tokenJWT);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }
}
