package com.cts.pas.consumer.enities;

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

@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Business {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private Long consumerId;

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
	private Long businessvalue;

	@NotNull
	private Long businessage;

	public Business(@NotNull Long consumerId, @NotBlank String businesscategory, @NotBlank String businesstype,
			@NotNull Long capitalInvested, @NotNull Long businessturnover, @NotNull Long totalemployees,
			@NotNull Long businessvalue, @NotNull Long businessage) {
		super();
		this.consumerId = consumerId;
		this.businesscategory = businesscategory;
		this.businesstype = businesstype;
		this.capitalInvested = capitalInvested;
		this.businessturnover = businessturnover;
		this.totalemployees = totalemployees;
		this.businessvalue = businessvalue;
		this.businessage = businessage;
	}

}
