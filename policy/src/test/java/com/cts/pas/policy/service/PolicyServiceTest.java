package com.cts.pas.policy.service;

import static org.assertj.core.api.Assertions.assertThat;

import static org.hamcrest.CoreMatchers.containsString;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Optional;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.cts.pas.policy.clients.ConsumerClient;
import com.cts.pas.policy.clients.QuotesClient;
import com.cts.pas.policy.dto.ConsumerBusinessViewResponse;
import com.cts.pas.policy.dto.Property;
import com.cts.pas.policy.entities.ConsumerPolicy;
import com.cts.pas.policy.entities.PolicyMaster;
import com.cts.pas.policy.exception.ConsumerNotFoundException;
import com.cts.pas.policy.exception.InApplicablePolicyException;
import com.cts.pas.policy.exception.InvalidConsumerPolicyIDException;
import com.cts.pas.policy.exception.InvalidPolicyException;
import com.cts.pas.policy.exception.PolicyExistsException;
import com.cts.pas.policy.exception.PropertyIdNotFoundException;
import com.cts.pas.policy.repository.ConsumerPolicyRepository;
import com.cts.pas.policy.repository.PolicyMasterRepository;
import com.cts.pas.policy.request.CreatePolicyRequest;
import com.cts.pas.policy.request.IssuePolicyRequest;
import com.cts.pas.policy.response.MessageResponse;

import static org.mockito.BDDMockito.given;

@SpringBootTest
public class PolicyServiceTest {
	@MockBean
	private ConsumerPolicyRepository consumerPolicyRepository;

	@InjectMocks
	private PolicyService policyService;

	@MockBean
	private ConsumerClient consumerClient;

	@MockBean
	private CreatePolicyRequest createPolicyRequest;

	@MockBean
	private PolicyMaster policyMaster1;
	private Optional<PolicyMaster> policyMaster2;

	@MockBean
	private QuotesClient quotesClient;

	@MockBean
	private PolicyMasterRepository policyMasterRepository;

	@MockBean
	private ConsumerPolicy consumerPolicy;

	private IssuePolicyRequest issuePolicyRequest;

	private ConsumerPolicy consumerPolicy1;
	private ConsumerPolicy consumerPolicy2;
	private Optional<ConsumerPolicy> consumerPolicy3;
	private Optional<ConsumerPolicy> consumerPolicy4;
	private Property property;
	private ConsumerBusinessViewResponse consumerBusinessViewResponse;
	private PolicyMaster policyMaster;
	private MessageResponse msgQuoates1;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
		consumerPolicy1 = new ConsumerPolicy(1L, 4L, 7L, 2L, "ABC", "EFG", "Issued", "HYU", "LKJ", "IUY", "GUI");
		consumerPolicy2 = new ConsumerPolicy(2L, 7L, 7L, 2L, "ABC", "EFG", "YUI", "HYU", "LKJ", "IUY", "EID");
		consumerPolicy3 = Optional
				.ofNullable(new ConsumerPolicy(4L, 5L, 7L, 2L, "ABC", "EFG", "Issued", "POL", "LKJ", "IUY", "EID"));
		consumerPolicy4 = Optional
				.ofNullable(new ConsumerPolicy(4L, 5L, 7L, 2L, "ABC", "EFG", "NotIssued", "POL", "LKJ", "IUY", "EID"));
		issuePolicyRequest = new IssuePolicyRequest(0L, "QWE", "RTY");
		policyMaster2 = Optional
				.ofNullable(new PolicyMaster(1L, "uio", "ujn", "plm", "hgd", "jhf", 3L, 7L, "IOP", "dfg"));
		createPolicyRequest = new CreatePolicyRequest(2L, "KJH");
		property = new Property(2L, 4L, 3L, "KJH", "DFG", "KJG", "ASD", 5L, 6L, 8L, 4L, 1L);
		consumerBusinessViewResponse = new ConsumerBusinessViewResponse(2L, 9L, "rty", "SDF", "KLJ", "lSD", 8L, "OKN",
				"RFV", "UHV", 7L, 9L, 2L, 3L, 4L);
		policyMaster = new PolicyMaster(1L, "kjh", "WER", "TYU", "IJB", "ASD", 2L, 3L, "OKN", "IJN");
		msgQuoates1 = new MessageResponse("32000 INR");
	}

	@Test
	@DisplayName("Test Mock ConsumerPolicy Repository")
	void testConsumerPolicyRepositoryNotNull() {
		assertThat(consumerPolicyRepository).isNotNull();
	}

	@Test
	@DisplayName("Test createPolicy() return success")
	public void testCreatePolicy() throws Exception {
		when(consumerPolicyRepository.findAll()).thenReturn(Arrays.asList(consumerPolicy1, consumerPolicy2));
	}

	@Test
	@DisplayName("Test createPolicy() return success")
	public void testviewCreatePolicy() throws Exception {
		when(consumerClient.viewBusinessProperty(anyString(), anyLong())).thenReturn(property);
		when(consumerClient.viewConsumerBusinessById(anyString(), anyLong())).thenReturn(consumerBusinessViewResponse);
		when(consumerPolicyRepository.findByConsumerid(anyLong())).thenReturn(null);
		when(policyMasterRepository.findByBusinessValueAndPropertyValue(anyLong(), anyLong())).thenReturn(policyMaster);
		when(consumerPolicyRepository.save(any(ConsumerPolicy.class))).thenReturn(consumerPolicy);
		assertThat(policyService.createPolicy(createPolicyRequest, "token")).isNotNull();

	}

	@Test
	@DisplayName("Test createPolicy() InApplicablePolicyException")
	public void testviewCreatePolicyThrowInApplicablePolicyException() throws Exception {
		when(consumerClient.viewBusinessProperty(anyString(), anyLong())).thenReturn(property);
		when(consumerClient.viewConsumerBusinessById(anyString(), anyLong())).thenReturn(consumerBusinessViewResponse);
		when(consumerPolicyRepository.findByConsumerid(anyLong())).thenReturn(null);
		when(policyMasterRepository.findByBusinessValueAndPropertyValue(anyLong(), anyLong())).thenReturn(null);
		when(consumerPolicyRepository.save(any(ConsumerPolicy.class))).thenReturn(consumerPolicy);
		assertThrows(InApplicablePolicyException.class, () -> {
			policyService.createPolicy(createPolicyRequest, "token");
		});

	}

	@Test
	@DisplayName("Test CreatePolicy() return PropertyIdNotFoundException success")
	public void testCreatePolicyPropertyIdNotFoundException() throws Exception {
		when(consumerClient.viewBusinessProperty(anyString(), anyLong())).thenReturn(null);
		assertThrows(PropertyIdNotFoundException.class, () -> {
			policyService.createPolicy(createPolicyRequest, "token");
		});
	}

	@Test
	@DisplayName("Test CreatePolicy() return PolicyExistsException success")
	public void testCreatePolicyExistsException() throws Exception {
		when(consumerClient.viewBusinessProperty(anyString(), anyLong())).thenReturn(property);
		when(consumerClient.viewConsumerBusinessById(anyString(), anyLong())).thenReturn(consumerBusinessViewResponse);
		when(consumerPolicyRepository.findByConsumerid(anyLong())).thenReturn(consumerPolicy1);
		assertThrows(PolicyExistsException.class, () -> {
			policyService.createPolicy(createPolicyRequest, "token");
		});
	}

	@Test
	@DisplayName("Test CreatePolicy() return ConsumerNotFoundException success")
	public void testCreatePolicyConsumerNotFoundException() throws Exception {
		when(consumerClient.viewBusinessProperty(anyString(), anyLong())).thenReturn(property);
		when(consumerClient.viewConsumerBusinessById(anyString(), anyLong())).thenReturn(null);
		assertThrows(ConsumerNotFoundException.class, () -> {
			policyService.createPolicy(createPolicyRequest, "kjh");
		});
	}

	@Test
	@DisplayName("Test viewPolicy() return success")
	public void testViewpolicyFindByConsumeridsucess() throws Exception {
		when(consumerPolicyRepository.findByConsumerid(anyLong())).thenReturn(consumerPolicy1);
		assertThat(policyService.viewPolicy(1L)).isNotNull();
	}

	@Test
	@DisplayName("Test viewPolicy() return failed")
	public void testViewpolicyFindByConsumeridfailed() throws Exception {
		when(consumerPolicyRepository.findByConsumerid(anyLong())).thenReturn(null);
		assertThrows(InvalidConsumerPolicyIDException.class, () -> {
			policyService.viewPolicy(1L);
		});
	}

	@Test
	@DisplayName("Test GetQuotes() return success")
	public void testGetQuotesSucess() throws Exception {
		when(quotesClient.getQuotes(anyLong(), anyLong(), anyString())).thenReturn("32000 INR");
		assertThat(policyService.getQuates(1L, 2L, "ABC")).isNotNull();
		// assertThat(policyService.getQuates(1L, 2L, "ABC")).isEqualTo(msgQuoates1);
	}

	@Test
	@DisplayName("Test issuePolicy() return Invalid Consumer")
	public void testIssuePolicyFindByIdinvalidException() throws Exception {
		assertThrows(InvalidConsumerPolicyIDException.class, () -> {
			policyService.issuePolicy(issuePolicyRequest);
		});
	}

	@Test
	@DisplayName("Test issuePolicy() return policyMaster success")
	public void testIssuePolicyFindByIdPolicyMasterSucess() throws Exception {
		when(consumerPolicyRepository.findById(anyLong())).thenReturn(consumerPolicy3);
		assertThrows(InvalidPolicyException.class, () -> {
			policyService.issuePolicy(issuePolicyRequest);
		});
	}

	@Test
	@DisplayName("Test issuePolicy() return policy exists success")
	public void testIssuePolicyFindByIdPolicyExistsSuccess() throws Exception {
		when(consumerPolicyRepository.findById(anyLong())).thenReturn(consumerPolicy3);
		when(policyMasterRepository.findById(anyLong())).thenReturn(policyMaster2);
		assertThrows(PolicyExistsException.class, () -> {
			policyService.issuePolicy(issuePolicyRequest);
		});
	}

	@Test
	@DisplayName("Test issuePolicy() return Save policy success")
	public void testIssuePolicyFindByIdConsumerPolicySavedSuccess() throws Exception {
		when(consumerPolicyRepository.findById(anyLong())).thenReturn(consumerPolicy4);
		when(policyMasterRepository.findById(anyLong())).thenReturn(policyMaster2);
		when(consumerPolicyRepository.save(consumerPolicy1)).thenReturn(consumerPolicy1);
		assertThat(policyService.issuePolicy(issuePolicyRequest)).isNotNull();
	}

}
