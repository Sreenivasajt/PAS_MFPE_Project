package com.cts.pas.consumer.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import com.cts.pas.consumer.response.AuthResponse;

@FeignClient(url = "${auth.url}", name = "authorization-service")
public interface AuthenticationClient {

	@GetMapping(value = "/validate")
	public ResponseEntity<AuthResponse> authorizeTheRequest(
			@RequestHeader(value = "Authorization", required = true) String requestTokenHeader);

}