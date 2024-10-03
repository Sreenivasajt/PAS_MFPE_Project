package com.cts.pas.policy.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ConsumerPolicy {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private Long consumerid;

	private Long policyid;

	@NotNull
	private Long businessid;

	private String paymentdetails;

	private String acceptancestatus;

	@NotBlank
	private String policystatus;

	private String effectivedate;

	private String covered_sum;

	private String duration;

	@NotBlank
	private String acceptedquote;

	public ConsumerPolicy(Long policyid, @NotNull Long consumerid, @NotNull Long businessid,
			@NotBlank @Size(max = 10) String policystatus, @NotBlank @Size(max = 15) String acceptedquote) {
		super();
		this.policyid = policyid;
		this.consumerid = consumerid;
		this.businessid = businessid;
		this.policystatus = policystatus;
		this.acceptedquote = acceptedquote;
	}

}
