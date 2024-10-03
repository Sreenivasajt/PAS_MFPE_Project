package com.cts.pas.consumer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cts.pas.consumer.enities.PropertyCategory;

@Repository
public interface PropertyCategoryRepository  extends JpaRepository<PropertyCategory, Long> {

	PropertyCategory findByPropertyTypeAndBuildingType(String propertyType, String buildingType);

}
