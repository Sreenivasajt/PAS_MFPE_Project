package com.cts.pas.consumer.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.meanbean.test.BeanTester;
import org.springframework.boot.test.context.SpringBootTest;

import com.cts.pas.consumer.enities.PropertyMaster;

@SpringBootTest
public class PropertyMasterTest {
	
	private PropertyMaster propertyMaster;
	
	@BeforeEach
	void setup() {
		propertyMaster = new PropertyMaster();
	}
	
	@Test
	void abc() {
		final BeanTester beanTester = new BeanTester();
		beanTester.getFactoryCollection();
		beanTester.testBean(PropertyMaster.class);
	}

}
