package com.cts.pas.consumer.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.meanbean.test.BeanTester;
import org.springframework.boot.test.context.SpringBootTest;

import com.cts.pas.consumer.enities.BusinessCategory;

@SpringBootTest
public class BusinessCategoryTest {
	
	private BusinessCategory businessCategory;
	
	@BeforeEach
	void setup() {
		businessCategory = new BusinessCategory();
	}

	@Test
	void abc() {
		final BeanTester beanTester = new BeanTester();
		beanTester.getFactoryCollection();
		beanTester.testBean(BusinessCategory.class);
	}

}
