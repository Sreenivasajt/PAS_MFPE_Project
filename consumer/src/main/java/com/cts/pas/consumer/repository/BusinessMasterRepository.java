package com.cts.pas.consumer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cts.pas.consumer.enities.BusinessMaster;

@Repository
public interface BusinessMasterRepository extends JpaRepository<BusinessMaster, Long> {
	
	@Query("SELECT row from BusinessMaster row where row.minPercent <=?1 and row.maxPercent >=?1") 
	BusinessMaster findIndexValue(int businessvalue);

}
