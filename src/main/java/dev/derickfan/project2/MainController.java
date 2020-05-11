package dev.derickfan.project2;

import java.util.List;

import com.fasterxml.jackson.databind.exc.InvalidDefinitionException;
import dev.derickfan.project2.model.*;
import dev.derickfan.project2.repository.*;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping
public class MainController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    ItemImageRepository itemImageRepository;

    @Autowired
    ListingRepository listingRepository;

    @GetMapping(path = "/")
    public @ResponseBody String test() {
        return "Hello world!";
    }

    /*----------------------------------
        User endpoints
    ----------------------------------*/

    // GET REQUEST
    // RETURNS A LIST OF ALL USER
    @GetMapping(path = "/getAllUsers")
    public @ResponseBody JSONObject getAllUsers() {
        JSONObject json = new JSONObject();
        json.put("data", userRepository.getAllUsers());
        return json;
    }

    // GET REQUEST
    // PARAM: NAME
    // RETURNS A USER WITH THE GIVEN NAME 
    @GetMapping(path = "/getUser")
    public @ResponseBody JSONObject getUser(@RequestParam String username) {
        JSONObject json = new JSONObject();
        json.put("data", userRepository.getUserByUsername(username));
        return json;
    }

    @GetMapping(path = "/login")
    public @ResponseBody JSONObject login(@RequestParam String username, @RequestParam String password) {
        JSONObject json = new JSONObject();
        try {
            User user = userRepository.authenticate(username, password);
            json.put("data", user);
        } catch (Exception e) {
            json.put("error", "LOGIN ERROR");
        }
        return json;
    }

    @PostMapping(path = "/signup")
    public @ResponseBody JSONObject login(@RequestParam String username, @RequestParam String email, @RequestParam String password) {
        JSONObject json = new JSONObject();
        try {
            User user = userRepository.signup(username, email, password);
            json.put("data", user);
        } catch (Exception e) {
            json.put("error", "SIGN UP ERROR");
        }
        return json;
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
    public @ResponseBody JSONObject getAllCategories() {
        JSONObject json = new JSONObject();
        json.put("data", categoryRepository.getAllCategories());
        return json;
    }

    // GET REQUEST
    // RETURNS THE REQUESTED CATEGORY
    @GetMapping(path = "/getCategory")
    public @ResponseBody Category getCategory(String name) {
        return categoryRepository.getCategoryByName(name);
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
    public @ResponseBody JSONObject getAllItems() {
        JSONObject json = new JSONObject();
        json.put("data", itemRepository.getAllItems());
        return json;
    }

    // POST REQUEST
    // PARAM: NAME, CATEGORYNAME
    // ADDS AN ITEM TO THE TABLE
    @PostMapping(value="/addItem")
    public @ResponseBody JSONObject addItem(@RequestParam String name, @RequestParam String categoryName, @RequestParam String itemImageUrl) {
        ItemImage itemImage;
        try {
            itemImage = itemImageRepository.getItemImageByUrl(itemImageUrl);
        } catch (EmptyResultDataAccessException e) {
            itemImageRepository.addItemImage(itemImageUrl);
            itemImage = itemImageRepository.getItemImageByUrl(itemImageUrl);
        }
        Category category = categoryRepository.getCategoryByName(categoryName);

        JSONObject json = new JSONObject();
        try {
            int data = itemRepository.addItem(name, category.getId(), itemImage.getId());
            json.put("data", data);
        } catch (Exception e) {
            json.put("error", "ADDING ITEM ERROR");
        }
        return json;
    }

    // DELETE REQUEST
    // PARAM: NAME
    // DELETES ITEM IN THE TABLE
    @DeleteMapping(path = "/deleteItem")
    public @ResponseBody int deleteItem(@RequestParam String name) {
        return itemRepository.deleteItem(name);
    }
    
    // POST REQUEST
    // PARAM: NAME, NEWCATEGORYNAME
    // UPDATES ITEM'S CATEGORY
    @PostMapping(path = "/updateItemCategory")
    public @ResponseBody int updateItemCategory(@RequestParam String name, @RequestParam String newCategoryName) {
        Category category = categoryRepository.getCategoryByName(newCategoryName);
        return itemRepository.updateCategory(name, category.getId());
    }

    /*----------------------------------
        ItemImage endpoints
    ----------------------------------*/

    @GetMapping(path = "/getAllItemImages")
    public @ResponseBody JSONObject getAllItemImages() {
        JSONObject json = new JSONObject();
        json.put("data", itemImageRepository.getAllItemImages());
        return json;
    }

    @DeleteMapping(path = "/deleteItemImageById")
    public @ResponseBody int deleteItemImage(@RequestParam int itemImageId) {
        return itemImageRepository.deleteItemImageById(itemImageId);
    }

    /*----------------------------------
        Listing endpoints
    ----------------------------------*/

    @PostMapping(path = "/addListing")
    public @ResponseBody JSONObject addListing(@RequestParam String username, @RequestParam String itemName, @RequestParam double price, @RequestParam int quantity) {
        JSONObject json = new JSONObject();
        try {
            User user = userRepository.getUserByUsername(username);
            Item item = itemRepository.getItemByName(itemName);
            json.put("data", listingRepository.addListing(user.getId(), item.getId(), price, quantity));

        } catch (Exception e) {
            json.put("error", "ADD LISTING ERROR");
        }
        return json;
    }

    @GetMapping(path = "/getAllListings")
    public @ResponseBody JSONObject getAllListings() {
        JSONObject json = new JSONObject();
        json.put("data", listingRepository.getAllListings());
        return json;
    }

    @GetMapping(path = "/getAllListingsByUser")
    public @ResponseBody JSONObject getAllListingsByUser(String username) {
        JSONObject json = new JSONObject();
        json.put("data", listingRepository.getAllListingsByUsername(username));
        return json;
    }

    @GetMapping(path = "/getAllListingsByItem")
    public @ResponseBody JSONObject getAllListingsByItem(String itemName) {
        JSONObject json = new JSONObject();
        json.put("data", listingRepository.getAllListingByItemName(itemName));
        return json;
    }

    @PostMapping(path = "/updateListQuantity")
    public @ResponseBody int updateListQuantity(@RequestParam String username, @RequestParam String itemName, @RequestParam int quantity) {
        User user = userRepository.getUserByUsername(username);
        Item item = itemRepository.getItemByName(itemName);
        return listingRepository.updateListingQuantity(user.getId(), item.getId(), quantity);
    }

    @PostMapping(path = "/updateListPrice")
    public @ResponseBody int updateListPrice(@RequestParam String username, @RequestParam String itemName, @RequestParam double price) {
        User user = userRepository.getUserByUsername(username);
        Item item = itemRepository.getItemByName(itemName);
        return listingRepository.updateListingPrice(user.getId(), item.getId(), price);
    }

    @DeleteMapping(path = "/deleteListing")
    public @ResponseBody int deleteListing(@RequestParam String username, @RequestParam String itemName) {
        User user = userRepository.getUserByUsername(username);
        Item item = itemRepository.getItemByName(itemName);
        return listingRepository.deleteListing(user.getId(), item.getId());
    }

}