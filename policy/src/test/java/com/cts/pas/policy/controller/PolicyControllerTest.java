package com.cts.pas.policy.controller;

import static org.assertj.core.api.Assertions.assertThat;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.cts.pas.policy.clients.AuthenticationClient;
import com.cts.pas.policy.dto.ConsumerBusinessViewResponse;
import com.cts.pas.policy.dto.Property;
import com.cts.pas.policy.entities.ConsumerPolicy;
import com.cts.pas.policy.entities.PolicyMaster;
import com.cts.pas.policy.exception.AuthorizationException;
import com.cts.pas.policy.request.CreatePolicyRequest;
import com.cts.pas.policy.request.IssuePolicyRequest;
import com.cts.pas.policy.response.AuthResponse;
import com.cts.pas.policy.response.MessageResponse;
import com.cts.pas.policy.service.PolicyService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@WebMvcTest(value = PolicyController.class)
public class PolicyControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private AuthenticationClient authenticationClient;

	@MockBean
	private PolicyService policyService;

	private AuthResponse authOK;
	private AuthResponse authFailed;
	private CreatePolicyRequest createPolicyRequest1;
	private MessageResponse createPolicyRequestMessageResponse1;
	private IssuePolicyRequest issuePolicyRequest1;
	private ConsumerPolicy consumerPolicy1;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
		authOK = new AuthResponse("abc", "abc", true);
		authFailed = new AuthResponse(null, null, false);
		createPolicyRequest1 = new CreatePolicyRequest(10L, "3200 INR");
		createPolicyRequestMessageResponse1 = new MessageResponse("Policy Created with id : " + 10 + " . ThankYou",
				HttpStatus.OK);
		issuePolicyRequest1 = new IssuePolicyRequest(11L, "ok", "ok");
	}

	@Test
	@DisplayName("Test Authorising client")
	void testClientNotNull() {
		assertThat(authenticationClient).isNotNull();
	}

	@Test
	@DisplayName("Test Mock MVC client")
	void testMockMvcNotNull() {
		assertThat(mockMvc).isNotNull();
	}

	@Test
	@DisplayName("Test PolicyService client")
	void testServiceNotNull() {
		assertThat(policyService).isNotNull();
	}

	@Test
	@DisplayName("Test createPolicy() with valid token ")
	void testCreatePolicyWithValidToken() throws Exception {
		when(authenticationClient.authorizeTheRequest("@uthoriz@tionToken123"))
				.thenReturn(new ResponseEntity<AuthResponse>(authOK, HttpStatus.OK));
		when(policyService.createPolicy(any(CreatePolicyRequest.class), anyString()))
				.thenReturn(createPolicyRequestMessageResponse1);

		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		String jsonString = mapper.writeValueAsString(createPolicyRequest1);
		this.mockMvc
				.perform(MockMvcRequestBuilders.post("/createpolicy").header("Authorization", "@uthoriz@tionToken123")
						.contentType(MediaType.APPLICATION_JSON).content(jsonString))
				.andExpect(status().isOk());

		verify(policyService, times(1)).createPolicy(any(CreatePolicyRequest.class), any());
	}

	@Test
	@DisplayName("Test createPolicy() with Invalid token ")
	void testCreatePolicyWithInValidToken() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		String jsonString = mapper.writeValueAsString(createPolicyRequest1);
		this.mockMvc
				.perform(MockMvcRequestBuilders.post("/createpolicy").header("Authorization", "wrongtoken")
						.contentType(MediaType.APPLICATION_JSON).content(jsonString))
				.andExpect(status().isUnauthorized());

		verify(policyService, times(0)).createPolicy(any(CreatePolicyRequest.class), any());
	}

	@Test
	@DisplayName("Test issuePolicy() with valid token ")
	void testIssuePolicyWithValidToken() throws Exception {
		when(authenticationClient.authorizeTheRequest("@uthoriz@tionToken123"))
				.thenReturn(new ResponseEntity<AuthResponse>(authOK, HttpStatus.OK));

		when(policyService.issuePolicy(any(IssuePolicyRequest.class))).thenReturn(createPolicyRequestMessageResponse1);

		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		String jsonString = mapper.writeValueAsString(issuePolicyRequest1);
		this.mockMvc
				.perform(MockMvcRequestBuilders.post("/issuepolicy").header("Authorization", "@uthoriz@tionToken123")
						.contentType(MediaType.APPLICATION_JSON).content(jsonString))
				.andExpect(status().isOk());

		verify(policyService, times(1)).issuePolicy(any(IssuePolicyRequest.class));
	}

	@Test
	@DisplayName("Test issuePolicy() with Invalid token ")
	void testIssuePolicyWithInValidToken() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		String jsonString = mapper.writeValueAsString(issuePolicyRequest1);
		this.mockMvc
				.perform(MockMvcRequestBuilders.post("/issuepolicy").header("Authorization", "@uthoriz@tionToken123")
						.contentType(MediaType.APPLICATION_JSON).content(jsonString))
				.andExpect(status().isUnauthorized());

		verify(policyService, times(0)).issuePolicy(any(IssuePolicyRequest.class));
	}

	@Test
	@DisplayName("Test issuePolicy() with valid token ")
	void testViewPolicyWithValidToken() throws Exception {
		when(authenticationClient.authorizeTheRequest("@uthoriz@tionToken123"))
				.thenReturn(new ResponseEntity<AuthResponse>(authOK, HttpStatus.OK));

		when(policyService.viewPolicy(anyLong())).thenReturn(consumerPolicy1);

		this.mockMvc.perform(
				MockMvcRequestBuilders.get("/viewpolicy?consumerId=1").header("Authorization", "@uthoriz@tionToken123"))
				.andExpect(status().isOk());

		verify(policyService, times(1)).viewPolicy(anyLong());
	}

	@Test
	@DisplayName("Test issuePolicy() with Invalid token ")
	void testViewPolicyWithInValidToken() throws Exception {
		this.mockMvc
				.perform(MockMvcRequestBuilders.get("/viewpolicy?consumerId=1").header("Authorization", "wrongtoken"))
				.andExpect(status().isUnauthorized());

		verify(policyService, times(0)).viewPolicy(anyLong());
	}

	@Test
	@DisplayName("Test getQuates() with valid token ")
	void testGetQuatesWithValidToken() throws Exception {
		when(authenticationClient.authorizeTheRequest("@uthoriz@tionToken123"))
				.thenReturn(new ResponseEntity<AuthResponse>(authOK, HttpStatus.OK));

		when(policyService.getQuates(1L, 1L, "ptype")).thenReturn(new MessageResponse("32000 INR"));

		this.mockMvc.perform(MockMvcRequestBuilders.get("/getquotes?businessValue=1&propertyValue=1&propertyType=1")
				.header("Authorization", "@uthoriz@tionToken123")).andExpect(status().isOk());

		verify(policyService, times(1)).getQuates(anyLong(), anyLong(), anyString());
	}

	@Test
	@DisplayName("Test getQuates() with Invalid token ")
	void testGetQuatesWithInValidToken() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/getquotes?businessValue=1&propertyValue=1&propertyType=1")
				.header("Authorization", "wrongtoken")).andExpect(status().isUnauthorized());
		verify(policyService, times(0)).getQuates(anyLong(), anyLong(), anyString());
	}

}
