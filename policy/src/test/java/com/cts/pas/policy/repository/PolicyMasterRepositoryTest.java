package com.cts.pas.policy.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.cts.pas.policy.entities.PolicyMaster;

@DataJpaTest(showSql = true)
public class PolicyMasterRepositoryTest {
	
	@Autowired
	private PolicyMasterRepository masterRepository;
	
	private PolicyMaster policyMaster;
	
	@Test
	public void testPolicyFindByIdSuccess() {
		assertNotNull(masterRepository.findById(1L));
	}
	
	@Test
	public void testPolicyFindByIdFailure() {
		assertThat(masterRepository.findById(8L).equals(policyMaster));
	}
	
	@Test
	public void testfindByBusinessValueAndPropertyValueSuccess() {
		assertNotNull(
				masterRepository.findByBusinessValueAndPropertyValue(8L, 5L));
	}
	
	@Test
	public void testfindByBusinessValueAndPropertyValueFailure() {
		assertNull(
				masterRepository.findByBusinessValueAndPropertyValue(800L, 8L));
	}

}
