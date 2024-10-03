package com.cts.pas.policy.clients;

import javax.validation.Valid;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import com.cts.pas.policy.dto.ConsumerBusinessViewResponse;
import com.cts.pas.policy.dto.Property;

@FeignClient(url = "${consumer.url}", name = "consumer-service")
public interface ConsumerClient {

	@GetMapping("/business/viewconsumerbusiness/{consumerid}")
	public ConsumerBusinessViewResponse viewConsumerBusinessById(
			@RequestHeader(value = "Authorization", required = true) String requestTokenHeader,
			@Valid @PathVariable("consumerid") Long consumerId) throws Exception;

	@GetMapping("/property/viewbusinessproperty/{consumerid}")
	public Property viewBusinessProperty(
			@RequestHeader(value = "Authorization", required = true) String requestTokenHeader,
			@Valid @PathVariable("consumerid") Long consumerId) throws Exception;
}
