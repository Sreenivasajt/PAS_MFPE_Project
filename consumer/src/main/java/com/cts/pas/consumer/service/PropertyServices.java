package com.cts.pas.consumer.service;

import javax.el.PropertyNotFoundException;

import javax.validation.Valid;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cts.pas.consumer.enities.Property;
import com.cts.pas.consumer.enities.PropertyCategory;
import com.cts.pas.consumer.enities.PropertyMaster;
import com.cts.pas.consumer.exceptions.PropertyIdNotFoundException;
import com.cts.pas.consumer.repository.BusinessRepository;
import com.cts.pas.consumer.repository.ConsumerRepository;
import com.cts.pas.consumer.repository.PropertyCategoryRepository;
import com.cts.pas.consumer.repository.PropertyMasterRepository;
import com.cts.pas.consumer.repository.PropertyRepository;
import com.cts.pas.consumer.request.BusinessPropertyAddRequest;
import com.cts.pas.consumer.response.ConsumerBusinessViewResponse;
import com.cts.pas.consumer.response.MessageResponse;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PropertyServices {

	@Autowired
	private PropertyRepository propertyRepository;

	@Autowired
	private PropertyMasterRepository propertyMasterRepository;

	@Autowired
	private PropertyCategoryRepository propertyCategoryRepository;

	@Autowired
	private BusinessRepository businessRepo;

	@Autowired
	private ConsumerRepository consumerRepo;

	public MessageResponse createBusinessProperty(@Valid BusinessPropertyAddRequest data) {
		/**
		 * Create new Business property return status
		 */
		log.info("createBusinessProperty function call");
		log.debug(data.toString());

		if (!isPropertyEligible(data.getPropertyType(), data.getBuildingAge(), data.getBuildingType())) {
			log.error("This Property Not Eligible!");
			return new MessageResponse("This Property Not Eligible!", HttpStatus.NOT_ACCEPTABLE);
		}

		if (!consumerRepo.existsById(data.getConsumerId())) {
			log.error("Invalid Customer Id");
			return new MessageResponse("Invalid Consumer Id", HttpStatus.NOT_ACCEPTABLE);
		}

		if (!businessRepo.existsByConsumerId(data.getConsumerId())) {
			log.error("Invalid Customer Id");
			return new MessageResponse("Invalid Consumer Id", HttpStatus.NOT_ACCEPTABLE);
		}

		if (!businessRepo.existsById(data.getBusinessId())) {
			log.error("Invalid Business Id");
			return new MessageResponse("Invalid Business Id", HttpStatus.NOT_ACCEPTABLE);
		}

		if (propertyRepository.existsByConsumerId(data.getConsumerId())) {
			log.error("Already Property registerd!");
			return new MessageResponse("Already Property registerd!", HttpStatus.NOT_ACCEPTABLE);
		}

		if (propertyRepository.existsByBusinessId(data.getBusinessId())) {
			log.error("Already Property registerd!");
			return new MessageResponse("Already Property registerd!", HttpStatus.NOT_ACCEPTABLE);
		}

		Long propertyValue = calculatePropertyValue(data.getCostoftheAsset(), data.getSalvageValue(),
				data.getUsefulLifeOfTheAsset());

		Property property = new Property();
		property.setBusinessId(data.getBusinessId());
		property.setConsumerId(data.getConsumerId());
		property.setBuildingAge(data.getBuildingAge());
		property.setBuildingSqft(data.getBuildingSqft());
		property.setBuildingStoreys(data.getBuildingStoreys());
		property.setBuildingType(data.getBuildingType());
		property.setCostoftheAsset(data.getCostoftheAsset());
		property.setPropertyType(data.getPropertyType());
		property.setPropertyValue(propertyValue);
		property.setSalvageValue(data.getSalvageValue());
		property.setUsefulLifeOfTheAsset(data.getUsefulLifeOfTheAsset());
		propertyRepository.save(property);

		log.debug("Consumer property created with property Id : " + property.getId());
		return new MessageResponse("Consumer property created with property Id : " + property.getId(), HttpStatus.OK);
	}

	public MessageResponse updateBusinessProperty(@Valid Property data) {
		/**
		 * Update Business property return status
		 */
		log.info("updateBusinessProperty function call");
		log.debug(data.toString());

		if (!isPropertyEligible(data.getPropertyType(), data.getBuildingAge(), data.getBuildingType())) {
			log.error("This Property Not Eligible!");
			return new MessageResponse("This Property Not Eligible!", HttpStatus.NOT_ACCEPTABLE);
		}

		if (!consumerRepo.existsById(data.getConsumerId())) {
			log.error("Invalid Customer Id");
			return new MessageResponse("Invalid Consumer Id", HttpStatus.NOT_ACCEPTABLE);
		}

		if (!businessRepo.existsByConsumerId(data.getConsumerId())) {
			log.error("Invalid Customer Id");
			return new MessageResponse("Invalid Consumer Id", HttpStatus.NOT_ACCEPTABLE);
		}

		if (!businessRepo.existsById(data.getBusinessId())) {
			log.error("Invalid Business Id");
			return new MessageResponse("Invalid Business Id", HttpStatus.NOT_ACCEPTABLE);
		}

		if (!propertyRepository.existsById(data.getId())) {
			log.error("Invalid Property Id");
			return new MessageResponse("Invalid Property Id", HttpStatus.NOT_ACCEPTABLE);
		}

		Long propertyValue = calculatePropertyValue(data.getCostoftheAsset(), data.getSalvageValue(),
				data.getUsefulLifeOfTheAsset());

		Property property = propertyRepository.findByConsumerId(data.getConsumerId());

		property.setBusinessId(data.getBusinessId());
		property.setConsumerId(data.getConsumerId());
		property.setBuildingAge(data.getBuildingAge());
		property.setBuildingSqft(data.getBuildingSqft());
		property.setBuildingStoreys(data.getBuildingStoreys());
		property.setBuildingType(data.getBuildingType());
		property.setCostoftheAsset(data.getCostoftheAsset());
		property.setPropertyType(data.getPropertyType());
		property.setPropertyValue(propertyValue);
		property.setSalvageValue(data.getSalvageValue());
		property.setUsefulLifeOfTheAsset(data.getUsefulLifeOfTheAsset());
		propertyRepository.save(property);

		log.info("Property details updated succesfully!");
		return new MessageResponse("Property details updated succesfully!", HttpStatus.OK);
	}

	public Property viewBusinessProperty(@Valid Long consumerId) throws Exception {
		/**
		 * Get property details by consumer ID
		 */
		log.info("viewBusinessProperty function call");

		if (!propertyRepository.existsByConsumerId(consumerId)) {
			log.error("Property Not Found");
			throw new PropertyIdNotFoundException("Property Not Found");
		}

		Property property = propertyRepository.findByConsumerId(consumerId);
		log.debug(property.toString());
		return property;
	}

	/**
	 * 
	 * @param propertyType
	 * @param buildingAge
	 * @param buildingType
	 * @return
	 */
	private boolean isPropertyEligible(String propertyType, @NotNull Long buildingAge, String buildingType) {
		/**
		 * Check eligibility of property
		 */
		log.info("isPropertyEligible function call");
		Boolean isPropertyEligible = false;

		PropertyCategory propertyCategory = propertyCategoryRepository.findByPropertyTypeAndBuildingType(propertyType,
				buildingType);

		if (propertyCategory == null) {
			log.error("property not eligible");
			return isPropertyEligible;
		}

		if (propertyCategory.getBuildingAge() <= buildingAge)
			isPropertyEligible = true;
		log.info("property eligible");
		return isPropertyEligible;
	}

	/**
	 * 
	 * @param costoftheAsset
	 * @param salvageValue
	 * @param usefulLifeOfTheAsset
	 * @return
	 */
	private Long calculatePropertyValue(@NotNull Long costoftheAsset, @NotNull Long salvageValue,
			@NotNull Long usefulLifeOfTheAsset) {
		/**
		 * Calculate property value
		 */

		if (costoftheAsset == 0 || salvageValue == 0 || usefulLifeOfTheAsset == 0 || (costoftheAsset == salvageValue))
			throw new NullPointerException("Property value Calculation Error");

		log.info("START : calculatePropertyValue()");
		Double x_ratio = (double) ((costoftheAsset - salvageValue) / (float) usefulLifeOfTheAsset);
		x_ratio = x_ratio / costoftheAsset;
		log.info("x_ratio : {}", x_ratio);
		int value = (int) Math.abs(Math.round(x_ratio * 1000));
		log.info("x_ratio * 1000 : {}", value);

		if (value >= 100) {
			log.info("Property value >= 100 , Property index value : 0");
			return 0L;
		}
		PropertyMaster master = propertyMasterRepository.findIndexValue(value);
		Long getValue = (long) master.getIndex();
		log.info("Business index value : {}", master.getIndex());
		log.info("STOP : calculatePropertyValue()");
		return getValue;

	}

}
