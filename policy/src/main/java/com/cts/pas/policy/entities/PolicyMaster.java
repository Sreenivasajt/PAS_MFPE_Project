package com.cts.pas.policy.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PolicyMaster {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	private String pId;

	@NotBlank
	private String propertyType;

	@NotBlank
	private String consumerType;

	@NotBlank
	private String assuredSum;

	@NotBlank
	private String tenure;

	@NotNull
	private Long businessValue;

	@NotNull
	private Long propertyValue;

	@NotBlank
	private String baseLocation;

	@NotBlank
	private String type;

}
