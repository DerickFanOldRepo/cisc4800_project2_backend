package dev.derickfan.project2;

import java.util.List;

import dev.derickfan.project2.model.*;
import dev.derickfan.project2.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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
    public @ResponseBody List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

    // GET REQUEST
    // PARAM: NAME
    // RETURNS A USER WITH THE GIVEN NAME 
    @GetMapping(path = "/getUser")
    public @ResponseBody User getUser(@RequestParam String username) {
        return userRepository.getUserByUsername(username);
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
    public @ResponseBody List<Item> getAllItems() {
        return itemRepository.getAllItems();
    }

    // POST REQUEST
    // PARAM: NAME, CATEGORYNAME
    // ADDS AN ITEM TO THE TABLE
    @PostMapping(value="/addItem")
    public @ResponseBody int addItem(@RequestParam String name, @RequestParam String categoryName, @RequestParam String itemImageUrl) {
        ItemImage itemImage;
        try {
            itemImage = itemImageRepository.getItemImageByUrl(itemImageUrl);
        } catch (EmptyResultDataAccessException e) {
            itemImageRepository.addItemImage(itemImageUrl);
            itemImage = itemImageRepository.getItemImageByUrl(itemImageUrl);
        }
        Category category = categoryRepository.getCategoryByName(categoryName);
        return itemRepository.addItem(name, category.getId(), itemImage.getId());
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
    public @ResponseBody List<ItemImage> getAllItemImages() { return itemImageRepository.getAllItemImages(); }

    @DeleteMapping(path = "/deleteItemImageById")
    public @ResponseBody int deleteItemImage(@RequestParam int itemImageId) {
        return itemImageRepository.deleteItemImageById(itemImageId);
    }

    /*----------------------------------
        Listing endpoints
    ----------------------------------*/

    @PostMapping(path = "/addListing")
    public @ResponseBody int addListing(@RequestParam String username, @RequestParam String itemName, @RequestParam double price, @RequestParam int quantity) {
        User user = userRepository.getUserByUsername(username);
        Item item = itemRepository.getItemByName(itemName);
        return listingRepository.addListing(user.getId(), item.getId(), price, quantity);
    }

    @GetMapping(path = "/getAllListings")
    public @ResponseBody List<Listing> getAllListings() { return listingRepository.getAllListings(); }

    @GetMapping(path = "/getAllListingsByUser")
    public @ResponseBody List<Listing> getAllListingsByUser(String username) {
        User user = userRepository.getUserByUsername(username);
        return listingRepository.getListingsByUserId(user.getId());
    }

    @GetMapping(path = "/getAllListingsByItem")
    public @ResponseBody List<Listing> getAllListingsByItem(String itemName) {
        Item item = itemRepository.getItemByName(itemName);
        return listingRepository.getListingsByItemId(item.getId());
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