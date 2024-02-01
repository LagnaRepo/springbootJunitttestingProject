package com.yash.springdatajparestful.controller;

import com.yash.springdatajparestful.dto.UserDto;
import com.yash.springdatajparestful.exception.ErrorDetails;
import com.yash.springdatajparestful.exception.ResourceNotFound;
import com.yash.springdatajparestful.model.User;
import com.yash.springdatajparestful.service.Userservice;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.List;
@Tag(name="CRUD REST Apis for userManagement",
    description ="CRUD REST Apis-> Create user, Update User, Get User By Id, Get all Users,Delete User."
)
@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
public class UserController {

    private Userservice userservice;
    @Operation(
            summary = "Create User rest api",
            description = "Create User rest api is used to save user in the database"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http Status 201 Created"
    )
    @PostMapping("saveUser")
    public ResponseEntity<UserDto> createUser(@RequestBody @Valid UserDto userDto)
    {
        UserDto savedUser= userservice.saveUser(userDto);
        return  new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get User rest api",
            description = "Get User rest api is used to get single  user from  the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 Ok"
    )

    @GetMapping("{id}")
    public ResponseEntity<UserDto> getUserbyId(@PathVariable("id") Long userId) {
        UserDto userDto = userservice.getUserById(userId);
        return ResponseEntity.ok(userDto);
    }

    @Operation(
            summary = "Get All Users rest api",
            description = "Get All Users rest api is used to get All  users from  the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 Ok"
    )

    @GetMapping()
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> userList = userservice.getAllUsers();
        return ResponseEntity.ok(userList);
    }

    @Operation(
            summary = "Update  Users rest api",
            description = "update  User rest api is used to update  user from  the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 Ok"
    )

    @PutMapping("update/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable("id") Long userId, @RequestBody @Valid UserDto userDto) {
      userDto.setId(userId);
      UserDto updateduser= userservice.updateUser(userDto);
        return ResponseEntity.ok(updateduser);
    }

    @Operation(
            summary = "Delete User rest api",
            description = "Delete  User rest api is used to delete  user from  the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 Ok"
    )
    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteuser(@PathVariable("id") Long userId) {
        userservice.deleteUser(userId);
        return ResponseEntity.ok("User with id : "+userId+" is deleted sucessfully...");
    }


    // controller Specific-> Specific exception Handler.

//    @ExceptionHandler(ResourceNotFound.class)
//    public ResponseEntity<ErrorDetails> getCustomErrorForNotFound(ResourceNotFound e, WebRequest webRequest)
//    {
//        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(),
//                e.getMessage(),webRequest.getDescription(false),
//                "USER_NOT_FOUND");
//        return new ResponseEntity<>(errorDetails,HttpStatus.NOT_FOUND);
//    }

}
