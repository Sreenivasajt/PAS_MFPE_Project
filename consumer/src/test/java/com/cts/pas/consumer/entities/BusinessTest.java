package com.cts.pas.consumer.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.meanbean.test.BeanTester;
import org.springframework.boot.test.context.SpringBootTest;

import com.cts.pas.consumer.enities.Business;

@SpringBootTest
public class BusinessTest {
	
	private Business business1;
	private Business business2;
	
	@BeforeEach
	void setup() {
		business1 = new Business();
		business2 = new Business(2L, 1L, "Pro", "INR", 3L, 4L, 5L, 6L, 7L);
	}
	
	@Test
	void abc() {
		final BeanTester beanTester = new BeanTester();
		beanTester.getFactoryCollection();
		beanTester.testBean(Business.class);
	}
	
	
}
