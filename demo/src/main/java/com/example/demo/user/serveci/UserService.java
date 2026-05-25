package com.example.demo.user.serveci;

import org.springframework.stereotype.Service;
import com.example.demo.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.demo.user.repository.UserRepository;


import com.example.demo.user.entity.User;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public User add(User user) {
        // Lógica para adicionar um usuário
        return userRepository.save(user);
    }

    public java.util.List<User> getAll() {
        // Lógica para obter todos os usuários
        return userRepository.findAll();
    }

    public User getById(Long id) {
        // Lógica para obter um usuário por ID
        return userRepository.findById(id).orElse(null);
    }

    public User update(Long id, User user) {
        // Lógica para atualizar um usuário
        User existingUser = userRepository.findById(id).orElse(null);
        if (existingUser != null) {
            existingUser.setEmail(user.getEmail());
            existingUser.setNome(user.getNome());
            existingUser.setPassword(user.getPassword());
            return userRepository.save(existingUser);
        }
        return null;
    }


    public void delete(Long id) {
        // Lógica para deletar um usuário
        userRepository.deleteById(id);
    }

    


}




