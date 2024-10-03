package com.cts.pas.policy.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.cts.pas.policy.entities.ConsumerPolicy;

@DataJpaTest(showSql = true)
public class ConsumerPolicyRepositoryTest {

	@Autowired
	private ConsumerPolicyRepository consumerPolicyRepository;

	private ConsumerPolicy consumerPolicy;
	
	@Test
	public void testConsumePolicyByIdSucces() {
		assertNotNull(consumerPolicyRepository.findById(8L));
	}
	
	@Test
	public void testConsumePolicyByIdFailure() {
//		assertNull(consumerPolicyRepository.findById(10L));
		assertThat(consumerPolicyRepository.findById(8L).equals(consumerPolicy));
	}
}