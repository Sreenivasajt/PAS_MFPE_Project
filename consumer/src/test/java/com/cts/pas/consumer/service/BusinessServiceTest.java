package com.cts.pas.consumer.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Optional;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import com.cts.pas.consumer.enities.Business;
import com.cts.pas.consumer.enities.BusinessCategory;
import com.cts.pas.consumer.enities.BusinessMaster;
import com.cts.pas.consumer.enities.Consumer;
import com.cts.pas.consumer.exceptions.BusinessNotFoundException;
import com.cts.pas.consumer.exceptions.ConsumerNotFoundException;
import com.cts.pas.consumer.repository.BusinessCategoryRepository;
import com.cts.pas.consumer.repository.BusinessMasterRepository;
import com.cts.pas.consumer.repository.BusinessRepository;
import com.cts.pas.consumer.repository.ConsumerRepository;
import com.cts.pas.consumer.request.ConsumerBusinessAddRequest;
import com.cts.pas.consumer.request.ConsumerBusinessUpdateRequest;
import com.cts.pas.consumer.response.MessageResponse;

@SpringBootTest
public class BusinessServiceTest {

	@InjectMocks
	private BusinessServices businessServices;

	@MockBean
	private BusinessRepository businessRepository;

	@MockBean
	private BusinessMasterRepository businessMasterRepository;

	@MockBean
	private ConsumerRepository consumerRepository;

	@MockBean
	private BusinessCategoryRepository businessCategoryRepository;

	private MessageResponse resOk;
	private ConsumerBusinessAddRequest consumerAddRequest1;
	private BusinessCategory businessCategory1;
	private BusinessCategory businessCategory2;
	private BusinessCategory businessCategoryFailed;
	private Consumer consumer;
	private Business business;
	private ConsumerBusinessUpdateRequest consumerBusinessUpdateRequest;
	private ConsumerBusinessUpdateRequest consumerBusinessUpdateRequestFailed;
	private ConsumerBusinessUpdateRequest consumerBusinessUpdateRequestHigh;
	private BusinessMaster businessMaster;
	private Optional<Consumer> consumerOptional;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
		resOk = new MessageResponse("success", HttpStatus.OK);
		consumerAddRequest1 = new ConsumerBusinessAddRequest("name", "22-22-2222", "email@gmail.com", "panNo", 32L,
				"agent", "Retailer", "E-tailer", 1200L, 1100L, 32L, 10L);

		businessCategory1 = new BusinessCategory(1L, "Retailer", "E-tailer", 0L, 0L);
		businessCategory2 = new BusinessCategory(0L, "AB", "CD", 0L, 0L);
		businessCategoryFailed = new BusinessCategory(0L, "AB", "CD", 1000L, 1000L);
		consumer = new Consumer(1L, "AB", "BC", "CD", "DE", 2L, "EF");
		business = new Business(1L, 2L, "AB", "CD", 3L, 4L, 5L, 6L, 7L);
		consumerBusinessUpdateRequest = new ConsumerBusinessUpdateRequest(1L, 12L, "name", "22-22-2222",
				"email@gmail.com", "panNo", 32L, "agent", "Retailer", "E-tailer", 1200L, 1100L, 32L, 10L);
		consumerBusinessUpdateRequestFailed = new ConsumerBusinessUpdateRequest(1L, 12L, "name", "22-22-2222",
				"email@gmail.com", "panNo", 32L, "agent", "Retailer", "E-tailer", 00L, 00L, 0L, 0L);
		consumerBusinessUpdateRequestHigh = new ConsumerBusinessUpdateRequest(1L, 12L, "name", "22-22-2222",
				"email@gmail.com", "panNo", 32L, "agent", "Retailer", "E-tailer", 1200L, 12000L, 32L, 1L);
		businessMaster = new BusinessMaster(1, 2, 3, 4);
		consumerOptional = Optional.ofNullable(new Consumer(1L, "AB", "BC", "CD", "DE", 2L, "EF"));
	}

	@Test
	@DisplayName("Test Mock businessRepository Repository")
	void testBusinessRepositoryNotNull() {
		assertThat(businessRepository).isNotNull();
	}

	@Test
	@DisplayName("Test addConsumerBusiness() return success")
	public void testAddConsumerBusiness() {
		when(businessCategoryRepository.findByBusinessCategoryAndBusinessType(anyString(), anyString()))
				.thenReturn(businessCategory1);
		when(consumerRepository.existsByEmail(anyString())).thenReturn(false);
		when(consumerRepository.existsByPanNo(anyString())).thenReturn(false);
		when(businessMasterRepository.findIndexValue(anyInt())).thenReturn(businessMaster);
		when(consumerRepository.save(any(Consumer.class))).thenReturn(consumer);

		assertThat(businessServices.addConsumerBusiness(consumerAddRequest1)).isNotNull();
	}

	@SuppressWarnings("deprecation")
	@Test
	@DisplayName("Test addConsumerBusiness() Business not eligible")
	public void testAddConsumerBusinessBusinessNotEligible() {
		when(businessCategoryRepository.findByBusinessCategoryAndBusinessType(anyString(), anyString()))
				.thenReturn(businessCategoryFailed);
		assertThat(businessServices.addConsumerBusiness(consumerAddRequest1)).isNotNull();
		assertThat(businessServices.addConsumerBusiness(consumerAddRequest1)).isOfAnyClassIn(MessageResponse.class);
		Assert.assertThat(businessServices.addConsumerBusiness(consumerAddRequest1).getMessage(),
				CoreMatchers.containsString("This Business Not Eligible!"));
	}

	@SuppressWarnings("deprecation")
	@Test
	@DisplayName("Test addConsumerBusiness() existsByEmail")
	public void testAddConsumerBusinessExistsByEmail() {
		when(businessCategoryRepository.findByBusinessCategoryAndBusinessType(anyString(), anyString()))
				.thenReturn(businessCategory1);
		when(consumerRepository.existsByEmail(anyString())).thenReturn(true);

		assertThat(businessServices.addConsumerBusiness(consumerAddRequest1)).isNotNull();
		assertThat(businessServices.addConsumerBusiness(consumerAddRequest1)).isOfAnyClassIn(MessageResponse.class);
		Assert.assertThat(businessServices.addConsumerBusiness(consumerAddRequest1).getMessage(),
				CoreMatchers.containsString("This Email Already Registered!"));
	}

	@SuppressWarnings("deprecation")
	@Test
	@DisplayName("Test addConsumerBusiness() exists Pancard details")
	public void testAddConsumerBusinessExistsByPanCardDetails() {
		when(businessCategoryRepository.findByBusinessCategoryAndBusinessType(anyString(), anyString()))
				.thenReturn(businessCategory1);
		when(consumerRepository.existsByEmail(anyString())).thenReturn(false);
		when(consumerRepository.existsByPanNo(anyString())).thenReturn(true);
		assertThat(businessServices.addConsumerBusiness(consumerAddRequest1)).isNotNull();
		assertThat(businessServices.addConsumerBusiness(consumerAddRequest1)).isOfAnyClassIn(MessageResponse.class);
		Assert.assertThat(businessServices.addConsumerBusiness(consumerAddRequest1).getMessage(),
				CoreMatchers.containsString("This PanCard Details Already Registered!"));
	}

	@Test
	@DisplayName("Test updateConsumerBusiness() return success")
	public void testUpdateConsumerBusiness() {
		when(businessCategoryRepository.findByBusinessCategoryAndBusinessType(anyString(), anyString()))
				.thenReturn(businessCategory1);
		when(consumerRepository.existsById(anyLong())).thenReturn(true);
		when(businessRepository.existsByConsumerId(anyLong())).thenReturn(true);
		when(consumerRepository.existsById(anyLong())).thenReturn(true);
		when(businessRepository.existsById(anyLong())).thenReturn(true);
		when(consumerRepository.findById(anyLong())).thenReturn(consumerOptional);
		when(businessRepository.findByConsumerId(anyLong())).thenReturn(business);
		when(consumerRepository.save(any(Consumer.class))).thenReturn(consumer);
		when(businessMasterRepository.findIndexValue(anyInt())).thenReturn(businessMaster);
		assertThat(businessServices.updateConsumerBusiness(consumerBusinessUpdateRequest)).isNotNull();
	}

	@SuppressWarnings("deprecation")
	@Test
	@DisplayName("Test updateConsumerBusiness() InvalidBusinessID")
	public void testUpdateConsumerBusinessInvalidBusinessId() {
		when(businessCategoryRepository.findByBusinessCategoryAndBusinessType(anyString(), anyString()))
				.thenReturn(businessCategory1);
		when(consumerRepository.existsById(anyLong())).thenReturn(true);
		when(businessRepository.existsByConsumerId(anyLong())).thenReturn(true);
		when(businessRepository.existsById(anyLong())).thenReturn(false);
		assertThat(businessServices.updateConsumerBusiness(consumerBusinessUpdateRequest)).isNotNull();
		Assert.assertThat(businessServices.updateConsumerBusiness(consumerBusinessUpdateRequest).getMessage(),
				CoreMatchers.containsString("Invalid Business Id"));
	}

	@SuppressWarnings("deprecation")
	@Test
	@DisplayName("Test updateConsumerBusiness() InvalidConsumerId2")
	public void testUpdateConsumerBusinessInvalidConsumerId2() {
		when(businessCategoryRepository.findByBusinessCategoryAndBusinessType(anyString(), anyString()))
				.thenReturn(businessCategory1);
		when(consumerRepository.existsById(anyLong())).thenReturn(true);
		when(businessRepository.existsByConsumerId(anyLong())).thenReturn(false);
		assertThat(businessServices.updateConsumerBusiness(consumerBusinessUpdateRequest)).isNotNull();
		Assert.assertThat(businessServices.updateConsumerBusiness(consumerBusinessUpdateRequest).getMessage(),
				CoreMatchers.containsString("Invalid Cusumer Id"));
	}

	@SuppressWarnings("deprecation")
	@Test
	@DisplayName("Test updateConsumerBusiness() InvalidConsumerId")
	public void testUpdateConsumerBusinessInvalidConsumerId() {
		when(businessCategoryRepository.findByBusinessCategoryAndBusinessType(anyString(), anyString()))
				.thenReturn(businessCategory1);
		when(consumerRepository.existsById(anyLong())).thenReturn(false);

		assertThat(businessServices.updateConsumerBusiness(consumerBusinessUpdateRequest)).isNotNull();
		Assert.assertThat(businessServices.updateConsumerBusiness(consumerBusinessUpdateRequest).getMessage(),
				CoreMatchers.containsString("Invalid Cusumer Id"));
	}

	@Test
	@DisplayName("Test updateConsumerBusiness() Business value calculation error")
	public void testUpdateConsumerBusinessErrorInCalculation() {
		when(businessCategoryRepository.findByBusinessCategoryAndBusinessType(anyString(), anyString()))
				.thenReturn(businessCategory1);
		when(consumerRepository.existsById(anyLong())).thenReturn(true);
		when(businessRepository.existsByConsumerId(anyLong())).thenReturn(true);
		when(consumerRepository.existsById(anyLong())).thenReturn(true);
		when(businessRepository.existsById(anyLong())).thenReturn(true);
		when(consumerRepository.findById(anyLong())).thenReturn(consumerOptional);
		when(businessRepository.findByConsumerId(anyLong())).thenReturn(business);
		when(consumerRepository.save(any(Consumer.class))).thenReturn(consumer);
		when(businessMasterRepository.findIndexValue(anyInt())).thenReturn(businessMaster);

		assertThrows(NullPointerException.class, () -> {
			businessServices.updateConsumerBusiness(consumerBusinessUpdateRequestFailed);
		});

	}

	@Test
	@DisplayName("Test updateConsumerBusiness() Business value >= 10")
	public void testUpdateConsumerBusinessValueHigh() {
		when(businessCategoryRepository.findByBusinessCategoryAndBusinessType(anyString(), anyString()))
				.thenReturn(businessCategory1);
		when(consumerRepository.existsById(anyLong())).thenReturn(true);
		when(businessRepository.existsByConsumerId(anyLong())).thenReturn(true);
		when(consumerRepository.existsById(anyLong())).thenReturn(true);
		when(businessRepository.existsById(anyLong())).thenReturn(true);
		when(consumerRepository.findById(anyLong())).thenReturn(consumerOptional);
		when(businessRepository.findByConsumerId(anyLong())).thenReturn(business);
		when(consumerRepository.save(any(Consumer.class))).thenReturn(consumer);
		when(businessMasterRepository.findIndexValue(anyInt())).thenReturn(businessMaster);
		assertThat(businessServices.updateConsumerBusiness(consumerBusinessUpdateRequestHigh)).isNotNull();
	}

	@SuppressWarnings("deprecation")
	@Test
	@DisplayName("Test updateConsumerBusiness() business not eligible")
	public void testUpdateConsumerBusinessButBusinessNotEligible() {
		when(businessCategoryRepository.findByBusinessCategoryAndBusinessType(anyString(), anyString()))
				.thenReturn(null);
		assertThat(businessServices.updateConsumerBusiness(consumerBusinessUpdateRequest)).isNotNull();
		Assert.assertThat(businessServices.addConsumerBusiness(consumerAddRequest1).getMessage(),
				CoreMatchers.containsString("This Business Not Eligible!"));
	}

	@Test
	@DisplayName("Test viewConsumerBusinessByIdNotExistsByIdException() return Failure")
	public void testViewConsumerBusinessByIdNotExistsByIdExceptionFailure() throws Exception {
		when(consumerRepository.existsById(11L)).thenReturn(false);
		assertThrows(ConsumerNotFoundException.class, () -> {
			businessServices.viewConsumerBusinessById(11L);
		});
	}

	@Test
	@DisplayName("Test viewConsumerBusinessByIdNotExistsByConsumerIdException() return Failure")
	public void testViewConsumerBusinessByIdNotExistsByConsumerIdExceptionFailure() throws Exception {
		when(consumerRepository.existsById(11L)).thenReturn(true);
		when(businessRepository.existsByConsumerId(11L)).thenReturn(false);
		assertThrows(BusinessNotFoundException.class, () -> {
			businessServices.viewConsumerBusinessById(11L);
		});
	}

	@Test
	@DisplayName("Test viewConsumerBusinessById() return Success")
	public void testViewConsumerBusinessById() throws Exception {
		when(consumerRepository.existsById(11L)).thenReturn(true);
		when(businessRepository.existsByConsumerId(11L)).thenReturn(true);
		when(consumerRepository.findById(11L)).thenReturn(consumerOptional);
		when(businessRepository.findByConsumerId(11L)).thenReturn(business);
		assertThat(businessServices.viewConsumerBusinessById(11L)).isNotNull();
	}

}
