package com.yash.springdatajparestful;


import com.yash.springdatajparestful.dto.UserDto;
import com.yash.springdatajparestful.exception.EmailAlreadyExistException;
import com.yash.springdatajparestful.mapper.UserMapper;
import com.yash.springdatajparestful.model.User;
import com.yash.springdatajparestful.repository.UserRepository;
import com.yash.springdatajparestful.service.Userservice;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringdatajparestfulwebservicesApplication.class)
class SpringdatajparestfulwebservicesApplicationTests {
	@Autowired
	private Userservice userservice;
	@Autowired
	private ModelMapper modelMapper;
	@MockBean
	private UserRepository userRepository;
	@BeforeEach
	public void setup()
	{

	}

	@Test
	@DisplayName("Test case for Get All Users")
	void getAllUsersTest() {

		Mockito.when(userRepository.findAll()).thenReturn(Stream.of(new User(101L,"lagnajita","saha","lagnajita.saha@gmail.com"),new User(102L,"srijan","das","srijan.das@gmail.com")).collect(Collectors.toList()));
		assertNotNull(userservice.getAllUsers());
		assertEquals(2, userservice.getAllUsers().size());
	}

	@Test
	@DisplayName("Test case for Get  User By id")
	void getUserByIdTest() {

		Mockito.when(userRepository.findById(101L)).thenReturn(Optional.of(new User(101L,"lagnajita","saha","lagnajita.saha@gmail.com")));
		assertNotNull(userservice.getUserById(101L));
		assertEquals("lagnajita", userservice.getUserById(101L).getFirstName());
	}

	@Test
	@DisplayName("Test case for Create  User ")
	void createUserTest() {
		User user = new User(101L,"lagnajita","saha","lagnajita.saha@gmail.com");

		Mockito.when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());
		Mockito.when(userRepository.save(user)).thenReturn(user);
		UserDto userDto = modelMapper.map(user,UserDto.class);
		UserDto savedUser = userservice.saveUser(userDto);
		assertThat(savedUser.getEmail()).isEqualTo("lagnajita.saha@gmail.com");
		assertThat(savedUser).isNotNull();

	}

	@Test
	@DisplayName("Test case for Create  User which throws exception")

	void createUserTestWithException() {
		User user = new User(101L,"lagnajita","saha","lagnajita.saha@gmail.com");
		Mockito.when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
		UserDto userDto = modelMapper.map(user,UserDto.class);
		assertThrows(EmailAlreadyExistException.class,()->{userservice.saveUser(userDto);});

		Mockito.verify(userRepository,never()).save(Mockito.any(User.class));
	}

	@DisplayName("JUnit test for updateUser method")
	@Test
	public void updateUserTest(){
		// given - precondition or setup
		User user = new User(101L,"lagnajita","saha","lagnajita.saha@gmail.com");
		Mockito.when(userRepository.findById(101L)).thenReturn(Optional.of(user));
		Mockito.when(userRepository.save(user)).thenReturn(user);
		user.setEmail("ram@gmail.com");
		user.setFirstName("Ram");
		// when -  action or the behaviour that we are going test
		UserDto userDto = modelMapper.map(user,UserDto.class);
		UserDto updatedUser = userservice.updateUser(userDto);

		// then - verify the output
		assertThat(updatedUser.getEmail()).isEqualTo("ram@gmail.com");
		assertThat(updatedUser.getFirstName()).isEqualTo("Ram");
	}


	@Test
	@DisplayName("Test case for Delete user By Id ")
	void testDeleteStudentById()
	{
		User user = new User(101L,"lagnajita","saha","lagnajita.saha@gmail.com");
		Mockito.when(userRepository.findById(101L)).thenReturn(Optional.of(user));
		userservice.deleteUser(101L);
		Mockito.verify(userRepository,times(1)).deleteById(101L);
	}

	@AfterEach
	public void tearDown()
	{}
}
