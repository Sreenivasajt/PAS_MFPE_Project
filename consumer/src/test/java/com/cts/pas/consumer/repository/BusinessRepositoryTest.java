package com.cts.pas.consumer.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest(showSql = true)
public class BusinessRepositoryTest {
	
	@Autowired
	private BusinessRepository businessRepository;
	
	@Test
	public void testexistsByConsumerIdSuccess() {
		assertNotNull(
				businessRepository.existsByConsumerId(11L));
	}
	
	@Test
	public void testexistsByConsumerIdFailure() {
//		it i return type is boolean so used boolean assertThat type
		assertThat(
				businessRepository.existsByConsumerId(1L));
	}
	
	@Test
	public void testfindByConsumerIdSuccess() {
		assertNotNull(
				businessRepository.findByConsumerId(11L));
	}
	
	@Test
	public void testfindByConsumerIdFailure() {
		assertNull(
				businessRepository.findByConsumerId(1000L));
	}

}
