package com.cts.authorization.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.cts.authorization.config.JwtTokenUtil;
import com.cts.authorization.exception.AuthorizationException;
import com.cts.authorization.model.AuthResponse;
import com.cts.authorization.model.JwtRequest;
import com.cts.authorization.model.JwtResponse;
import com.cts.authorization.service.JwtUserDetailsService;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;

@RestController
@CrossOrigin
@Slf4j
public class JwtAuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtUserDetailsService userDetailsService;

	/**
	 * 
	 * @param authenticationRequest
	 * @return
	 * @throws AuthorizationException
	 */
	@PostMapping(value = "/login")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest)
			throws AuthorizationException {
		Authentication auth = authenticate(authenticationRequest.getId(), authenticationRequest.getPassword());
		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getId());
		System.out.println(userDetails);
		final String token = jwtTokenUtil.generateToken(userDetails);
		JwtResponse res = new JwtResponse();
		res.setAuthToken(token);
		res.setId(userDetails.getUsername());
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	/**
	 * 
	 * @param userName
	 * @param password
	 * @return
	 * @throws AuthorizationException
	 */
	private Authentication authenticate(String userName, String password) throws AuthorizationException {
		try {
			System.out.println("Inside authenticate Method==================================");
			Authentication auth = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(userName, password));
			System.out.println("Authentication Successful.....");
			System.out.println(auth.getCredentials() + "+++++++++++++++++++++++++++++++++");
			return auth;

		} catch (DisabledException e) {
			throw new AuthorizationException("USER_DISABLED");
		} catch (BadCredentialsException e) {
			e.printStackTrace();
			throw new AuthorizationException("INVALID_CREDENTIALS");
		}

	}

	/**
	 * 
	 * @param requestTokenHeader
	 * @return
	 */
	@GetMapping(value = "/validate")
	public ResponseEntity<?> authorizeTheRequest(
			@RequestHeader(value = "Authorization", required = true) String requestTokenHeader) {
		log.info("Start {}", this.getClass().getSimpleName());
		AuthResponse res = new AuthResponse();
		String jwtToken = null;
		String userName = null;
		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			jwtToken = requestTokenHeader.substring(7);

			try {
				userName = jwtTokenUtil.getUsernameFromToken(jwtToken);
				res.setId(userName);
				res.setValid(true);
				res.setName("Agent");
			} catch (IllegalArgumentException | ExpiredJwtException e) {
				res.setValid(false);
				log.info("Null Token - End {}", this.getClass().getSimpleName());
				return new ResponseEntity<>(res, HttpStatus.FORBIDDEN);
			}
		} else {
			res.setValid(false);
			return new ResponseEntity<>(res, HttpStatus.NOT_ACCEPTABLE);
		}

		log.info(" Token accepted - End {}", this.getClass().getSimpleName());
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@GetMapping("/health-check")
	public ResponseEntity<String> healthCheck() {
		return new ResponseEntity<>("auth-Ok", HttpStatus.OK);
	}

}