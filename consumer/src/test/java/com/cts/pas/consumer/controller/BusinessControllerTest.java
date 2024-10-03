package com.cts.pas.consumer.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.contentOf;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.swing.text.StringContent;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import com.cts.pas.consumer.client.AuthenticationClient;
import com.cts.pas.consumer.request.ConsumerBusinessAddRequest;
import com.cts.pas.consumer.request.ConsumerBusinessUpdateRequest;
import com.cts.pas.consumer.response.AuthResponse;
import com.cts.pas.consumer.response.ConsumerBusinessViewResponse;
import com.cts.pas.consumer.response.MessageResponse;
import com.cts.pas.consumer.service.BusinessServices;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import ch.qos.logback.core.status.Status;

@WebMvcTest(value = BusinessController.class)
public class BusinessControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private BusinessServices businessServices;

	@MockBean
	private AuthenticationClient authenticationClient;

	private AuthResponse authOK;
	private AuthResponse authFailed;
	private ConsumerBusinessAddRequest consumerAddRequest1;
	private MessageResponse resOk;
	private ConsumerBusinessUpdateRequest businessUpdateRequest;
	private ConsumerBusinessViewResponse consumerBusinessViewResponse;

	@BeforeEach
	public void setup() {
		authOK = new AuthResponse("abc", "abc", true);
		authFailed = new AuthResponse(null, null, false);
		consumerAddRequest1 = new ConsumerBusinessAddRequest("name", null, null, null, null, null, null, null, null,
				null, null, null);
		resOk = new MessageResponse("success", HttpStatus.OK);
		businessUpdateRequest = new ConsumerBusinessUpdateRequest(1L, 2L, "AB", "CD", "EF", "GH", 3L, "IJ", "KL", "MN",
				4L, 5L, 6L, 7L);
		consumerBusinessViewResponse = new ConsumerBusinessViewResponse(10L, 2L, "AB", "CD", "EF", "GH", 3L, "IJ", "KL",
				"MN", 4L, 5L, 6L, 7L, 8L);
	}

	@Test
	@DisplayName("Test mock MVC Client")
	void testmockMvcNotNull() {
		assertThat(mockMvc).isNotNull();
	}

	@Test
	@DisplayName("Test BusinessServices not null")
	void testBusinessServicescNotNull() {
		assertThat(businessServices).isNotNull();
	}

	@Test
	@DisplayName("Test AuthenticationClient not null")
	void testAuthenticationClientNotNull() {
		assertThat(authenticationClient).isNotNull();
	}

	@Test
	@DisplayName("Test createConsumerBusiness() with valid token")
	void testCreateConsumerBusinessWithValidToken() throws Exception {
		when(authenticationClient.authorizeTheRequest("@uthoriz@tionToken123"))
				.thenReturn(new ResponseEntity<AuthResponse>(authOK, HttpStatus.OK));
		when(businessServices.addConsumerBusiness(any(ConsumerBusinessAddRequest.class))).thenReturn(resOk);
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		String jsonString = mapper.writeValueAsString(consumerAddRequest1);
		ResultActions result = this.mockMvc.perform(MockMvcRequestBuilders.post("/business/createconsumerbusiness")
				.header("Authorization", "@uthoriz@tionToken123").contentType(MediaType.APPLICATION_JSON)
				.content(jsonString)).andExpect(status().isOk());
		verify(businessServices, times(1)).addConsumerBusiness(any());
	}

	@Test
	@DisplayName("Test createConsumerBusiness() with Invalid token")
	void testCreateConsumerBusinessWithInValidToken() throws Exception {
		when(authenticationClient.authorizeTheRequest("wrongtoken"))
				.thenReturn(new ResponseEntity<AuthResponse>(authFailed, HttpStatus.OK));

		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		String jsonString = mapper.writeValueAsString(consumerAddRequest1);

		this.mockMvc.perform(MockMvcRequestBuilders.post("/business/createconsumerbusiness")
				.header("Authorization", "wrongtoken").contentType(MediaType.APPLICATION_JSON).content(jsonString))
				.andExpect(status().isUnauthorized());

		verify(businessServices, times(0)).addConsumerBusiness(any());

	}

	@Test
	@DisplayName("Test createConsumerBusiness() throw AuthentiactionException")
	void testCreateConsumerBusinessAuthentiactionException() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		String jsonString = mapper.writeValueAsString(consumerAddRequest1);

		this.mockMvc.perform(MockMvcRequestBuilders.post("/business/createconsumerbusiness")
				.header("Authorization", "token")
				.contentType(MediaType.APPLICATION_JSON).content(jsonString))
				.andExpect(status().isUnauthorized());

		verify(businessServices, times(0)).addConsumerBusiness(any());

	}

	@Test
	@DisplayName("Test updateConsumerBusiness() with valid token")
	void testCreateUpdateConsumerBusinessWithValidToken() throws Exception {
		when(authenticationClient.authorizeTheRequest("@uthoriz@tionToken123"))
				.thenReturn(new ResponseEntity<AuthResponse>(authOK, HttpStatus.OK));
		when(businessServices.updateConsumerBusiness(any())).thenReturn(resOk);
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		String jsonString = mapper.writeValueAsString(businessUpdateRequest);
		ResultActions result = this.mockMvc.perform(MockMvcRequestBuilders.post("/business/updateconsumerbusiness")
				.header("Authorization", "@uthoriz@tionToken123").contentType(MediaType.APPLICATION_JSON)
				.content(jsonString)).andExpect(status().isOk());
		verify(businessServices, times(1)).updateConsumerBusiness(any());
	}

	@Test
	@DisplayName("Test updateConsumerBusiness() with Invalid token")
	void testUpdateConsumerBusinessWithInValidToken() throws Exception {
		when(authenticationClient.authorizeTheRequest("wrongtoken"))
				.thenReturn(new ResponseEntity<AuthResponse>(authFailed, HttpStatus.OK));

		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		String jsonString = mapper.writeValueAsString(businessUpdateRequest);

		this.mockMvc.perform(MockMvcRequestBuilders.post("/business/updateconsumerbusiness")
				.header("Authorization", "wrongtoken").contentType(MediaType.APPLICATION_JSON).content(jsonString))
				.andExpect(status().isUnauthorized());

		verify(businessServices, times(0)).updateConsumerBusiness(any());

	}

	@Test
	@DisplayName("Test viewConsumerBusinessById() with valid token")
	void testviewConsumerBusinessByIdWithValidToken() throws Exception {
		when(authenticationClient.authorizeTheRequest("@uthoriz@tionToken123"))
				.thenReturn(new ResponseEntity<AuthResponse>(authOK, HttpStatus.OK));
		when(businessServices.viewConsumerBusinessById(11L)).thenReturn(consumerBusinessViewResponse);
		ResultActions result = this.mockMvc.perform(MockMvcRequestBuilders.get("/business/viewconsumerbusiness/11")
				.header("Authorization", "@uthoriz@tionToken123")).andExpect(status().isOk());
		verify(businessServices, times(1)).viewConsumerBusinessById(any());
	}

	@Test
	@DisplayName("Test viewConsumerBusinessById() with Invalid token")
	void testviewConsumerBusinessByIdWithInValidToken() throws Exception {
		when(authenticationClient.authorizeTheRequest("wrongtoken"))
				.thenReturn(new ResponseEntity<AuthResponse>(authFailed, HttpStatus.OK));
		this.mockMvc
				.perform(MockMvcRequestBuilders.get("/business/viewconsumerbusiness/1000")
						.header("Authorization", "wrongtoken").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isUnauthorized());

		verify(businessServices, times(0)).viewConsumerBusinessById(any());

	}

}
