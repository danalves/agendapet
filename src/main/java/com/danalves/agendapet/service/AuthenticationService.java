package com.danalves.agendapet.service;

import com.danalves.agendapet.dto.AuthenticationResponse;
import com.danalves.agendapet.dto.LoginRequest;
import com.danalves.agendapet.dto.RegisterRequest;
import com.danalves.agendapet.model.User;
import com.danalves.agendapet.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private PasswordEncoder encoder;

    public AuthenticationResponse register(RegisterRequest request) {
        var encodedPassword = encoder.encode(request.password());
        var user = new User(request, encodedPassword);

        userRepository.save(user);
        var jwtToken = tokenService.generateToken(user);

        return new AuthenticationResponse(jwtToken);
    }

    public AuthenticationResponse login(LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.username(),
                        loginRequest.password()
                )
        );
        var user = userRepository.findByUsername(loginRequest.username()).orElseThrow();
        var jwtToken = tokenService.generateToken(user);

        return new AuthenticationResponse(jwtToken);
    }
}
