package com.example.adminService.controller;


import com.example.adminService.customExceptions.AdminCannotDeactivateException;
import com.example.adminService.customExceptions.InvalidDataProvidedException;
import com.example.adminService.customExceptions.UserAlreadyExistsException;
import com.example.adminService.customExceptions.UserNotFoundException;
import com.example.adminService.dto.requests.UserRequest;
import com.example.adminService.dto.requests.UserUpdateRequest;
import com.example.adminService.dto.responses.UserResponse;
import com.example.adminService.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("")
    public Page<UserResponse> getAllUser(@RequestParam int page, @RequestParam int size){
        try {
            return userService.getAllUser(page,size);
        }  catch (InvalidDataProvidedException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
        }catch (Exception e){
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage());
        }
    }
    @GetMapping("/id/{id}")
    public UserResponse getUserByID(@PathVariable int id){
        try {
            return userService.getUserById(id);
        } catch (InvalidDataProvidedException | UserNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
        }catch (Exception e){
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage());
        }
    }
    @GetMapping("/username/{username}")
    public UserResponse getUserByName(@PathVariable String username){
        try {
            return userService.getUserByUsername(username);
        } catch (InvalidDataProvidedException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
        }catch (Exception e){
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage());
        }
    }

    @GetMapping("/search")
    public Page<UserResponse> searchUser(@RequestParam String query,@RequestParam int page,@RequestParam int size){
        try {
            return userService.searchUser(query,page,size);
        } catch (InvalidDataProvidedException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
        }catch (Exception e){
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage());
        }
    }

    @PostMapping(value = "/add")
    public UserResponse saveUser(@RequestBody UserRequest user) {
        try {
            return userService.saveUser(user);
        }catch (InvalidDataProvidedException | UserAlreadyExistsException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
        }catch (Exception e){
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage());
        }
    }

    @PutMapping(value = "/update/name")
    public UserResponse updateUser(@RequestBody UserUpdateRequest user) {
        try {
            return userService.updateUserName(user);
        }catch (InvalidDataProvidedException | UserNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
        }catch (Exception e){
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage());
        }
    }
    @PutMapping(value = "/update/password")
    public UserResponse updateUserPassword(@RequestBody UserUpdateRequest user) {
        try {
            return userService.updateUserPassword(user);

        }catch (InvalidDataProvidedException | UserNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
        }catch (Exception e){
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage());
        }
    }
    @PutMapping(value = "/update/active/{id}")
    public UserResponse blockUser(@PathVariable int id){
        try {
            return userService.disableOrEnableUser(id);
        }catch (InvalidDataProvidedException | UserNotFoundException | AdminCannotDeactivateException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
        }catch (Exception e){
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage());
        }
    }

    @GetMapping("/check/dealer")
    public boolean checkDealer(){
        return true;
    }
    @GetMapping("/check/retailer")
    public boolean checkRetailer(){
        return true;
    }
}
