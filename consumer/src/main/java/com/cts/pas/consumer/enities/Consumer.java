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
public class Consumer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
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

	public Consumer(@NotBlank String name, @NotBlank String dateOfBirth, @NotBlank String email, @NotBlank String panNo,
			@NotNull Long validityInDays, @NotBlank String agentId) {
		super();
		this.name = name;
		this.dateOfBirth = dateOfBirth;
		this.email = email;
		this.panNo = panNo;
		this.validityInDays = validityInDays;
		this.agentId = agentId;
	}

}
