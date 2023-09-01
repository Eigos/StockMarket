package com.stockmarket.sproject;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SprojectApplicationTests {

	@Test
	@DisplayName("Should pass when tests run")
	void contextLoads() {
		assertEquals(0, 0);
	}

}
