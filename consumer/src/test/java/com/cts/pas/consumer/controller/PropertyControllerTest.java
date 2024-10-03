package com.cts.pas.consumer.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.cts.pas.consumer.client.AuthenticationClient;
import com.cts.pas.consumer.enities.Property;
import com.cts.pas.consumer.request.BusinessPropertyAddRequest;
import com.cts.pas.consumer.request.ConsumerBusinessAddRequest;
import com.cts.pas.consumer.response.AuthResponse;
import com.cts.pas.consumer.response.MessageResponse;
import com.cts.pas.consumer.service.BusinessServices;
import com.cts.pas.consumer.service.PropertyServices;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@WebMvcTest(value = PropertyController.class)
public class PropertyControllerTest {
	
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private PropertyServices propertyServices;
	
	@MockBean
	private Property property;

	@MockBean
	private AuthenticationClient authenticationClient;
	
	private AuthResponse authOK;
	private AuthResponse authFailed;
	private MessageResponse resOk;
	private BusinessPropertyAddRequest businessPropertyAddRequest;
	
	@BeforeEach
	public void setup() {
		authOK = new AuthResponse("abc", "abc", true);
		authFailed = new AuthResponse(null, null, false);
		resOk = new MessageResponse("success", HttpStatus.OK);
		businessPropertyAddRequest = new BusinessPropertyAddRequest(1L, 2L, "AB", "BC", "CD", "DE", 3L, 4L, 5L, 6L, 7L);
		property = new Property(1L, 2L, 3L, "AB", "BC", "CD", "DE", 2L, 3L, 4L, 5L, 6L);
	}
	
	@Test
	@DisplayName("Test mock MVC Client")
	void testmockMvcNotNull() {
		assertThat(mockMvc).isNotNull();
	}

	@Test
	@DisplayName("Test BusinessServices not null")
	void testBusinessServicescNotNull() {
		assertThat(propertyServices).isNotNull();
	}

	@Test
	@DisplayName("Test AuthenticationClient not null")
	void testAuthenticationClientNotNull() {
		assertThat(authenticationClient).isNotNull();
	}
	
	@Test
	@DisplayName("Test createBusinessProperty() with valid token")
	void testCreateBusinessPropertyWithValidToken() throws Exception {
		when(authenticationClient.authorizeTheRequest("@uthoriz@tionToken123"))
				.thenReturn(new ResponseEntity<AuthResponse>(authOK, HttpStatus.OK));
		when(propertyServices.createBusinessProperty(any())).thenReturn(resOk);
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		String jsonString = mapper.writeValueAsString(businessPropertyAddRequest);
		ResultActions result = this.mockMvc.perform(MockMvcRequestBuilders.post("/property/createbusinessproperty")
				.header("Authorization", "@uthoriz@tionToken123").contentType(MediaType.APPLICATION_JSON)
				.content(jsonString)).andExpect(status().isOk());
		verify(propertyServices, times(1)).createBusinessProperty(any());
	}
	
	@Test
	@DisplayName("Test createBusinessProperty() with Invalid token")
	void testCreateBusinessPropertyWithInValidToken() throws Exception {
		when(authenticationClient.authorizeTheRequest("wrongtoken"))
				.thenReturn(new ResponseEntity<AuthResponse>(authFailed, HttpStatus.OK));
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		String jsonString = mapper.writeValueAsString(businessPropertyAddRequest);
		
		this.mockMvc.perform(MockMvcRequestBuilders.post(("/property/createbusinessproperty"))
				.header("Authorization", "wrongtoken")
				.contentType(MediaType.APPLICATION_JSON).content(jsonString))
				.andExpect(status().isUnauthorized());

		verify(propertyServices, times(0)).createBusinessProperty(any());

	}
	
	@Test
	@DisplayName("Test createBusinessProperty() throw Exception")
	void testCreateBusinessPropertyThrowException() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		String jsonString = mapper.writeValueAsString(businessPropertyAddRequest);
		
		this.mockMvc.perform(MockMvcRequestBuilders.post(("/property/createbusinessproperty"))
				.header("Authorization", "wrongtoken")
				.contentType(MediaType.APPLICATION_JSON).content(jsonString))
				.andExpect(status().isUnauthorized());

		verify(propertyServices, times(0)).createBusinessProperty(any());

	}
	
	@Test
	@DisplayName("Test updateBusinessProperty() with valid token")
	void testUpdateBusinessPropertyWithValidToken() throws Exception {
		when(authenticationClient.authorizeTheRequest("@uthoriz@tionToken123"))
				.thenReturn(new ResponseEntity<AuthResponse>(authOK, HttpStatus.OK));
		when(propertyServices.updateBusinessProperty(any())).thenReturn(resOk);
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		String jsonString = mapper.writeValueAsString(property);
		ResultActions result = this.mockMvc.perform(MockMvcRequestBuilders.post("/property/updatebusinessproperty")
				.header("Authorization", "@uthoriz@tionToken123").contentType(MediaType.APPLICATION_JSON)
				.content(jsonString)).andExpect(status().isOk());
		verify(propertyServices, times(1)).updateBusinessProperty(any());
	}
	
	@Test
	@DisplayName("Test updateBusinessProperty() with Invalid token")
	void testUpdateBusinessPropertyWithInValidToken() throws Exception {
		when(authenticationClient.authorizeTheRequest("wrongtoken"))
				.thenReturn(new ResponseEntity<AuthResponse>(authFailed, HttpStatus.OK));
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		String jsonString = mapper.writeValueAsString(property);
		
		this.mockMvc.perform(MockMvcRequestBuilders.post(("/property/updatebusinessproperty"))
				.header("Authorization", "wrongtoken")
				.contentType(MediaType.APPLICATION_JSON).content(jsonString))
				.andExpect(status().isUnauthorized());

		verify(propertyServices, times(0)).createBusinessProperty(any());

	}
	
	@Test
	@DisplayName("Test viewConsumerBusinessById() with valid token")
	void testViewConsumerBusinessByIdWithValidToken() throws Exception {
		when(authenticationClient.authorizeTheRequest("@uthoriz@tionToken123"))
				.thenReturn(new ResponseEntity<AuthResponse>(authOK, HttpStatus.OK));
		when(propertyServices.viewBusinessProperty(11L)).thenReturn(property);
		ResultActions result = this.mockMvc.perform(MockMvcRequestBuilders.get("/property/viewbusinessproperty/11")
				.header("Authorization", "@uthoriz@tionToken123")).andExpect(status().isOk());
		verify(propertyServices, times(1)).viewBusinessProperty(any());
	}

	@Test
	@DisplayName("Test viewBusinessProperty() with Invalid token")
	void testViewBusinessPropertyWithInValidToken() throws Exception {
		when(authenticationClient.authorizeTheRequest("wrongtoken"))
				.thenReturn(new ResponseEntity<AuthResponse>(authFailed, HttpStatus.OK));
		this.mockMvc.perform(MockMvcRequestBuilders.get("/property/viewbusinessproperty/100")
				.header("Authorization", "wrongtoken")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isUnauthorized());

		verify(propertyServices, times(0)).viewBusinessProperty(any());

	}
	

}
