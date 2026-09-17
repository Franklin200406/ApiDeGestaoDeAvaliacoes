package br.edu.gestaoavaliacoes.security;

import org.springframework.security.core.context.SecurityContextHolder;

public final class SecurityUtils {

    private SecurityUtils() {
    }

    public static SecurityUser currentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof SecurityUser securityUser) {
            return securityUser;
        }
        throw new IllegalStateException("Usuário autenticado inválido");
    }
}
