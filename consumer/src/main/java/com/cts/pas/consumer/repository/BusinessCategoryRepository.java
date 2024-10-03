package com.cts.pas.consumer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cts.pas.consumer.enities.BusinessCategory;

@Repository
public interface BusinessCategoryRepository extends JpaRepository<BusinessCategory, Long> {

	BusinessCategory findByBusinessCategoryAndBusinessType(String businesscategory, String businesstype);

}
