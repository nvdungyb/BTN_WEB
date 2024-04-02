package com.dzungyb.btn_web.controller;

import com.dzungyb.btn_web.entity.User;
import com.dzungyb.btn_web.request.UserRequest;
import com.dzungyb.btn_web.service.UserService;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    public String login(@PathParam("email") String email, @PathParam("password") String password, Model model) {

        return "index";
    }

    @PostMapping("/register")
    public @ResponseBody User register(@RequestBody UserRequest request) {
        User user = new User();
        user.setUserName(request.getUserName());
            user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());

        if (userService.checkExistedEmail(request.getEmail())) {
            return null;
        }
        userService.saveUser(user);

        return user;
    }

    @GetMapping("/getUsers")
    public @ResponseBody List<User> getUsers() {
        return userService.getUsers();
    }

    @DeleteMapping("/delete/{id}")
    public @ResponseBody String deleteUser(@PathVariable("id") int id) {
        userService.deleteUser(id);
        return "User deleted";
    }
}
