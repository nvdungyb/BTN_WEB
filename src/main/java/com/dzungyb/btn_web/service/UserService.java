package com.dzungyb.btn_web.service;

import com.dzungyb.btn_web.entity.User;
import com.dzungyb.btn_web.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public User getUser(User user) {
        return userRepository.findById(user.getId()).get();
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public boolean checkExistedEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }

}
