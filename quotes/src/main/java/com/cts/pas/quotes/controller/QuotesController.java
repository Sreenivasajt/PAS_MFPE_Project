package com.cts.pas.quotes.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


import com.cts.pas.quotes.entities.Quotes;
import com.cts.pas.quotes.service.QuoteSevice;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class QuotesController {

	@Autowired
	private QuoteSevice quoteSevice;

	/**
	 * 
	 * @param businessValue
	 * @param propertyValue
	 * @param propertyType
	 * @return
	 */
	@GetMapping("/getquotesforpolicy/{businessValue}/{propertyValue}/{propertyType}")
	public String getQuotes(@PathVariable("businessValue") Long businessValue,
			@PathVariable("propertyValue") Long propertyValue, @PathVariable("propertyType") String propertyType) {
		log.info("Start quotes for policy");
		return quoteSevice.getQuotes(businessValue, propertyValue, propertyType);
		
	}

	/**
	 * 
	 * @return
	 */
	@GetMapping("/getquotesforpolicy")
	public List<Quotes> getAllQuotese() {
		log.info("Result for all Quotes for policy");
		return quoteSevice.getAllQuotes();
	}

}
