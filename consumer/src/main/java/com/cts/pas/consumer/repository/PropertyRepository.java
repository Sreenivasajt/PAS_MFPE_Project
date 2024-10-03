package com.cts.pas.consumer.repository;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cts.pas.consumer.enities.Property;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {

	Property findByConsumerId(@NotNull Long consumerId);

	Property findByConsumerIdAndId(@Valid Long consumerId, @Valid Long propertyId);

	boolean existsByConsumerIdAndId(@Valid Long consumerId, @Valid Long propertyId);

	boolean existsByConsumerId(@Valid Long consumerId);

	boolean existsByBusinessId(@NotNull Long businessId);

}
