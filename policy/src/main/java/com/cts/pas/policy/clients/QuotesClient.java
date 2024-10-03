package com.cts.pas.policy.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(url = "${quotes.url}", name = "quotes-service")
public interface QuotesClient {

	@GetMapping("/getquotesforpolicy/{businessValue}/{propertyValue}/{propertyType}")
	public String getQuotes(@PathVariable("businessValue") Long businessValue,
			@PathVariable("propertyValue") Long propertyValue, @PathVariable("propertyType") String propertyType);
}
