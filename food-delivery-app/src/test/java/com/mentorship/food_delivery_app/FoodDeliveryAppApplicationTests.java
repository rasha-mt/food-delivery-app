package com.mentorship.food_delivery_app;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.sql.DataSource;          // ✅ ADD THIS IMPORT

@ActiveProfiles("test")
@SpringBootTest
class FoodDeliveryAppApplicationTests {
	@Autowired
	private DataSource dataSource;

	@Test
	void contextLoads() {
	}

}
