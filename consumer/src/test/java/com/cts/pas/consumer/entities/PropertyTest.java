package com.cts.pas.consumer.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.meanbean.test.BeanTester;
import org.springframework.boot.test.context.SpringBootTest;

import com.cts.pas.consumer.enities.Property;

@SpringBootTest
public class PropertyTest {
	
	private Property property1;
	private Property property2;
	
	@BeforeEach
	void setup() {
		property1 = new Property();
		property2 = new Property(1L, 2L, 3L, "AB", "CD", "EF", "GH", 3L, 5L, 6L, 7L, 8L);
	}
	
	@Test
	void abc() {
		final BeanTester beanTester = new BeanTester();
		beanTester.getFactoryCollection();
		beanTester.testBean(Property.class);
	}

}
