package com.cts.pas.policy.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import com.cts.pas.policy.response.AuthResponse;

@FeignClient(url = "${auth.url}", name = "authorization-service")
public interface AuthenticationClient {
	@GetMapping(value = "/validate")
	public ResponseEntity<AuthResponse> authorizeTheRequest(
			@RequestHeader(value = "Authorization", required = true) String requestTokenHeader);
}
