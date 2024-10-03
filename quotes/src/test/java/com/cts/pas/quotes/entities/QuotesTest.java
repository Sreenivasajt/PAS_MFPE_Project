package com.cts.pas.quotes.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import org.meanbean.test.BeanTester;

@SpringBootTest
public class QuotesTest {

	private Quotes quotes1;
	private Quotes quotes2;

	@BeforeEach
	void setup() {
		quotes1 = new Quotes();
		quotes2 = new Quotes(2, 1, 1, "Property in Transit", "32,000 INR");
	}

	@Test
	void abc() {
		final BeanTester beanTester = new BeanTester();
		beanTester.getFactoryCollection();
		beanTester.testBean(Quotes.class);
	}
}
