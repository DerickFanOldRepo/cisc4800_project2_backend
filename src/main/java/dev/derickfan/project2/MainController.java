package dev.derickfan.project2;

import java.util.List;

import dev.derickfan.project2.model.Category;
import dev.derickfan.project2.model.Item;
import dev.derickfan.project2.model.User;
import dev.derickfan.project2.repository.CategoryRepository;
import dev.derickfan.project2.repository.ItemRepository;
import dev.derickfan.project2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ItemRepository itemRepository;

    @GetMapping(path = "/test")
    public @ResponseBody String test() {
        return "Hello world!";
    }

    /*----------------------------------
        User endpoints
    ----------------------------------*/

    // GET REQUEST
    // RETURNS A LIST OF ALL USER
    @GetMapping(path = "/getAllUsers")
    public @ResponseBody List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

    // GET REQUEST
    // PARAM: NAME
    // RETURNS A USER WITH THE GIVEN NAME 
    @GetMapping(path = "/getUser")
    public @ResponseBody User getUser(@RequestParam String username) {
        return userRepository.getUser(username);
    }

    // POST REQUEST
    // PARAM: NAME, EMAIL, PASSWORD
    // ADDS A USER TO THE DATABASE
    @PostMapping(path = "/addUser")
    public @ResponseBody int addUser(@RequestParam String username, @RequestParam String email, @RequestParam String password) {
        return userRepository.addUser(username, email, password);
    }
    
    // POST REQUEST
    // PARAM: NAME, OLDPASSWORD, NEWPASSWORD
    // UPDATES THE USER'S PASSWORD AFTER CONFIRMING OLD PASSWORD
    @PostMapping(path = "/changePassword")
    public @ResponseBody int changePassword(@RequestParam String username, @RequestParam String oldPassword, @RequestParam String newPassword) {
        return userRepository.updatePassword(username,oldPassword, newPassword);
    }
    
    // POST REQUEST
    // PARAM: NAME, OLDEMAIL, NEWEMAIL
    // UPDATES THE USER'S EMAIL AFTER CONFIRMING OLD EMAIL
    @PostMapping(path = "/changeEmail")
    public @ResponseBody int changeEmail(@RequestParam String username, @RequestParam String oldEmail, @RequestParam String newEmail) {
        return userRepository.updateEmail(username, oldEmail, newEmail);
    }
    
    // DELETE REQUEST
    // PARAM: NAME
    // DELETES THE USER'S ENTRY IN THE TABLE
    @DeleteMapping(path = "/deleteUser")
    public @ResponseBody int deleteUser(@RequestParam String username) {
        return userRepository.deleteByUsername(username);
    }

    /*----------------------------------
        Category endpoints
    ----------------------------------*/

    // GET REQUEST
    // RETURNS A LIST OF ALL THE CATEGORIES
    @GetMapping(path = "/getAllCategories")
    public @ResponseBody List<Category> getAllCategories() {
        return categoryRepository.getAllCategories();
    }

    // POST REQUEST
    // PARAM: NAME
    // INSERTS A NEW CATEGORY INTO THE DATABASE
    @PostMapping(path = "/addCategory")
    public @ResponseBody int addCategory(@RequestParam String name) {
        return categoryRepository.addCategory(name);
    }

    // DELETE REQUEST
    // PARAM: NAME
    // DELETES A CATEGORY ENTRY BY THE NAME
    @DeleteMapping(path = "/deleteCategory")
    public @ResponseBody int deleteCategory(@RequestParam String name) {
        return categoryRepository.deleteCategory(name);
    }

    /*----------------------------------
        Item endpoints
    ----------------------------------*/

    // GET REQUEST
    // RETURNS A LIST OF ALL THE ITEMS
    @GetMapping(path = "/getAllItems")
    public @ResponseBody List<Item> getAllItems() {
        return itemRepository.getAllItems();
    }

}