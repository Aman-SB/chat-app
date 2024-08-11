package com.aman.chat_application.Service;

import com.aman.chat_application.Model.User;
import com.aman.chat_application.Repository.UserRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class UserServiceTests {

    @Autowired
    private UserRepository userRepository;

    @Disabled
    @ParameterizedTest
    @CsvSource({
            "admin",
            "aman",
            "user1"
    })
    public void testUserByUserName(String name){
        User user = userRepository.findByUserName(name).orElseThrow(() -> new AssertionError("User not found " + name));
        assertNotNull(user, "failed for name" + name);
    }
}
