package com.cts.pas.consumer.repository;

import javax.validation.constraints.NotNull;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cts.pas.consumer.enities.Consumer;

@Repository
public interface ConsumerRepository extends JpaRepository<Consumer, Long> {

	boolean existsByEmail(String email);

	boolean existsByPanNo(String panNo);

}
