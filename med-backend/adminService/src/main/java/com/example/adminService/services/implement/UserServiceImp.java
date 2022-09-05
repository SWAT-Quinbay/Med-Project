package com.example.adminService.services.implement;


import com.example.adminService.customExceptions.AdminCannotDeactivateException;
import com.example.adminService.customExceptions.InvalidDataProvidedException;
import com.example.adminService.customExceptions.UserAlreadyExistsException;
import com.example.adminService.customExceptions.UserNotFoundException;
import com.example.adminService.dto.requests.UserRequest;
import com.example.adminService.dto.requests.UserUpdateRequest;
import com.example.adminService.dto.responses.UserResponse;
import com.example.adminService.kafka.models.AppUser;
import com.example.adminService.repos.UserRepository;
import com.example.adminService.services.UserService;
import com.example.adminService.utils.Constants;
import com.example.adminService.utils.Utilities;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Locale;
import java.util.Optional;

@Service
public class UserServiceImp implements UserService {

    private final String[] roles = {Constants.ADMIN_ROLE, Constants.DEALER_ROLE, Constants.RETAILER_ROLE};
    @Autowired
    UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @Override
    public Page<UserResponse> getAllUser(int page, int size) throws InvalidDataProvidedException {
        if (!(Utilities.validPage(page) && Utilities.validNumber(size))) {
            throw new InvalidDataProvidedException("Numbers Should be positive digits");
        }

        PageRequest pr = PageRequest.of(page, size, Sort.by("id").descending());
        return mapAllUserResponse(userRepository.findByRoleNot(Constants.ADMIN_ROLE, pr));
    }

    @Override
    public AppUser getUserObjectById(int id) throws InvalidDataProvidedException, UserNotFoundException {

        if (!Utilities.validNumber(id)) {
            throw new InvalidDataProvidedException("Numbers Should be positive digits");
        }

        Optional<AppUser> result = userRepository.findById(id);
        if (!result.isPresent()) {
            throw new UserNotFoundException("user not found");
        }
        return result.get();
    }

    @Override
    public AppUser getAdminObject() throws UserNotFoundException {
        Optional<AppUser> result = userRepository.findByRole(Constants.ADMIN_ROLE);
        if (!result.isPresent()) {
            throw new UserNotFoundException("User Not found !");
        }
        return result.get();
    }

    @Override
    public AppUser getUserByUsernameObject(String username) throws UsernameNotFoundException {
        username = Utilities.formatString(username);

        Optional<AppUser> result = userRepository.findByUsername(username);
        if (!result.isPresent()) {
            throw new UsernameNotFoundException("Username not found");
        }
        return result.get();

    }

    @Override
    public UserResponse getUserById(int id) throws InvalidDataProvidedException, UserNotFoundException {
        AppUser user = getUserObjectById(id);
        return mapUserResponse(user);
    }

    @Override
    public UserResponse getUserByUsername(String username) throws InvalidDataProvidedException, UsernameNotFoundException {
        username = Utilities.formatString(username);
        if (!Utilities.validString(username)) {
            throw new InvalidDataProvidedException("Provide Valid Username");
        }

        AppUser user = getUserByUsernameObject(username);
        return mapUserResponse(user);
    }

    @Override
    public Page<UserResponse> searchUser(String query, int page, int size) throws InvalidDataProvidedException {
        query = Utilities.formatString(query);

        if (!(Utilities.validPage(page) && Utilities.validNumber(size))) {
            throw new InvalidDataProvidedException("Numbers Should be positive digits");
        }


        query = query.toLowerCase(Locale.ROOT);

        PageRequest pr = PageRequest.of(page, size, Sort.by("name").ascending());
        Page<AppUser> result = userRepository.findAllByNameContainingIgnoreCase(query, pr);
        return mapAllUserResponse(result);
    }

    @Override
    public UserResponse saveUser(UserRequest request) throws UserAlreadyExistsException, InvalidDataProvidedException {

//      Format data
        request.setName(Utilities.formatString(request.getName()));
        request.setUsername(Utilities.formatString(request.getUsername()));
        request.setRole(Utilities.formatString(request.getRole()));

        if (!(Utilities.validString(request.getName())
                && Utilities.validString(request.getUsername())
                && Utilities.validString(request.getRole())
                && Utilities.validString(request.getPassword()))) {
            throw new InvalidDataProvidedException("Text Should contains 4 characters");
        }

        AppUser user = mapUserFromRequest(request);

        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new UserAlreadyExistsException("User Already exist");
        }

        if (!Arrays.stream(roles).anyMatch(role -> role.equals(user.getRole()))) {
            throw new InvalidDataProvidedException("Invalid Role Provided");
        }

        // check only one admin creating
        if (request.getRole().equals(Constants.ADMIN_ROLE)) {
            Optional<AppUser> result = userRepository.findByRole(request.getRole());
            if (!result.isPresent()) {
//                create it
                user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
                return mapUserResponse(userRepository.save(user));
            }
            throw new UserAlreadyExistsException("Admin already exists");
        }

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return mapUserResponse(userRepository.save(user));
    }

    @Override
    public UserResponse updateUserName(UserUpdateRequest userRequest) throws InvalidDataProvidedException, UserNotFoundException {

        AppUser user = getUserObjectById(userRequest.getId());

        userRequest.setName(Utilities.formatString(userRequest.getName()));

        if (!Utilities.validString(userRequest.getName())) {
            throw new InvalidDataProvidedException("Invalid Name");
        }
        user.setName(userRequest.getName());
        userRepository.save(user);
        return mapUserResponse(user);
    }

    @Override
    public UserResponse updateUserPassword(UserUpdateRequest userRequest) throws InvalidDataProvidedException, UserNotFoundException {

        AppUser user = getUserObjectById(userRequest.getId());

        if (!Utilities.validString(userRequest.getPassword())) {
            throw new InvalidDataProvidedException("Invalid Password");
        }

        user.setPassword(bCryptPasswordEncoder.encode(userRequest.getPassword()));
        userRepository.save(user);
        return mapUserResponse(user);
    }

    @Override
    public UserResponse disableOrEnableUser(int userId) throws InvalidDataProvidedException, UserNotFoundException, AdminCannotDeactivateException {

        AppUser user = getUserObjectById(userId);

        if (user.getRole().equals(Constants.ADMIN_ROLE))
            throw new AdminCannotDeactivateException("Admin Cannot be deactivate");

        user.setActive(!user.isActive());
        userRepository.save(user);

        return mapUserResponse(user);
    }


    private AppUser mapUserFromRequest(UserRequest user) {
        return modelMapper.map(user, AppUser.class);
    }

    private UserResponse mapUserResponse(AppUser user) {
        return modelMapper.map(user, UserResponse.class);
    }

    private Page<UserResponse> mapAllUserResponse(Page<AppUser> users) {
        return users.map(this::mapUserResponse);
    }
}
