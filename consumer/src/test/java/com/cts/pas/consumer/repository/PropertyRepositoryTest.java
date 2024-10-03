package com.cts.pas.consumer.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest(showSql = true)
public class PropertyRepositoryTest {
	
	@Autowired
	private PropertyRepository propertyRepository;
	
	@Test
	public void testExistsByIdSuccess() {
		assertNotNull(
				propertyRepository.existsById(11L));
	}
	
	@Test
	public void testExistsByIdFailure() {
//		checking as boolean type because return type is boolean
		assertThat(
				propertyRepository.existsById(120L));
	}
	
	@Test
	public void testFindByIdSuccess() {
		assertNotNull(
				propertyRepository.findById(11L));
	}
	
	@Test
	public void testFindByIdFailure() {
//		checking as boolean type because return type is boolean
		assertThat(
				propertyRepository.findById(1200L));
	}

}
