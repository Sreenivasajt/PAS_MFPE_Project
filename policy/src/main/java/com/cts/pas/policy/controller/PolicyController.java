package com.cts.pas.policy.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cts.pas.policy.clients.AuthenticationClient;
import com.cts.pas.policy.entities.ConsumerPolicy;
import com.cts.pas.policy.exception.AuthorizationException;
import com.cts.pas.policy.exception.InvalidConsumerPolicyIDException;
import com.cts.pas.policy.exception.InvalidPolicyException;
import com.cts.pas.policy.exception.PolicyExistsException;
import com.cts.pas.policy.request.CreatePolicyRequest;
import com.cts.pas.policy.request.IssuePolicyRequest;
import com.cts.pas.policy.response.AuthResponse;
import com.cts.pas.policy.response.MessageResponse;
import com.cts.pas.policy.service.PolicyService;

import lombok.extern.slf4j.Slf4j;

@RestController
@CrossOrigin(origins = "*")
@Slf4j
public class PolicyController {

	@Autowired
	private PolicyService policyService;

	@Autowired
	private AuthenticationClient authenticationClient;

	/**
	 * 
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
	 * 
	 * @param requestTokenHeader
	 * @param createPolicyRequest
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/createpolicy")
	public ResponseEntity<?> createPolicy(
			@RequestHeader(value = "Authorization", required = true) String requestTokenHeader,
			@Valid @RequestBody CreatePolicyRequest createPolicyRequest) throws Exception {
		log.info("Create policy request started");
		if (checkAuthentication(requestTokenHeader)) {
			MessageResponse res = policyService.createPolicy(createPolicyRequest, requestTokenHeader);
			return new ResponseEntity<>(res, res.getHttpStatus());
		} else {
			log.error("Invalid token");
			throw new AuthorizationException("Invalid token");
		}

	}

	/**
	 * 
	 * @param requestTokenHeader
	 * @param issuePolicyRequest
	 * @return
	 * @throws InvalidConsumerPolicyIDException
	 * @throws InvalidPolicyException
	 * @throws PolicyExistsException
	 * @throws AuthorizationException
	 */
	@PostMapping("/issuepolicy")
	public ResponseEntity<?> issuePolicy(
			@RequestHeader(value = "Authorization", required = true) String requestTokenHeader,
			@Valid @RequestBody IssuePolicyRequest issuePolicyRequest) throws InvalidConsumerPolicyIDException,
			InvalidPolicyException, PolicyExistsException, AuthorizationException {
		log.info("Start the issuePolicy");
		if (checkAuthentication(requestTokenHeader)) {
			MessageResponse res = policyService.issuePolicy(issuePolicyRequest);
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
	 * @throws InvalidConsumerPolicyIDException
	 * @throws AuthorizationException
	 */
	@GetMapping("/viewpolicy")
	public ResponseEntity<?> viewPolicy(
			@RequestHeader(value = "Authorization", required = true) String requestTokenHeader,
			@Valid @RequestParam Long consumerId) throws InvalidConsumerPolicyIDException, AuthorizationException {
		log.info("Start the viewPolicy");
		if (checkAuthentication(requestTokenHeader)) {
			ConsumerPolicy res = policyService.viewPolicy(consumerId);
			return new ResponseEntity<ConsumerPolicy>(res, HttpStatus.OK);
		} else {
			log.error("Invalid token");
			throw new AuthorizationException("Invalid token");
		}

	}

	/**
	 * 
	 * @param requestTokenHeader
	 * @param businessValue
	 * @param propertyValue
	 * @param propertyType
	 * @return
	 * @throws AuthorizationException
	 */
	@GetMapping("/getquotes")
	public ResponseEntity<?> getQuates(
			@RequestHeader(value = "Authorization", required = true) String requestTokenHeader,
			@Valid @RequestParam Long businessValue, @RequestParam Long propertyValue,
			@RequestParam String propertyType) throws AuthorizationException {
		log.info("Start the getQuotes");
		if (checkAuthentication(requestTokenHeader)) {
			MessageResponse res = policyService.getQuates(businessValue, propertyValue, propertyType);
			return new ResponseEntity<MessageResponse>(res, HttpStatus.OK);
		} else {
			log.error("Invalid token");
			throw new AuthorizationException("Invalid token");
		}

	}

}