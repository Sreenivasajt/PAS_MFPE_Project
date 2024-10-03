package com.cts.pas.consumer.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.meanbean.test.BeanTester;
import org.springframework.boot.test.context.SpringBootTest;

import com.cts.pas.consumer.enities.PropertyCategory;

@SpringBootTest
public class PropertyCategoryTest {
	
	private PropertyCategory propertyCategory;
	
	@BeforeEach
	void setup() {
		propertyCategory = new PropertyCategory();
	}
	
	@Test
	void abc() {
		final BeanTester beanTester = new BeanTester();
		beanTester.getFactoryCollection();
		beanTester.testBean(PropertyCategory.class);
	}

}
