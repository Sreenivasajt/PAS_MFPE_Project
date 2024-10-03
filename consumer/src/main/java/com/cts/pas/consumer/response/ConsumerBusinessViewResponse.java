package com.cts.pas.consumer.response;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ConsumerBusinessViewResponse {
	
	@NotNull
	private Long businessId;

	@NotNull
	private Long consumerId;
	
	@NotBlank
	private String name;
	
	@NotBlank
	private String dateOfBirth;
	
	@NotBlank
	private String email;
	
	@NotBlank
	private String panNo;
	
	@NotNull
	private Long validityInDays;
	
	@NotBlank
	private String agentId;
	
	@NotBlank
	private String businesscategory;

	@NotBlank
	private String businesstype;
	
	@NotNull
	private Long capitalInvested;

	@NotNull
	private Long businessturnover;

	@NotNull
	private Long totalemployees;

	@NotNull
	private Long businessage;
	
	@NotNull
	private Long businessvalue;
	
}
