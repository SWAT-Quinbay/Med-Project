package com.example.adminService.services;

import com.example.adminService.customExceptions.AdminCannotDeactivateException;
import com.example.adminService.customExceptions.InvalidDataProvidedException;
import com.example.adminService.customExceptions.UserAlreadyExistsException;
import com.example.adminService.customExceptions.UserNotFoundException;
import com.example.adminService.dto.requests.UserRequest;
import com.example.adminService.dto.requests.UserUpdateRequest;
import com.example.adminService.dto.responses.UserResponse;
import com.example.adminService.kafka.models.AppUser;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService {

    Page<UserResponse> getAllUser(int page, int size) throws InvalidDataProvidedException;
    UserResponse getUserById(int id) throws UsernameNotFoundException,InvalidDataProvidedException, UserNotFoundException;
    UserResponse getUserByUsername(String username) throws UsernameNotFoundException,InvalidDataProvidedException;

    AppUser getUserObjectById(int id) throws UsernameNotFoundException,InvalidDataProvidedException, UserNotFoundException;
    AppUser getAdminObject() throws UserNotFoundException;
    AppUser getUserByUsernameObject(String username) throws UsernameNotFoundException;

    Page<UserResponse> searchUser(String query,int page,int size) throws InvalidDataProvidedException;
    UserResponse saveUser(UserRequest user) throws UserAlreadyExistsException, InvalidDataProvidedException;

    UserResponse updateUserName(UserUpdateRequest userUpdateRequest) throws InvalidDataProvidedException, UserNotFoundException;
    UserResponse updateUserPassword(UserUpdateRequest userUpdateRequest) throws InvalidDataProvidedException, UserNotFoundException;
    UserResponse disableOrEnableUser(int userId) throws InvalidDataProvidedException, UserNotFoundException, AdminCannotDeactivateException;
}
