package com.cts.pas.consumer.enities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
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

	public Property(@NotNull Long businessId, @NotNull Long consumerId, @NotBlank String propertyType,
			@NotBlank String buildingSqft, @NotBlank String buildingType, @NotBlank String buildingStoreys,
			@NotNull Long buildingAge, @NotNull Long propertyValue, @NotNull Long costoftheAsset,
			@NotNull Long salvageValue, @NotNull Long usefulLifeOfTheAsset) {
		super();
		this.businessId = businessId;
		this.consumerId = consumerId;
		this.propertyType = propertyType;
		this.buildingSqft = buildingSqft;
		this.buildingType = buildingType;
		this.buildingStoreys = buildingStoreys;
		this.buildingAge = buildingAge;
		this.propertyValue = propertyValue;
		this.costoftheAsset = costoftheAsset;
		this.salvageValue = salvageValue;
		this.usefulLifeOfTheAsset = usefulLifeOfTheAsset;
	}

}
