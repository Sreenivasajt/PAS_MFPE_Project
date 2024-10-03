package com.cts.pas.consumer.request;

import javax.validation.constraints.NotBlank;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BusinessPropertyAddRequest {

	@NotNull
	private Long businessId;

	@NotNull
	private Long consumerId;

	@NotBlank
	private String propertyType;

	@NotBlank
	private String buildingSqft;

	@NotBlank
	private String buildingType;

	@NotBlank
	private String buildingStoreys;

	@NotNull
	private Long buildingAge;

	@NotNull
	private Long propertyValue;

	@NotNull
	private Long costoftheAsset;

	@NotNull
	private Long salvageValue;

	@NotNull
	private Long usefulLifeOfTheAsset;
}
