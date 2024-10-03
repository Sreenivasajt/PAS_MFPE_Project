package com.cts.pas.consumer.repository;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest(showSql = true)
public class BusinessMasterRepositoryTest {
	
	@Autowired
	private BusinessMasterRepository businessMasterRepository;
	
	@Test
	public void testfindIndexValueSuccess() {
		assertNotNull(
				businessMasterRepository.findIndexValue(10));
	}
	
	@Test
	public void testfindIndexValueFailure() {
		assertNull(
				businessMasterRepository.findIndexValue(1000));
	}

}
