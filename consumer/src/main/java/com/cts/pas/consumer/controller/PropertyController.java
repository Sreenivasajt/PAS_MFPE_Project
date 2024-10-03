package com.cts.pas.consumer.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.pas.consumer.client.AuthenticationClient;
import com.cts.pas.consumer.enities.Property;
import com.cts.pas.consumer.exceptions.AuthorizationException;
import com.cts.pas.consumer.request.BusinessPropertyAddRequest;
import com.cts.pas.consumer.response.AuthResponse;
import com.cts.pas.consumer.response.MessageResponse;
import com.cts.pas.consumer.service.PropertyServices;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/property")
@CrossOrigin(origins = "*")
@Slf4j
public class PropertyController {

	@Autowired
	private PropertyServices propertyServices;

	@Autowired
	private AuthenticationClient authenticationClient;

	
	public boolean checkAuthentication(String token) {
		try {
			ResponseEntity<AuthResponse> valid = authenticationClient.authorizeTheRequest(token);
			return valid.getBody().isValid();
		} catch (Exception e) {

		}
		return false;
	}

	@PostMapping("/createbusinessproperty")
	public ResponseEntity<?> createBusinessProperty(
			@RequestHeader(value = "Authorization", required = true) String requestTokenHeader,
			@Valid @RequestBody BusinessPropertyAddRequest data) throws AuthorizationException {
		log.info("/createbusinessproperty request");
		if (checkAuthentication(requestTokenHeader)) {
			MessageResponse res = propertyServices.createBusinessProperty(data);
			return new ResponseEntity<>(res, res.getHttpStatus());
		} else {
			log.error("Invalid token");
			throw new AuthorizationException("Invalid token");
		}
	}

	@PostMapping("/updatebusinessproperty")
	public ResponseEntity<?> updateBusinessProperty(
			@RequestHeader(value = "Authorization", required = true) String requestTokenHeader,
			@Valid @RequestBody Property data) throws AuthorizationException {
		log.info("/updatebusinessproperty request");
		if (checkAuthentication(requestTokenHeader)) {
			MessageResponse res = propertyServices.updateBusinessProperty(data);
			return new ResponseEntity<>(res, res.getHttpStatus());
		} else {
			log.error("Invalid token");
			throw new AuthorizationException("Invalid token");
		}
	}

	@GetMapping("/viewbusinessproperty/{consumerid}")
	public ResponseEntity<?> viewBusinessProperty(
			@RequestHeader(value = "Authorization", required = true) String requestTokenHeader,
			@Valid @PathVariable("consumerid") Long consumerId) throws Exception {
		log.info("/viewbusinessproperty/{} request", consumerId);
		if (checkAuthentication(requestTokenHeader)) {
			Property res = propertyServices.viewBusinessProperty(consumerId);
			return new ResponseEntity<>(res, HttpStatus.OK);
		} else {
			log.error("Invalid token");
			throw new AuthorizationException("Invalid token");
		}
	}

}
