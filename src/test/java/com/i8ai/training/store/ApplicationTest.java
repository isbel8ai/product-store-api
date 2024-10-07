package com.i8ai.training.store;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ContextConfiguration(classes = Application.class)
class ApplicationTest {
    @Test
    void contextLoads() {
        assertTrue(true);
    }
}
