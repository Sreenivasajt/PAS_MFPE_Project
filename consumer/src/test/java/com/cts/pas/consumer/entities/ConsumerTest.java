package com.cts.pas.consumer.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.meanbean.test.BeanTester;
import org.springframework.boot.test.context.SpringBootTest;

import com.cts.pas.consumer.enities.Consumer;

@SpringBootTest
public class ConsumerTest {

	private Consumer consumer1;
	private Consumer consumer2;
	
	@BeforeEach
	void setup() {
		consumer1 = new Consumer();
		consumer2 = new Consumer(1L, "AB", "CD", "EF", "GH", 2L, "IJ");
	}
	
	@Test
	void abc() {
		final BeanTester beanTester = new BeanTester();
		beanTester.getFactoryCollection();
		beanTester.testBean(Consumer.class);
	}
	
}
