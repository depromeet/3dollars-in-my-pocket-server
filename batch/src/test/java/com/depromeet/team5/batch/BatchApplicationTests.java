package com.depromeet.team5.batch;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BatchApplicationTests {
    @Autowired
    private LoginService loginService;

    @Test
    void contextLoads() {
    }
}
