package dev.derickfan.project2;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping
public class MainController {

    @Autowired
    UserRepository userRepository;

    @GetMapping(path = "/test")
    public @ResponseBody String test() {
        return "Hello world!";
    }

    @GetMapping(path = "/getAllUsers")
    public @ResponseBody List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping(path = "/getUser")
    public @ResponseBody User getUser(@RequestParam String name) {
        return userRepository.find(name);
    }

    @PostMapping(path = "/addUser")
    public @ResponseBody int addUser(@RequestParam String name, @RequestParam String email, @RequestParam String password) {
        return userRepository.save(name, email, password);
    }

    @DeleteMapping(path = "/deleteUser")
    public @ResponseBody int deleteUser(@RequestParam String name) {
        return userRepository.deleteByUsername(name);
    }

    @PostMapping(path = "/changePassword")
    public @ResponseBody int changePassword(@RequestParam String name, @RequestParam String oldPassword, @RequestParam String newPassword) {
        return userRepository.updatePassword(name, oldPassword, newPassword);
    }
    
    @PostMapping(path = "/changeEmail")
    public @ResponseBody int changeEmail(@RequestParam String name, @RequestParam String oldEmail, @RequestParam String newEmail) {
        return userRepository.updateEmail(name, oldEmail, newEmail);
    }

}