package com.example.demo.auth.config;

import com.example.demo.auth.entity.Role;
import com.example.demo.auth.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InitialDataLoader implements CommandLineRunner {

    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        // Criar roles padrão se não existirem
        if (roleRepository.findByNome("ROLE_ADMIN").isEmpty()) {
            roleRepository.save(new Role("ROLE_ADMIN", "Administrador com acesso total"));
        }

        if (roleRepository.findByNome("ROLE_USER").isEmpty()) {
            roleRepository.save(new Role("ROLE_USER", "Usuário comum com acesso limitado"));
        }

        if (roleRepository.findByNome("ROLE_GUEST").isEmpty()) {
            roleRepository.save(new Role("ROLE_GUEST", "Visitante com acesso mínimo"));
        }
    }
}
