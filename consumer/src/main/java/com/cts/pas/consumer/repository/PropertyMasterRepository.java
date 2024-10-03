package com.cts.pas.consumer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cts.pas.consumer.enities.PropertyMaster;

@Repository
public interface PropertyMasterRepository  extends JpaRepository<PropertyMaster, Long> {
	@Query("SELECT row from PropertyMaster row where row.minAge <=?1 and row.maxAge >=?1") 
	PropertyMaster findIndexValue(int value);

}
