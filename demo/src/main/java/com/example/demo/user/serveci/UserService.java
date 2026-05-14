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

}




