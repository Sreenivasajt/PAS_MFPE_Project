package com.cts.pas.quotes.repositary;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.cts.pas.quotes.entities.Quotes;

@Repository
public interface QuotesRepository extends JpaRepository<Quotes, Integer> {
	
	Quotes findByBusinessValueAndPropertyValueAndPropertyType(Long businessValue, Long propertyValue,
			String propertyType);
}
