package com.cts.pas.policy.request;

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
public class CreatePolicyRequest {

	@NotNull
	private Long consumerId;

	@NotBlank
	private String acceptedQuotes;

}
