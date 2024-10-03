package com.cts.pas.quotes.entities;

import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Quotes {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	

	@NotBlank(message = "Businesss value is mandatory")
	private long businessValue;

	@NotBlank(message = "Property value is mandatory")
	private long propertyValue;

	@NotNull(message = "Property Type is mandatory")
	private String propertyType;

	@NotNull(message = "Quote is mandatory")
	private String quote;

}
