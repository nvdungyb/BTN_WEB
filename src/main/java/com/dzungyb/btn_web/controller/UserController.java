package com.dzungyb.btn_web.controller;

import com.dzungyb.btn_web.model.User;
import com.dzungyb.btn_web.request.UserRequest;
import com.dzungyb.btn_web.service.UserService;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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

//    @GetMapping("/")
//    public String index() {
//        return "Dashboard admin";
//    }

    @GetMapping("/login")
    public String login(@PathParam("email") String email, @PathParam("password") String password, Model model) {
        User user = userService.getuserByEmail(email);
        System.out.println(user);
        if (user.getPassword().equals(password)) {
            model.addAttribute("user", user);
            return "home";
        }

        return "index";
    }

    @PostMapping("/register")
    public @ResponseBody User register(@RequestBody UserRequest request) {
        User user = new User();
        user.setUserName(request.getUserName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());

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
    public @ResponseBody String deleteUser(@PathVariable("id") String id) {
        userService.deleteUser(id);
        return "User deleted";
    }

    @PostMapping("/add-user")
    public String addUser(@RequestParam String username, @RequestParam String password, @RequestParam String email, @RequestParam String firstName, @RequestParam String lastName){
        List<User> userList = userService.getUsers();
        boolean isUserExist = userList.stream().anyMatch(user -> username.equals(user.getUserName()));
        if(isUserExist){
            System.out.println("User " + username +" is existed");
        }
        else{
            User user = new User();
            user.setUserName(username);
            user.setPassword(password);
            user.setEmail(email);
            user.setLastName(lastName);
            user.setFirstName(firstName);
            userService.saveUser(user);
            System.out.println("Added new User to the Database");
        }
//        String id = @GeneratedValue(strategy = GenerationType.UUID)
        return "index";
    }

    @RequestMapping("/analyst")
    public String openAnalyst(){
        return "analyst";
    }
}
