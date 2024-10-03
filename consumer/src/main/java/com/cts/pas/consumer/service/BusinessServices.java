package com.cts.pas.consumer.service;

import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cts.pas.consumer.enities.Consumer;
import com.cts.pas.consumer.exceptions.BusinessNotFoundException;
import com.cts.pas.consumer.exceptions.ConsumerNotFoundException;
import com.cts.pas.consumer.enities.Business;
import com.cts.pas.consumer.enities.BusinessCategory;
import com.cts.pas.consumer.enities.BusinessMaster;
import com.cts.pas.consumer.repository.BusinessCategoryRepository;
import com.cts.pas.consumer.repository.BusinessMasterRepository;
import com.cts.pas.consumer.repository.BusinessRepository;
import com.cts.pas.consumer.repository.ConsumerRepository;
import com.cts.pas.consumer.request.ConsumerBusinessAddRequest;
import com.cts.pas.consumer.request.ConsumerBusinessUpdateRequest;
import com.cts.pas.consumer.response.ConsumerBusinessViewResponse;
import com.cts.pas.consumer.response.MessageResponse;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BusinessServices {

	@Autowired
	private BusinessRepository businessRepo;

	@Autowired
	private BusinessMasterRepository businessMasterRepository;

	@Autowired
	private BusinessCategoryRepository businessCategoryRepository;

	@Autowired
	private ConsumerRepository consumerRepo;

	
	
	public MessageResponse addConsumerBusiness(ConsumerBusinessAddRequest data) {
		/**
		 * Add new customer and business details return status
		 */
		log.info("addConsumerBusiness function call");
		log.debug(data.toString());

		if (!isBusinessEligible(data.getBusinesscategory(), data.getBusinesstype(), data.getTotalemployees(),
				data.getBusinessage())) {
			log.error("This Business Not Eligible!");
			return new MessageResponse("This Business Not Eligible!", HttpStatus.NOT_ACCEPTABLE);
		}
		if (consumerRepo.existsByEmail(data.getEmail())) {
			log.error("This Email Already Registered!");
			return new MessageResponse("This Email Already Registered!", HttpStatus.NOT_ACCEPTABLE);
		}
		if (consumerRepo.existsByPanNo(data.getPanNo())) {
			log.error("This PanCard Details Already Registered!");
			return new MessageResponse("This PanCard Details Already Registered!", HttpStatus.NOT_ACCEPTABLE);
		}

		Consumer consumers = new Consumer();
		consumers.setAgentId(data.getAgentId());
		consumers.setDateOfBirth(data.getDateOfBirth());
		consumers.setEmail(data.getEmail());
		consumers.setName(data.getName());
		consumers.setPanNo(data.getPanNo());
		consumers.setValidityInDays(data.getValidityInDays());
		Consumer consumer = consumerRepo.save(consumers);

		Business business = new Business();
		Long businessValue = calculateBusinessValue(data.getBusinessturnover(), data.getCapitalInvested());
		business.setBusinessage(data.getBusinessage());
		business.setBusinesscategory(data.getBusinesscategory());
		business.setBusinessturnover(data.getBusinessturnover());
		business.setBusinesstype(data.getBusinesstype());
		business.setBusinessvalue(businessValue);
		business.setCapitalInvested(data.getCapitalInvested());
		business.setConsumerId(consumer.getConsumerId());
		business.setTotalemployees(data.getTotalemployees());
		businessRepo.save(business);

		log.info("Successfully created with cosnumer Id : " + consumer.getConsumerId());
		return new MessageResponse("Successfully created with cosnumer Id : " + consumer.getConsumerId(),
				HttpStatus.OK);
	}

	public MessageResponse updateConsumerBusiness(ConsumerBusinessUpdateRequest data) {
		/**
		 * Update existing consumer and Business details return status
		 */
		log.info("updateConsumerBusiness function call");
		log.debug(data.toString());

		if (!isBusinessEligible(data.getBusinesscategory(), data.getBusinesstype(), data.getTotalemployees(),
				data.getBusinessage())) {
			log.error("This Business Not Eligible!");
			return new MessageResponse("This Business Not Eligible!", HttpStatus.NOT_ACCEPTABLE);
		}

		if (!consumerRepo.existsById(data.getConsumerId())) {
			log.error("Invalid Customer Id");
			return new MessageResponse("Invalid Cusumer Id", HttpStatus.NOT_ACCEPTABLE);
		}

		if (!businessRepo.existsByConsumerId(data.getConsumerId())) {
			log.error("Invalid Customer Id");
			return new MessageResponse("Invalid Cusumer Id", HttpStatus.NOT_ACCEPTABLE);
		}
		

		if (!businessRepo.existsById(data.getBusinessId())) {
			log.error("Invalid Business Id");
			return new MessageResponse("Invalid Business Id", HttpStatus.NOT_ACCEPTABLE);
		}

		Optional<Consumer> consumer = consumerRepo.findById(data.getConsumerId());
		Consumer consumers = consumer.get();

		Business business = businessRepo.findByConsumerId(data.getConsumerId());

		consumers.setAgentId(data.getAgentId());
		consumers.setConsumerId(data.getConsumerId());
		consumers.setDateOfBirth(data.getDateOfBirth());
		consumers.setEmail(data.getEmail());
		consumers.setName(data.getName());
		consumers.setPanNo(data.getPanNo());
		consumers.setValidityInDays(data.getValidityInDays());
		consumerRepo.save(consumers);

		Long businessValue = calculateBusinessValue(data.getBusinessturnover(), data.getCapitalInvested());
		business.setBusinessage(data.getBusinessage());
		business.setBusinesscategory(data.getBusinesscategory());
		business.setBusinessturnover(data.getBusinessturnover());
		business.setBusinesstype(data.getBusinesstype());
		business.setBusinessvalue(businessValue);
		business.setCapitalInvested(data.getCapitalInvested());
		business.setConsumerId(data.getConsumerId());
		business.setTotalemployees(data.getTotalemployees());
		businessRepo.save(business);

		log.error("Updated successfully");
		return new MessageResponse("Updated successfully", HttpStatus.OK);

	}

	
	
	public ConsumerBusinessViewResponse viewConsumerBusinessById(@Valid Long consumerId) throws Exception {
		/**
		 * Get consumer details by ID return ConsumerBusinessViewResponse
		 */
		log.info("viewConsumerBusinessById function call");

		if (!consumerRepo.existsById(consumerId)) {
			log.error("Consumer Not Found");
			throw new ConsumerNotFoundException("Consumer Not Found");
		}
		if (!businessRepo.existsByConsumerId(consumerId)) {
			log.error("Business Not Found");
			throw new BusinessNotFoundException("Business Not Found");
		}

		Optional<Consumer> consumer = consumerRepo.findById(consumerId);
		Consumer consumers = consumer.get();
		Business business = businessRepo.findByConsumerId(consumerId);

		ConsumerBusinessViewResponse response = new ConsumerBusinessViewResponse();
		response.setConsumerId(consumers.getConsumerId());
		response.setName(consumers.getName());
		response.setDateOfBirth(consumers.getDateOfBirth());
		response.setEmail(consumers.getEmail());
		response.setPanNo(consumers.getPanNo());
		response.setValidityInDays(consumers.getValidityInDays());
		response.setAgentId(consumers.getAgentId());

		response.setBusinessId(business.getId());
		response.setBusinessage(business.getBusinessage());
		response.setBusinessturnover(business.getBusinessturnover());
		response.setBusinessvalue(business.getBusinessvalue());
		response.setCapitalInvested(business.getCapitalInvested());
		response.setTotalemployees(business.getTotalemployees());
		response.setBusinesscategory(business.getBusinesscategory());
		response.setBusinesstype(business.getBusinesstype());

		return response;
	}

	/**
	 * 
	 * @param businesscategory
	 * @param businesstype
	 * @param totalemployees
	 * @param businessage
	 * @return
	 */
	private boolean isBusinessEligible(String businesscategory, String businesstype, Long totalemployees,
			Long businessage) {
		/**
		 * Check business eligibility while create business
		 */
		log.info("isBusinessEligible function call");
		Boolean isBusinessMasterPresent = false;

		BusinessCategory businessCategory = businessCategoryRepository
				.findByBusinessCategoryAndBusinessType(businesscategory, businesstype);

		if (businessCategory == null) {
			log.error("Business category not found");
			return isBusinessMasterPresent;
		}

		if (businessCategory.getTotalEmployees() <= totalemployees || businessCategory.getBusinessAge() <= businessage)
			isBusinessMasterPresent = true;

		log.info("Business eligible");
		return isBusinessMasterPresent;

	}

	/**
	 * 
	 * @param businessturnover
	 * @param capitalInvested
	 * @return
	 */
	private Long calculateBusinessValue(@NotNull Long businessturnover, Long capitalInvested) {
		log.info("START : calculateBusinessValue()");
		if (businessturnover <= 0 || capitalInvested <= 0) {
			log.error("Business value Calculation Error");
			throw new NullPointerException("Business value Calculation Error");
		}
		Double ratio = (double) (businessturnover / (float) capitalInvested);
		Double percentage = ratio * 100;
		log.error("percentage : {}", percentage);
		int value = (int) Math.abs(Math.round(percentage));
		if (value > 100) {
			log.info("Business value > 100 , Business index value : 10");
			return 10L;
		}
		BusinessMaster master = businessMasterRepository.findIndexValue(value);
		Long getValue = (long) master.getIndex();
		log.error("Business index value : {}", master.getIndex());
		log.info("STOP : calculateBusinessValue()");
		return getValue;

	}

}
