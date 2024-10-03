package com.cts.pas.policy.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
public class Property {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

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