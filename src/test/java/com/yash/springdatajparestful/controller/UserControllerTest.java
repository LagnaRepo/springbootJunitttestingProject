package com.yash.springdatajparestful.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.yash.springdatajparestful.SpringdatajparestfulwebservicesApplication;
import com.yash.springdatajparestful.dto.UserDto;
import com.yash.springdatajparestful.model.User;
import com.yash.springdatajparestful.service.impl.UserserviceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.web.exchanges.HttpExchange;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.awt.*;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ContextConfiguration(classes= SpringdatajparestfulwebservicesApplication.class)
@WebMvcTest
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

   @MockBean
    private UserserviceImpl userservice;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
    }

    @Test
    @DisplayName("Test case for save  User")
    public void userSaveTestController() throws Exception {
        // given - precondition or setup
        UserDto userDto = new UserDto(101L,"lagnajita","saha","lagnajita.saha@gmail.com");
        given(userservice.saveUser(any(UserDto.class)))
                .willAnswer((invocation)-> invocation.getArgument(0));
        String jsonData= objectMapper.writeValueAsString(userDto);

        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post("/api/user/saveUser")
                        .content(jsonData)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated())
                .andReturn();
        String resultContent =mvcResult.getResponse().getContentAsString();
        System.out.println("RESULT: "+resultContent);
        UserDto userDto1 = objectMapper.readValue(resultContent,UserDto.class);
        assertEquals("lagnajita",userDto1.getFirstName());


    }
    @Test
    @DisplayName("Test case for get  Users")
    public void userGetAllTestController() throws Exception {

        // given - precondition or setup
        List<UserDto> listOfUsers = new ArrayList<>();
        listOfUsers.add(new UserDto(101L,"lagnajita","saha","lagnajita.saha@gmail.com"));
        listOfUsers.add(new UserDto(102L,"srijan","das","srijan.das@gmail.com"));
        given(userservice.getAllUsers()).willReturn(listOfUsers);
        // 1st way of invocation and assertion
       ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/user").contentType(MediaType.APPLICATION_JSON_VALUE));
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().json(objectMapper.writeValueAsString(listOfUsers)));

        // 2nd way of invocation and assertion by javatechie
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/api/user")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        String resultContent =mvcResult.getResponse().getContentAsString();
        System.out.println("RESULT: "+resultContent);
// 1st way converting to java list
     List<UserDto> ListOfUserResponse =objectMapper.readValue(resultContent, new TypeReference<List<UserDto>>() {
         @Override
         public Type getType() {
             return super.getType();
         }
     });
        System.out.println("List: "+ListOfUserResponse);
        assertEquals(listOfUsers.size(),ListOfUserResponse.size());
        // 2nd way converting to javaArray
       UserDto[] arrayOfUserResponse =objectMapper.readValue(resultContent,UserDto[].class);
        System.out.println("Aray: "+ Arrays.toString(arrayOfUserResponse));
       assertEquals(listOfUsers.size(),arrayOfUserResponse.length);

    }

    @Test
    @DisplayName("Test case for get  Users by Id")
    public void userGetByIdTestController() throws Exception {
        long userId = 101L;
        UserDto userDto = new UserDto(101L, "lagnajita", "saha", "lagnajita.saha@gmail.com");
        given(userservice.getUserById(userId)).willReturn(userDto);

        // when -  action or the behaviour that we are going test
      //  ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/user/{id}", userId));
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/api/user/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        String resultContent =mvcResult.getResponse().getContentAsString();
        System.out.println("RESULT: "+resultContent);
        UserDto userDto1 = objectMapper.readValue(resultContent,UserDto.class);
        assertEquals("lagnajita",userDto1.getFirstName());
    }

    // JUnit test for update user REST API - positive scenario
    @Test
    @DisplayName("Test case for get  update Users by Id")
    public void givenUpdatedUser_whenUpdateUser_thenReturnUpdateUserObject() throws Exception {
        // given - precondition or setup
        long userId = 101L;
        UserDto userDto = new UserDto(101L, "lagnajita", "saha", "lagnajita.saha@gmail.com");

        UserDto updatedUser = new UserDto(101L,
                "Ram",
                "Jadhav",
                "ram@gmail.com");

        given(userservice.getUserById(userId)).willReturn(userDto);
        given(userservice.updateUser(any(UserDto.class)))
                .willAnswer((invocation) -> invocation.getArgument(0));

        // when -  action or the behaviour that we are going test

        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.put("/api/user/update/{id}", userId)
                        .content(objectMapper.writeValueAsString(updatedUser))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
        String resultContent = mvcResult.getResponse().getContentAsString();
        System.out.println("RESULT: " + resultContent);
        UserDto userDto1 = objectMapper.readValue(resultContent, UserDto.class);
        assertEquals("Ram", userDto1.getFirstName());
    }

    // JUnit test for delete employee REST API
    @Test
    @DisplayName("Test case for get  deete Users by Id")
    public void givenUserId_whenDeleteUser_thenReturn200() throws Exception{
        // given - precondition or setup
        long userId = 101L;
        willDoNothing().given(userservice).deleteUser(101L);

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.delete("/api/user/delete/{id}", userId));

        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print());
    }


        @AfterEach
    void tearDown() {
    }
}