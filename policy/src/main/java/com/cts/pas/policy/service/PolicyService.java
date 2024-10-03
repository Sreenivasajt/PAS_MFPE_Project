package com.cts.pas.policy.service;

import java.time.Instant;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.stereotype.Service;

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

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PolicyService {

	@Autowired
	private ConsumerPolicyRepository consumerPolicyRepo;

	@Autowired
	private PolicyMasterRepository policyMasterRepo;

	@Autowired
	private QuotesClient quotesClient;

	@Autowired
	private ConsumerClient consumerClient;

	/**
	 * 
	 * @param createPolicyRequest
	 * @param requestTokenHeader
	 * @return
	 * @throws Exception feign communication to consumer microservice
	 */
	public MessageResponse createPolicy(CreatePolicyRequest createPolicyRequest, String requestTokenHeader)
			throws Exception {

		log.info("Create policy STARTED");

		Property property = consumerClient.viewBusinessProperty(requestTokenHeader,
				createPolicyRequest.getConsumerId());

		if (property == null) {
			log.info("Property Not Found in create policy");
			throw new PropertyIdNotFoundException("Property Not Found");
		}

		ConsumerBusinessViewResponse business = consumerClient.viewConsumerBusinessById(requestTokenHeader,
				createPolicyRequest.getConsumerId());

		if (business == null) {
			log.info("Consumer Not Found in create policy");
			throw new ConsumerNotFoundException("Consumer Not Found");
		}

		ConsumerPolicy policyExists = consumerPolicyRepo.findByConsumerid(createPolicyRequest.getConsumerId());
		if (policyExists != null) {
			log.info("Policy Exists! in create policy");
			throw new PolicyExistsException("Policy Exists!");
		}

		// Find the applicable policy
		PolicyMaster policy = findSuitablePolicy(business.getBusinessvalue(), property.getPropertyValue());

		ConsumerPolicy consumerPolicy = new ConsumerPolicy(policy.getId(), business.getConsumerId(),
				business.getBusinessId(), "Initiated", createPolicyRequest.getAcceptedQuotes());

		ConsumerPolicy consumerPolicySaved = consumerPolicyRepo.save(consumerPolicy);

		log.info("create policy ENDS");
		return new MessageResponse("Policy Created with id : " + consumerPolicySaved.getId() + " . ThankYou",
				HttpStatus.OK);

	}

	/**
	 * 
	 * @param issuePolicyRequest
	 * @return
	 * @throws InvalidConsumerPolicyIDException
	 * @throws InvalidPolicyException
	 * @throws PolicyExistsException
	 */
	public MessageResponse issuePolicy(@Valid IssuePolicyRequest issuePolicyRequest)
			throws InvalidConsumerPolicyIDException, InvalidPolicyException, PolicyExistsException {

		ConsumerPolicy consumerPolicy = consumerPolicyRepo.findById(issuePolicyRequest.getPolicyid())
				.orElseThrow(() -> new InvalidConsumerPolicyIDException("Invalid consumer policy ID!"));

		PolicyMaster policyMaster = policyMasterRepo.findById(consumerPolicy.getPolicyid())
				.orElseThrow(() -> new InvalidPolicyException("Invalid policy Id!"));

		if (consumerPolicy.getPolicystatus() == "Issued") {
			log.info("Policy Exists! in issue policy");
			throw new PolicyExistsException("Policy Exists!");
		}

		consumerPolicy.setPolicystatus("Issued");
		consumerPolicy.setEffectivedate(Instant.now().toString());
		consumerPolicy.setDuration(policyMaster.getTenure());
		consumerPolicy.setCovered_sum(policyMaster.getAssuredSum());
		consumerPolicy.setAcceptancestatus(issuePolicyRequest.getAcceptancestatus());
		consumerPolicy.setPaymentdetails(issuePolicyRequest.getPaymentdetails());
		ConsumerPolicy consumerPolicySaved = consumerPolicyRepo.save(consumerPolicy);

		log.info("Issue policy ENDS");
		return new MessageResponse("Policy Issued. ThankYou", HttpStatus.OK);

	}

	/**
	 * 
	 * @param consumerId
	 * @return
	 * @throws InvalidConsumerPolicyIDException
	 */
	public ConsumerPolicy viewPolicy(@Valid Long consumerId) throws InvalidConsumerPolicyIDException {
		ConsumerPolicy consumerPolicy = consumerPolicyRepo.findByConsumerid(consumerId);
		if (consumerPolicy == null) {
			log.error("Invalid consumer policy ID!");
			throw new InvalidConsumerPolicyIDException("Invalid consumer policy ID!");
		}
		log.info("Results for View policy");
		return consumerPolicy;
	}

	/**
	 * 
	 * @param businessValue
	 * @param propertyValue
	 * @param propertyType
	 * @return
	 */
	public MessageResponse getQuates(@Valid Long businessValue, Long propertyValue, String propertyType) {
		log.info("Results for  getquotes");
		return new MessageResponse(quotesClient.getQuotes(businessValue, propertyValue, propertyType), HttpStatus.OK);
	}

	/**
	 * 
	 * @param businessvalue
	 * @param propertyValue
	 * @return
	 * @throws Exception
	 */
	private PolicyMaster findSuitablePolicy(Long businessvalue, Long propertyValue) throws Exception {
		PolicyMaster policy = policyMasterRepo.findByBusinessValueAndPropertyValue(businessvalue, propertyValue);
		if (policy == null) {

			log.info("There is no  Applicable policy available! in find suitable policy");
			throw new InApplicablePolicyException("There is no  Applicable policy available!");
		}
		log.info("find suitable policy ends");
		return policy;
	}

}
