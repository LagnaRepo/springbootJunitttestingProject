package com.yash.springdatajparestful.service.impl;

import com.yash.springdatajparestful.dto.UserDto;
import com.yash.springdatajparestful.exception.EmailAlreadyExistException;
import com.yash.springdatajparestful.exception.ResourceNotFound;
import com.yash.springdatajparestful.mapper.UserMapper;
import com.yash.springdatajparestful.model.User;
import com.yash.springdatajparestful.repository.UserRepository;
import com.yash.springdatajparestful.service.Userservice;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class UserserviceImpl implements Userservice {

    private UserRepository userRepository;

    private ModelMapper modelMapper;

    @Override
    public UserDto saveUser(UserDto userDto) {
        //check user with the specific mailid already present in DB.
        Optional<User> userWithEmailCheck = userRepository.findByEmail(userDto.getEmail());
            if(userWithEmailCheck.isPresent()) {
                throw new EmailAlreadyExistException("User with EmailId : " + userDto.getEmail() + " Already Exists In DataBase");
            }
        // User user = UserMapper.userDtoToUser(userDto);
        // using ModelMapper in-built library for conversion
        User user = modelMapper.map(userDto,User.class);
        // return  UserMapper.userToUserDto(savedUser);
        return  modelMapper.map(userRepository.save(user),UserDto.class);
    }

    @Override
    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(()->new ResourceNotFound("UserId","Id",id));

       // return  UserMapper.userToUserDto(user);
        return  modelMapper.map(user,UserDto.class);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> userList = userRepository.findAll();
//        return userList.stream().map(UserMapper::userToUserDto)
//                .collect(Collectors.toList());
        return userList.stream().map(u->modelMapper.map(u,UserDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public UserDto updateUser(UserDto userDto) {
        User updatedUser= userRepository.findById(userDto.getId())
                 .map(user1 -> {
                    user1.setFirstName(userDto.getFirstName());
                    user1.setLastName(userDto.getLastName());
                    user1.setEmail(userDto.getEmail());
                    return user1;
                 }).orElseThrow(()->new ResourceNotFound("UserId","Id",userDto.getId()));
        // return UserMapper.userToUserDto(userRepository.save(updatedUser));
        return modelMapper.map(userRepository.save(updatedUser),UserDto.class);
    }

    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(()->new ResourceNotFound("UserId","Id",id));
        userRepository.deleteById(id);
    }
}
