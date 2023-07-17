package org.example.shixi;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;

@SpringBootTest
class ShixiApplicationTests {

    @Test
    void contextLoads() {
    }
    @Test
    void encode() {
        String encodeAes = "user@Thinking";
        System.out.println(encodeAes);
        String encode = PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(encodeAes);
        System.out.println(encode);
    }
}
