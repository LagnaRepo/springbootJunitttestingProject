package com.yash.springdatajparestful.repository;

import com.yash.springdatajparestful.SpringdatajparestfulwebservicesApplication;
import com.yash.springdatajparestful.dto.UserDto;
import com.yash.springdatajparestful.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ContextConfiguration(classes = SpringdatajparestfulwebservicesApplication.class )
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;



    private User user;

    @BeforeEach
    void setUp() {
         user = new User(101L,"lagnajita","saha","lagnajita.saha@gmail.com");

    }

    @DisplayName("JUnit test for save user operation")
    @Test
    public void givenEmployeesList_whenFindAll_thenEmployeesList(){
        // given - precondition or setup

        User user1 = new User(102L,"sijan","das","srijan.das@gmail.com");


        User savedUser = userRepository.save(user1);

        // then - verify the output
        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getId()).isGreaterThan(0);

    }
    @Test
    public void findByUserId() {
        User user1 = new User(101L,"lagnajita","saha","lagnajita.saha@gmail.com");

        User savedUser = userRepository.save(user1);
        User userDB = userRepository.findById(user1.getId()).get();

        assertEquals(user1.getFirstName(), userDB.getFirstName());
    }

    @Test
    public void finAlltUsers() {
        User user1 = new User(102L,"srijan","das","srijan.das@gmail.com");

        User savedUser = userRepository.save(user1);
        User savedUser2 = userRepository.save(user);
        List<User> userDBList = userRepository.findAll();

        assertThat(userDBList).isNotNull();
        assertThat(userDBList.size()).isEqualTo(2);
    }

    @Test
    public void updateUser() {
        User user1 = new User(101L,"lagnajita","saha","lagnajita.saha@gmail.com");

        User savedUser = userRepository.save(user1);
        User userDb = userRepository.findById(user1.getId()).get();
        userDb.setFirstName("Ram");
        userDb.setLastName("Saha");
        User updateuser = userRepository.save(userDb);

        assertEquals("Ram", updateuser.getFirstName());
    }

    @Test
    public void deleteUserId() {
        User user1 = new User(101L,"lagnajita","saha","lagnajita.saha@gmail.com");

        User savedUser = userRepository.save(user1);
        userRepository.deleteById(user1.getId());
        Optional<User> optionalUser=  userRepository.findById(user1.getId());

        assertThat(optionalUser).isEmpty();



    }
    @AfterEach
    void tearDown() {
    }
}