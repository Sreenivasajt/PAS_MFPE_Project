package com.cts.pas.policy.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.meanbean.test.BeanTester;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PolicyMasterTest {
	private PolicyMaster policyMaster1;
	private PolicyMaster policyMaster2;
	
	@BeforeEach
	void setup() {
		policyMaster1 = new PolicyMaster();
		policyMaster2 = new PolicyMaster(1L,"P01","Building","Owner","2,00,00,000","2 year",8L,5L,"Chennai","Pay Back");
	}
	
	@Test
	void abc() {
		final BeanTester beanTester = new BeanTester();
		beanTester.getFactoryCollection();
		beanTester.testBean(PolicyMaster.class);
	}

}
