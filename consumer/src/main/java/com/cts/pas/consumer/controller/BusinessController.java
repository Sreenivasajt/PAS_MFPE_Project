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
import com.cts.pas.consumer.exceptions.AuthorizationException;
import com.cts.pas.consumer.request.ConsumerBusinessAddRequest;
import com.cts.pas.consumer.request.ConsumerBusinessUpdateRequest;
import com.cts.pas.consumer.response.AuthResponse;
import com.cts.pas.consumer.response.ConsumerBusinessViewResponse;
import com.cts.pas.consumer.response.MessageResponse;
import com.cts.pas.consumer.service.BusinessServices;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/business")
@CrossOrigin(origins = "*")
@Slf4j
public class BusinessController {

	@Autowired
	private BusinessServices businessServices;

	@Autowired
	private AuthenticationClient authenticationClient;

	/**
	 * @param token
	 * @return
	 */
	public boolean checkAuthentication(String token) {
		try {
			ResponseEntity<AuthResponse> valid = authenticationClient.authorizeTheRequest(token);
			return valid.getBody().isValid();
		} catch (Exception e) {

		}
		return false;
	}

	/**
	 * @param requestTokenHeader
	 * @param consumerBusinessAddRequest
	 * @return
	 * @throws AuthorizationException
	 */
	@PostMapping(value = "/createconsumerbusiness")
	public ResponseEntity<?> createConsumerBusiness(@RequestHeader("Authorization") String requestTokenHeader,
			@RequestBody ConsumerBusinessAddRequest consumerBusinessAddRequest) throws AuthorizationException {
		log.info("/createconsumerbusiness request ");
		log.debug(consumerBusinessAddRequest.toString());
		if (checkAuthentication(requestTokenHeader)) {
			MessageResponse res = businessServices.addConsumerBusiness(consumerBusinessAddRequest);
			return new ResponseEntity<>(res, res.getHttpStatus());
		} else {
			log.error("Invalid token");
			throw new AuthorizationException("Invalid token");
		}
	}

	/**
	 * @param requestTokenHeader
	 * @param data
	 * @return
	 * @throws AuthorizationException
	 */
	@PostMapping("/updateconsumerbusiness")
	public ResponseEntity<?> updateConsumerBusiness(
			@RequestHeader(value = "Authorization", required = true) String requestTokenHeader,
			@Valid @RequestBody ConsumerBusinessUpdateRequest data) throws AuthorizationException {
		log.info("/updateconsumerbusiness request ");
		if (checkAuthentication(requestTokenHeader)) {
			MessageResponse res = businessServices.updateConsumerBusiness(data);
			return new ResponseEntity<>(res, res.getHttpStatus());
		} else {
			log.error("Invalid token");
			throw new AuthorizationException("Invalid token");
		}
	}

	/**
	 * 
	 * @param requestTokenHeader
	 * @param consumerId
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/viewconsumerbusiness/{consumerid}")
	public ResponseEntity<?> viewConsumerBusinessById(
			@RequestHeader(value = "Authorization", required = true) String requestTokenHeader,
			@Valid @PathVariable("consumerid") Long consumerId) throws Exception {
		log.info("/viewconsumerbusiness/{} request ", consumerId);
		if (checkAuthentication(requestTokenHeader)) {
			ConsumerBusinessViewResponse res = businessServices.viewConsumerBusinessById((consumerId));
			return new ResponseEntity<>(res, HttpStatus.OK);
		} else {
			log.error("Invalid token");
			throw new AuthorizationException("Invalid token");
		}
	}

}
