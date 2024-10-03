package com.cts.pas.consumer.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.cts.pas.consumer.enities.Business;
import com.cts.pas.consumer.enities.Consumer;
import com.cts.pas.consumer.enities.Property;
import com.cts.pas.consumer.enities.PropertyCategory;
import com.cts.pas.consumer.enities.PropertyMaster;
import com.cts.pas.consumer.exceptions.ConsumerNotFoundException;
import com.cts.pas.consumer.exceptions.PropertyIdNotFoundException;
import com.cts.pas.consumer.repository.BusinessRepository;
import com.cts.pas.consumer.repository.ConsumerRepository;
import com.cts.pas.consumer.repository.PropertyCategoryRepository;
import com.cts.pas.consumer.repository.PropertyMasterRepository;
import com.cts.pas.consumer.repository.PropertyRepository;
import com.cts.pas.consumer.request.BusinessPropertyAddRequest;
import com.cts.pas.consumer.response.MessageResponse;

@SpringBootTest
public class PropertyServiceTest {

	@InjectMocks
	PropertyServices propertyServices;

	@MockBean
	private PropertyRepository propertyRepository;

	@MockBean
	private PropertyMasterRepository propertyMasterRepository;

	@MockBean
	private PropertyCategoryRepository propertyCategoryRepository;

	@MockBean
	private BusinessRepository businessRepo;

	@MockBean
	private ConsumerRepository consumerRepo;

	BusinessPropertyAddRequest businessPropertyAddRequest;
	BusinessPropertyAddRequest businessPropertyAddRequest1;
	BusinessPropertyAddRequest businessPropertyAddRequest2;
	BusinessPropertyAddRequest businessPropertyAddRequest3;
	BusinessPropertyAddRequest businessPropertyAddRequest4;
	PropertyCategory propertyCategory1;
	PropertyCategory propertyCategory2;
	PropertyCategory propertyCategory3;
	PropertyMaster propertyMaster;
	Property property;
	Property property1;
	Property property2;
	Property property3;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
		businessPropertyAddRequest = new BusinessPropertyAddRequest(11L, 11L, "Property In Transit", "1200 sqft",
				"Rented", "1202", 10L, 1200L, 1200L, 1150L, 2L);
		businessPropertyAddRequest1 = new BusinessPropertyAddRequest(11L, 11L, "Property In Transit", "1200 sqft",
				"Rented", "1202", 20L, 1200L, 1200L, 1150L, 2L);
		businessPropertyAddRequest2 = new BusinessPropertyAddRequest(11L, 11L, "Property In Transit", "1200 sqft",
				"Rented", "1202", 20L, 0L, 0L, 1150L, 2L);
		businessPropertyAddRequest3 = new BusinessPropertyAddRequest(11L, 11L, "Property In Transit", "1200 sqft",
				"Rented", "1202", 20L, 1200L, 1200L, 100L, 2L);
		businessPropertyAddRequest4 = new BusinessPropertyAddRequest(11L, 11L, "Property In Transit", "1200 sqft",
				"Rented", "1202", 20L, 1200L, 1200L, 130000L, 2L);
		propertyCategory1 = new PropertyCategory(1L, "Ins type", "property type", "building type", 12L);
		propertyCategory2 = new PropertyCategory(100L, "Ins type", "property type", "building type", 12L);

		propertyMaster = new PropertyMaster(1, 2, 3, 4);
		property = new Property(1L, 2L, 3L, "SB", "ND", "MA", "MI", 40L, 5L, 6L, 7L, 12L);
		property1 = new Property(1L, 2L, 3L, "SB", "ND", "MA", "MI", 4L, 5L, 6L, 7L, 12L);
		property2 = new Property(1L, 2L, 3L, "SB", "ND", "MA", "MI", 40L, 5L, 0L, 0L, 12L);
		property3 = new Property(1L, 2L, 3L, "SB", "ND", "MA", "MI", 40L, 5L, 10L, 20000L, 12L);
	}

	@Test
	@DisplayName("Test Mock PropertyRepository Repository")
	void testBusinessRepositoryNotNull() {
		assertThat(propertyRepository).isNotNull();
	}

	@Test
	@DisplayName("Test createBusinessProperty() return success")
	public void testCreateBusinessPropertyReturnSucess() {

		when(propertyCategoryRepository.findByPropertyTypeAndBuildingType(anyString(), anyString()))
				.thenReturn(propertyCategory1);

		assertThat(propertyServices.createBusinessProperty(businessPropertyAddRequest1)).isNotNull();
	}

	@Test
	@DisplayName("Test createBusinessProperty() Check For All condition return success")
	public void testAddConsumerBusiness() {
		when(propertyCategoryRepository.findByPropertyTypeAndBuildingType(anyString(), anyString()))
				.thenReturn(propertyCategory1);
		when(consumerRepo.existsById(anyLong())).thenReturn(false);
		when(businessRepo.existsByConsumerId(anyLong())).thenReturn(false);
		when(consumerRepo.existsById(anyLong())).thenReturn(false);
		when(propertyRepository.existsByConsumerId(anyLong())).thenReturn(false);
		when(propertyRepository.existsByBusinessId(anyLong())).thenReturn(false);
		when(propertyMasterRepository.findIndexValue(anyInt())).thenReturn(propertyMaster);
		assertThat(propertyServices.createBusinessProperty(businessPropertyAddRequest1)).isNotNull();
	}

	@SuppressWarnings("deprecation")
	@Test
	@DisplayName("Test createBusinessProperty() Business not eligible")
	public void testAddConsumerBusinessBusinessNotEligible() {
		when(propertyCategoryRepository.findByPropertyTypeAndBuildingType(anyString(), anyString()))
				.thenReturn(propertyCategory2);
		assertThat(propertyServices.createBusinessProperty(businessPropertyAddRequest)).isNotNull();
		assertThat(propertyServices.createBusinessProperty(businessPropertyAddRequest))
				.isOfAnyClassIn(MessageResponse.class);
		Assert.assertThat(propertyServices.createBusinessProperty(businessPropertyAddRequest).getMessage(),
				CoreMatchers.containsString("This Property Not Eligible!"));
	}

	@Test
	@DisplayName("Test createBusinessProperty() IsEligiblePropertyEualsToNull")
	public void testAddConsumerBusinessBusinessIsEligiblePropertyEualsToNull() {
		when(propertyCategoryRepository.findByPropertyTypeAndBuildingType("", "")).thenReturn(propertyCategory3);
		assertThat(propertyServices.createBusinessProperty(businessPropertyAddRequest1)).isNotNull();
	}

	@SuppressWarnings("deprecation")
	@Test
	@DisplayName("Test createBusinessProperty() ConsumerRepository existsById")
	public void testCreateBusinessPropertyConsumerRepositoryExistsById() {
		when(propertyCategoryRepository.findByPropertyTypeAndBuildingType(anyString(), anyString()))
				.thenReturn(propertyCategory2);
		when(consumerRepo.existsById(anyLong())).thenReturn(true);
		assertThat(propertyServices.createBusinessProperty(businessPropertyAddRequest1)).isNotNull();
		assertThat(propertyServices.createBusinessProperty(businessPropertyAddRequest1))
				.isOfAnyClassIn(MessageResponse.class);
		Assert.assertThat(propertyServices.createBusinessProperty(businessPropertyAddRequest1).getMessage(),
				CoreMatchers.containsString("Invalid Consumer Id"));
	}

	@SuppressWarnings("deprecation")
	@Test
	@DisplayName("Test createBusinessProperty() BusinessRepository existsByConsumerId")
	public void testCreateBusinessPropertyBusinessRepoExistsByConsumerId() {
		when(propertyCategoryRepository.findByPropertyTypeAndBuildingType(anyString(), anyString()))
				.thenReturn(propertyCategory2);
		when(consumerRepo.existsById(anyLong())).thenReturn(false);
		when(businessRepo.existsByConsumerId(anyLong())).thenReturn(true);
		assertThat(propertyServices.createBusinessProperty(businessPropertyAddRequest1)).isNotNull();
		assertThat(propertyServices.createBusinessProperty(businessPropertyAddRequest1))
				.isOfAnyClassIn(MessageResponse.class);
		Assert.assertThat(propertyServices.createBusinessProperty(businessPropertyAddRequest1).getMessage(),
				CoreMatchers.containsString("Invalid Consumer Id"));
	}

	@SuppressWarnings("deprecation")
	@Test
	@DisplayName("Test createBusinessProperty() BusinessRepository existsById")
	public void testCreateBusinessPropertyBusinessRepoExistsById() {
		when(propertyCategoryRepository.findByPropertyTypeAndBuildingType(anyString(), anyString()))
				.thenReturn(propertyCategory1);
		when(consumerRepo.existsById(anyLong())).thenReturn(true);
		when(businessRepo.existsByConsumerId(anyLong())).thenReturn(true);
		when(businessRepo.existsById(anyLong())).thenReturn(false);
		assertThat(propertyServices.createBusinessProperty(businessPropertyAddRequest1)).isNotNull();
		assertThat(propertyServices.createBusinessProperty(businessPropertyAddRequest1))
				.isOfAnyClassIn(MessageResponse.class);
		Assert.assertThat(propertyServices.createBusinessProperty(businessPropertyAddRequest1).getMessage(),
				CoreMatchers.containsString("Invalid Business Id"));
	}

	@SuppressWarnings("deprecation")
	@Test
	@DisplayName("Test createBusinessProperty() PropertyRepository existsByConsumerId")
	public void testCreateBusinessPropertyPropertyRepoExistsByConsumerId() {
		when(propertyCategoryRepository.findByPropertyTypeAndBuildingType(anyString(), anyString()))
				.thenReturn(propertyCategory1);
		when(consumerRepo.existsById(anyLong())).thenReturn(true);
		when(businessRepo.existsByConsumerId(anyLong())).thenReturn(true);
		when(businessRepo.existsById(anyLong())).thenReturn(true);
		when(propertyRepository.existsByConsumerId(anyLong())).thenReturn(true);
		assertThat(propertyServices.createBusinessProperty(businessPropertyAddRequest1)).isNotNull();
		assertThat(propertyServices.createBusinessProperty(businessPropertyAddRequest1))
				.isOfAnyClassIn(MessageResponse.class);
		Assert.assertThat(propertyServices.createBusinessProperty(businessPropertyAddRequest1).getMessage(),
				CoreMatchers.containsString("Already Property registerd!"));
	}

	@SuppressWarnings("deprecation")
	@Test
	@DisplayName("Test createBusinessProperty() PropertyRepository existsByBusinessId")
	public void testCreateBusinessPropertyPropertyRepoExistsByBusinessId() {
		when(propertyCategoryRepository.findByPropertyTypeAndBuildingType(anyString(), anyString()))
				.thenReturn(propertyCategory1);
		when(consumerRepo.existsById(anyLong())).thenReturn(true);
		when(businessRepo.existsByConsumerId(anyLong())).thenReturn(true);
		when(businessRepo.existsById(anyLong())).thenReturn(true);
		when(propertyRepository.existsByConsumerId(anyLong())).thenReturn(false);
		when(propertyRepository.existsByBusinessId(anyLong())).thenReturn(true);
		assertThat(propertyServices.createBusinessProperty(businessPropertyAddRequest1)).isNotNull();
		assertThat(propertyServices.createBusinessProperty(businessPropertyAddRequest1))
				.isOfAnyClassIn(MessageResponse.class);
		Assert.assertThat(propertyServices.createBusinessProperty(businessPropertyAddRequest1).getMessage(),
				CoreMatchers.containsString("Already Property registerd!"));
	}

	@Test
	@DisplayName("Test createBusinessProperty() Proerty value calculation NullPoiterException")
	public void testCreateBusinessPropertyErrorInCalculationNullPoiterException() {
		when(propertyCategoryRepository.findByPropertyTypeAndBuildingType(anyString(), anyString()))
				.thenReturn(propertyCategory1);
		when(consumerRepo.existsById(anyLong())).thenReturn(true);
		when(businessRepo.existsByConsumerId(anyLong())).thenReturn(true);
		when(consumerRepo.existsById(anyLong())).thenReturn(true);
		when(businessRepo.existsById(anyLong())).thenReturn(true);
		when(propertyRepository.findByConsumerId(anyLong())).thenReturn(property);
		when(propertyRepository.save(any(Property.class))).thenReturn(property);
		when(propertyMasterRepository.findIndexValue(anyInt())).thenReturn(propertyMaster);

		assertThrows(NullPointerException.class, () -> {
			propertyServices.createBusinessProperty(businessPropertyAddRequest2);
		});

	}

	@SuppressWarnings("deprecation")
	@Test
	@DisplayName("Test createBusinessProperty() Proerty value calculation return Success")
	public void testCreateBusinessPropertySuccessInCalculation() {
		when(propertyCategoryRepository.findByPropertyTypeAndBuildingType(anyString(), anyString()))
				.thenReturn(propertyCategory1);
		when(consumerRepo.existsById(anyLong())).thenReturn(true);
		when(businessRepo.existsByConsumerId(anyLong())).thenReturn(true);
		when(consumerRepo.existsById(anyLong())).thenReturn(true);
		when(businessRepo.existsById(anyLong())).thenReturn(true);
		when(propertyRepository.findByConsumerId(anyLong())).thenReturn(property);
		when(propertyRepository.save(any(Property.class))).thenReturn(property);
		when(propertyMasterRepository.findIndexValue(anyInt())).thenReturn(propertyMaster);
		assertThat(propertyServices.createBusinessProperty(businessPropertyAddRequest1)).isNotNull();
		assertThat(propertyServices.createBusinessProperty(businessPropertyAddRequest1))
				.isOfAnyClassIn(MessageResponse.class);
		Assert.assertThat(propertyServices.createBusinessProperty(businessPropertyAddRequest1).getMessage(),
				CoreMatchers.containsString("Consumer property created with property Id"));

	}

	@SuppressWarnings("deprecation")
	@Test
	@DisplayName("Test createBusinessProperty() Proerty value calculation GreaterThan100 return Success")
	public void testCreateBusinessPropertySuccessInCalculationGreaterThan100() {
		when(propertyCategoryRepository.findByPropertyTypeAndBuildingType(anyString(), anyString()))
				.thenReturn(propertyCategory1);
		when(consumerRepo.existsById(anyLong())).thenReturn(true);
		when(businessRepo.existsByConsumerId(anyLong())).thenReturn(true);
		when(consumerRepo.existsById(anyLong())).thenReturn(true);
		when(businessRepo.existsById(anyLong())).thenReturn(true);
		when(propertyRepository.findByConsumerId(anyLong())).thenReturn(property);
		when(propertyRepository.save(any(Property.class))).thenReturn(property);
		when(propertyMasterRepository.findIndexValue(anyInt())).thenReturn(propertyMaster);
		assertThat(propertyServices.createBusinessProperty(businessPropertyAddRequest3)).isNotNull();
		assertThat(propertyServices.createBusinessProperty(businessPropertyAddRequest3))
				.isOfAnyClassIn(MessageResponse.class);
		Assert.assertThat(propertyServices.createBusinessProperty(businessPropertyAddRequest3).getMessage(),
				CoreMatchers.containsString("Consumer property created with property Id"));

	}

	@SuppressWarnings("deprecation")
	@Test
	@DisplayName("Test createBusinessProperty() Proerty value calculation LessThan0 return Success")
	public void testCreateBusinessPropertySuccessInCalculationLessThan0() {
		when(propertyCategoryRepository.findByPropertyTypeAndBuildingType(anyString(), anyString()))
				.thenReturn(propertyCategory1);
		when(consumerRepo.existsById(anyLong())).thenReturn(true);
		when(businessRepo.existsByConsumerId(anyLong())).thenReturn(true);
		when(consumerRepo.existsById(anyLong())).thenReturn(true);
		when(businessRepo.existsById(anyLong())).thenReturn(true);
		when(propertyRepository.findByConsumerId(anyLong())).thenReturn(property);
		when(propertyRepository.save(any(Property.class))).thenReturn(property);
		when(propertyMasterRepository.findIndexValue(anyInt())).thenReturn(propertyMaster);
		assertThat(propertyServices.createBusinessProperty(businessPropertyAddRequest4)).isNotNull();
		assertThat(propertyServices.createBusinessProperty(businessPropertyAddRequest4))
				.isOfAnyClassIn(MessageResponse.class);
		Assert.assertThat(propertyServices.createBusinessProperty(businessPropertyAddRequest4).getMessage(),
				CoreMatchers.containsString("Consumer property created with property Id"));

	}

	@Test
	@DisplayName("Test updateBusinessProperty() return success")
	public void testUpdateConsumerBusiness() {
		when(propertyCategoryRepository.findByPropertyTypeAndBuildingType(anyString(), anyString()))
				.thenReturn(propertyCategory1);
		when(consumerRepo.existsById(anyLong())).thenReturn(true);
		when(businessRepo.existsByConsumerId(anyLong())).thenReturn(true);
		when(consumerRepo.existsById(anyLong())).thenReturn(true);
		when(businessRepo.existsById(anyLong())).thenReturn(true);
		when(propertyRepository.findByConsumerId(anyLong())).thenReturn(property);
		when(propertyRepository.save(any(Property.class))).thenReturn(property);
		when(propertyMasterRepository.findIndexValue(anyInt())).thenReturn(propertyMaster);
		assertThat(propertyServices.updateBusinessProperty(property)).isNotNull();
	}

	@Test
	@DisplayName("Test createBusinessProperty() IsEligiblePropertyEualsToNull")
	public void testUpdateConsumerBusinessIsEligiblePropertyEualsToNull() {
		when(propertyCategoryRepository.findByPropertyTypeAndBuildingType("", "")).thenReturn(propertyCategory3);
		assertThat(propertyServices.updateBusinessProperty(property)).isNotNull();
	}

	@SuppressWarnings("deprecation")
	@Test
	@DisplayName("Test updateBusinessProperty() InvalidProperty")
	public void testUpdateConsumerBusinessInvalidProperty() {
		when(propertyCategoryRepository.findByPropertyTypeAndBuildingType(anyString(), anyString()))
				.thenReturn(propertyCategory1);
		assertThat(propertyServices.updateBusinessProperty(property1)).isNotNull();
		Assert.assertThat(propertyServices.updateBusinessProperty(property1).getMessage(),
				CoreMatchers.containsString("This Property Not Eligible!"));
	}

	@SuppressWarnings("deprecation")
	@Test
	@DisplayName("Test updateConsumerProerty() InvalidConsumerId")
	public void testupdateBusinessPropertyInvalidConsumerId() {
		when(propertyCategoryRepository.findByPropertyTypeAndBuildingType(anyString(), anyString()))
				.thenReturn(propertyCategory1);
		when(consumerRepo.existsById(anyLong())).thenReturn(false);

		assertThat(propertyServices.updateBusinessProperty(property)).isNotNull();
		Assert.assertThat(propertyServices.updateBusinessProperty(property).getMessage(),
				CoreMatchers.containsString("Invalid Consumer Id"));
	}

	@SuppressWarnings("deprecation")
	@Test
	@DisplayName("Test updateConsumerProerty() InvalidConsumerId2")
	public void testUpdateBusinessPropertyInvalidConsumerId2() {
		when(propertyCategoryRepository.findByPropertyTypeAndBuildingType(anyString(), anyString()))
				.thenReturn(propertyCategory1);
		when(consumerRepo.existsById(anyLong())).thenReturn(true);
		when(businessRepo.existsByConsumerId(anyLong())).thenReturn(false);
		assertThat(propertyServices.updateBusinessProperty(property)).isNotNull();
		Assert.assertThat(propertyServices.updateBusinessProperty(property).getMessage(),
				CoreMatchers.containsString("Invalid Consumer Id"));
	}

	@SuppressWarnings("deprecation")
	@Test
	@DisplayName("Test updateConsumerProerty() InvalidBusinessID")
	public void testUpdateBusinessPropertyInvalidBusinessId() {
		when(propertyCategoryRepository.findByPropertyTypeAndBuildingType(anyString(), anyString()))
				.thenReturn(propertyCategory1);
		when(consumerRepo.existsById(anyLong())).thenReturn(true);
		when(businessRepo.existsByConsumerId(anyLong())).thenReturn(true);
		when(businessRepo.existsById(anyLong())).thenReturn(false);
		assertThat(propertyServices.updateBusinessProperty(property)).isNotNull();
		Assert.assertThat(propertyServices.updateBusinessProperty(property).getMessage(),
				CoreMatchers.containsString("Invalid Business Id"));
	}

	@SuppressWarnings("deprecation")
	@Test
	@DisplayName("Test updateConsumerProerty() Invalid Property Id")
	public void testUpdateBusinessPropertyInvalidPropertyId() {
		when(propertyCategoryRepository.findByPropertyTypeAndBuildingType(anyString(), anyString()))
				.thenReturn(propertyCategory1);
		when(consumerRepo.existsById(anyLong())).thenReturn(true);
		when(businessRepo.existsByConsumerId(anyLong())).thenReturn(true);
		when(businessRepo.existsById(anyLong())).thenReturn(true);
		assertThat(propertyServices.updateBusinessProperty(property)).isNotNull();
		Assert.assertThat(propertyServices.updateBusinessProperty(property).getMessage(),
				CoreMatchers.containsString("Invalid Property Id"));
	}

	

	@SuppressWarnings("deprecation")
	@Test
	@DisplayName("Test updateBusinessProperty() Proerty value calculation return Success")
	public void testUpdateBusinessPropertySuccessInCalculation() {
		when(propertyCategoryRepository.findByPropertyTypeAndBuildingType(anyString(), anyString()))
				.thenReturn(propertyCategory1);
		when(consumerRepo.existsById(anyLong())).thenReturn(true);
		when(businessRepo.existsByConsumerId(anyLong())).thenReturn(true);
		when(consumerRepo.existsById(anyLong())).thenReturn(true);
		when(businessRepo.existsById(anyLong())).thenReturn(true);
		when(propertyRepository.existsById(anyLong())).thenReturn(true);
		when(propertyRepository.findByConsumerId(anyLong())).thenReturn(property);
		when(propertyRepository.save(any(Property.class))).thenReturn(property);
		when(propertyMasterRepository.findIndexValue(anyInt())).thenReturn(propertyMaster);
		assertThat(propertyServices.updateBusinessProperty(property)).isOfAnyClassIn(MessageResponse.class);
		Assert.assertThat(propertyServices.updateBusinessProperty(property).getMessage(),
				CoreMatchers.containsString("Property details updated succesfully!"));

	}

	@Test
	@DisplayName("Test viewBusinessPropertyByPropertyNotFoundException() return Failure")
	public void testViewConsumerBusinessByIdNotExistsByIdExceptionFailure() throws Exception {
		when(propertyRepository.existsByConsumerId(11L)).thenReturn(false);
		assertThrows(PropertyIdNotFoundException.class, () -> {
			propertyServices.viewBusinessProperty(11L);
		});
	}

	@Test
	@DisplayName("Test viewBusinessProperty() return Success")
	public void testviewBusinessPropertyfindByConsumerId() throws Exception {
		when(propertyRepository.existsByConsumerId(11L)).thenReturn(true);
		when(propertyRepository.findByConsumerId(11L)).thenReturn(property);
		assertThat(propertyServices.viewBusinessProperty(11L)).isNotNull();
	}

	
//	@SuppressWarnings("deprecation")
//	@Test
//	@DisplayName("Test updateConsumerBusiness() Proerty value calculation NullPoiterException")
//	public void testUpdateConsumerPropertyErrorInCalculationNullPoiterException() {
//		when(propertyCategoryRepository.findByPropertyTypeAndBuildingType(anyString(), anyString()))
//				.thenReturn(propertyCategory1);
//		when(consumerRepo.existsById(anyLong())).thenReturn(true);
//		when(businessRepo.existsByConsumerId(anyLong())).thenReturn(true);
//		when(consumerRepo.existsById(anyLong())).thenReturn(true);
//		when(businessRepo.existsById(anyLong())).thenReturn(true);
//		when(propertyRepository.existsById(anyLong())).thenReturn(true);
//		when(propertyRepository.findByConsumerId(anyLong())).thenReturn(property);
//		when(propertyRepository.save(any(Property.class))).thenReturn(property);
//		when(propertyMasterRepository.findIndexValue(0)).thenReturn(propertyMaster);
//		Assert.assertThat(propertyServices.updateBusinessProperty(property3).getMessage(),
//				CoreMatchers.containsString("Property details updated succesfully!"));
//
//	}
	
}
