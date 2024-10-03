package com.cts.pas.policy.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.meanbean.test.BeanTester;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class ConsumerPolicyTest {

	private ConsumerPolicy consumerPolicy1;
	private ConsumerPolicy consumerPolicy2;
	
	@BeforeEach
	void setup() {
		consumerPolicy1 = new ConsumerPolicy();
		consumerPolicy2 = new ConsumerPolicy(1L,1L,2L,"ABC","GHI");
	}
	
	@Test
	void abc() {
		final BeanTester beanTester = new BeanTester();
		beanTester.getFactoryCollection();
		beanTester.testBean(ConsumerPolicy.class);
	}
}
