package com.cts.pas.policy.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import org.springframework.stereotype.Repository;

import com.cts.pas.policy.entities.PolicyMaster;

@Repository
public interface PolicyMasterRepository extends JpaRepository<PolicyMaster, Long> {

	PolicyMaster findByBusinessValueAndPropertyValue(Long businessvalue, Long propertyValue);

}
