package com.example.adminService.controller;


import com.example.adminService.customExceptions.AdminCannotDeactivateException;
import com.example.adminService.customExceptions.InvalidDataProvidedException;
import com.example.adminService.customExceptions.UserAlreadyExistsException;
import com.example.adminService.customExceptions.UserNotFoundException;
import com.example.adminService.dto.requests.UserRequest;
import com.example.adminService.dto.requests.UserUpdateRequest;
import com.example.adminService.dto.responses.UserResponse;
import com.example.adminService.services.UserService;
import com.example.adminService.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequestMapping(Constants.USER_BASE_URL)
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping(Constants.USER_GET_ALL)
    public Page<UserResponse> getAllUser(@RequestParam int page, @RequestParam int size) {
        try {
            return userService.getAllUser(page, size);
        } catch (InvalidDataProvidedException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Constants.INVALID_DATA_PROVIDED);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, Constants.INTERNAL_ERROR_MESSAGE);
        }
    }

    @GetMapping(Constants.USER_GET_BY_ID)
    public UserResponse getUserByID(@PathVariable int id) {
        try {
            return userService.getUserById(id);
        } catch (InvalidDataProvidedException | UserNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Constants.INVALID_DATA_PROVIDED);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, Constants.INTERNAL_ERROR_MESSAGE);
        }
    }

    @GetMapping(Constants.USER_GET_BY_USERNAME)
    public UserResponse getUserByName(@PathVariable String username) {
        try {
            return userService.getUserByUsername(username);
        } catch (UsernameNotFoundException | InvalidDataProvidedException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Constants.INVALID_DATA_PROVIDED);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, Constants.INTERNAL_ERROR_MESSAGE);
        }
    }

    @GetMapping(Constants.USER_SEARCH)
    public Page<UserResponse> searchUser(@RequestParam String query, @RequestParam int page, @RequestParam int size) {
        try {
            return userService.searchUser(query, page, size);
        } catch (InvalidDataProvidedException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Constants.INVALID_DATA_PROVIDED);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, Constants.INTERNAL_ERROR_MESSAGE);
        }
    }

    @PostMapping(Constants.USER_ADD)
    public UserResponse saveUser(@RequestBody UserRequest user) {
        try {
            return userService.saveUser(user);
        } catch (InvalidDataProvidedException | UserAlreadyExistsException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Constants.INVALID_DATA_PROVIDED);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, Constants.INTERNAL_ERROR_MESSAGE);
        }
    }

    @PutMapping(Constants.USER_UPDATE_NAME)
    public UserResponse updateUser(@RequestBody UserUpdateRequest user) {
        try {
            return userService.updateUserName(user);
        } catch (InvalidDataProvidedException | UserNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Constants.INVALID_DATA_PROVIDED);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, Constants.INTERNAL_ERROR_MESSAGE);
        }
    }

    @PutMapping(Constants.USER_UPDATE_PASSWORD)
    public UserResponse updateUserPassword(@RequestBody UserUpdateRequest user) {
        try {
            return userService.updateUserPassword(user);

        } catch (InvalidDataProvidedException | UserNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Constants.INVALID_DATA_PROVIDED);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, Constants.INTERNAL_ERROR_MESSAGE);
        }
    }

    @PutMapping(Constants.USER_UPDATE_ACTIVE)
    public UserResponse blockUser(@PathVariable int id) {
        try {
            return userService.disableOrEnableUser(id);
        } catch (InvalidDataProvidedException | UserNotFoundException | AdminCannotDeactivateException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Constants.INVALID_DATA_PROVIDED);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, Constants.INTERNAL_ERROR_MESSAGE);
        }
    }
}
