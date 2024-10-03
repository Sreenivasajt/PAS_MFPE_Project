package com.cts.pas.quotes.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.pas.quotes.entities.Quotes;
import com.cts.pas.quotes.repositary.QuotesRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class QuoteSevice {

	@Autowired
	private QuotesRepository quotesRepository;

	public List<Quotes> getAllQuotes() {
		List<Quotes> quotes = quotesRepository.findAll();
		log.info("Getting all quotes");
		return quotes;
		
	}
	
	/**
	 * @param businessValue
	 * @param propertyValue
	 * @param propertyType
	 * @return
	 */

	public String getQuotes(Long businessValue, Long propertyValue, String propertyType) {
		Quotes quote = quotesRepository.findByBusinessValueAndPropertyValueAndPropertyType(businessValue, propertyValue,
				propertyType);

		if (quote == null) {
			log.info("No Quotes, Contact Insurance Provider");
			return "No Quotes, Contact Insurance Provider";
		}

		log.info("Quotes by businessValue and propertyValue and propertyType");
		return quote.getQuote();
	}
}
