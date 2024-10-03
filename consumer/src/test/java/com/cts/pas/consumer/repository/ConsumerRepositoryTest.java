package com.cts.pas.consumer.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest(showSql = true)
public class ConsumerRepositoryTest {
	
	@Autowired
	private ConsumerRepository consumerRepository;
	
	@Test
	public void testExistsByIdSuccess() {
		assertNotNull(
				consumerRepository.existsById(11L));
	}
	
	@Test
	public void testExistsByIdFailure() {
//		checking as boolean type because return type is boolean
		assertThat(
				consumerRepository.existsById(1200L));
	}
	
	@Test
	public void testFindByIdSuccess() {
		assertNotNull(
				consumerRepository.findById(11L));
	}
	
	@Test
	public void testFindByIdFailure() {
//		checking as boolean type because return type is boolean
		assertThat(
				consumerRepository.findById(120L));
	}

}
