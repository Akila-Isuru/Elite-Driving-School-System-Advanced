package org.example.elitedrivinglearnersadvancedapp.bo.Custom;


import java.util.List;

public interface UserBo {
    UserDto login(UserDto userDto) throws InvalidCredentialsException;
    void registerUser(UserDto userDto);
    void updateUser(UserDto userDto);
    void changePassword(String username, String newPassword, String role);
    void deleteUser(String username);
    List<UserDto> getAllUsers();
}