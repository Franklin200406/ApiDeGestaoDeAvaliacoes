package br.edu.gestaoavaliacoes.service;

import br.edu.gestaoavaliacoes.model.User;
import br.edu.gestaoavaliacoes.presentation.dto.request.LoginRequest;
import br.edu.gestaoavaliacoes.presentation.dto.response.LoginResponse;
import br.edu.gestaoavaliacoes.repository.UserRepository;
import br.edu.gestaoavaliacoes.security.SecurityUser;
import br.edu.gestaoavaliacoes.security.TokenProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;

    public AuthService(AuthenticationManager authenticationManager,
                       UserRepository userRepository,
                       TokenProvider tokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.tokenProvider = tokenProvider;
    }

    public LoginResponse login(LoginRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        } catch (AuthenticationException ex) {
            throw new UsernameNotFoundException("Credenciais inválidas");
        }
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Credenciais inválidas"));
        String token = tokenProvider.generateToken(user);
        return new LoginResponse(token);
    }

    public User currentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof SecurityUser securityUser)) {
            return null;
        }
        return userRepository.findByEmail(securityUser.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
    }
}
