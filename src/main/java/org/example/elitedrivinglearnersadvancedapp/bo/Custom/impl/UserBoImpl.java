package org.example.elitedrivinglearnersadvancedapp.bo.Custom.impl;


import org.example.elitedrivinglearnersadvancedapp.bo.Custom.UserBo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserBoImpl implements UserBo {

    private final UserDao userDao = new UserDaoImpl();

    @Override
    public UserDto login(UserDto userDto) throws InvalidCredentialsException {
        Optional<User> userOptional = userDao.findByUsername(userDto.getUsername());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (BCrypt.checkpw(userDto.getPassword(), user.getPassword())) {
                return new UserDto(user.getUsername(), null, user.getRole());
            }
        }
        throw new InvalidCredentialsException("Invalid username or password.");
    }

    @Override
    public void registerUser(UserDto userDto) {
        String hashedPassword = BCrypt.hashpw(userDto.getPassword(), BCrypt.gensalt());
        User user = new User(userDto.getUsername(), hashedPassword, userDto.getRole());
        userDao.save(user);
    }

    @Override
    public void updateUser(UserDto userDto) {
        Optional<User> userOptional = userDao.findByUsername(userDto.getUsername());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setRole(userDto.getRole());
            userDao.update(user);
        }
    }

    @Override
    public void changePassword(String username, String newPassword, String role) {
        Optional<User> userOptional = userDao.findByUsername(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            String hashedNewPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());
            user.setPassword(hashedNewPassword);
            user.setRole(role);
            userDao.update(user);
        }
    }

    @Override
    public void deleteUser(String username) {
        Optional<User> userOptional = userDao.findByUsername(username);
        userOptional.ifPresent(userDao::delete);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> userList = userDao.findAllUsers();
        List<UserDto> dtoList = new ArrayList<>();
        for (User user : userList) {
            dtoList.add(new UserDto(user.getUsername(), user.getPassword(), user.getRole()));
        }
        return dtoList;
    }
}