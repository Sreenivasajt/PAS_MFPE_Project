package com.cts.pas.consumer.repository;

import javax.validation.constraints.NotNull;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cts.pas.consumer.enities.Business;

@Repository
public interface BusinessRepository  extends JpaRepository<Business, Long> {

	boolean existsByConsumerId(@NotNull Long consumerId);

	Business findByConsumerId(@NotNull Long consumerId);

}
