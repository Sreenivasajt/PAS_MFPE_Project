package com.cts.pas.consumer.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.meanbean.test.BeanTester;
import org.springframework.boot.test.context.SpringBootTest;

import com.cts.pas.consumer.enities.BusinessMaster;

@SpringBootTest
public class BusinessMasterTest {

	private BusinessMaster businessMaster;
	
	@BeforeEach
	void setup() {
		businessMaster = new BusinessMaster();
	}
	
	@Test
	void abc() {
		final BeanTester beanTester = new BeanTester();
		beanTester.getFactoryCollection();
		beanTester.testBean(BusinessMaster.class);
	}
}
