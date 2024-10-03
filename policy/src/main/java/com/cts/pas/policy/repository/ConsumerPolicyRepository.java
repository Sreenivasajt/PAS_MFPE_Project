package com.cts.pas.policy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.cts.pas.policy.entities.ConsumerPolicy;

@Repository
public interface ConsumerPolicyRepository extends JpaRepository<ConsumerPolicy, Long> {

	ConsumerPolicy findByConsumerid(Long consumerId);

}
