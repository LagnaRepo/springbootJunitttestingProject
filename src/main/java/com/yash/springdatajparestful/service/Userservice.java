package com.yash.springdatajparestful.service;

import com.yash.springdatajparestful.dto.UserDto;

import java.util.List;

public interface Userservice {
    public UserDto saveUser(UserDto userDto);
    public  UserDto getUserById(Long id);

    public List<UserDto> getAllUsers();

    public UserDto updateUser(UserDto userDto);

    public void deleteUser(Long id);
}
