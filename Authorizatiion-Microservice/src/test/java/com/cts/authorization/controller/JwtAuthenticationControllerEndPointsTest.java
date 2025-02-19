package com.cts.authorization.controller;

import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.cts.authorization.config.JwtTokenUtil;
import com.cts.authorization.model.JwtRequest;
import com.cts.authorization.model.User;
import com.cts.authorization.service.JwtUserDetailsService;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.ExpiredJwtException;

@SpringBootTest
@AutoConfigureMockMvc
class JwtAuthenticationControllerEndPointsTest {

	@SuppressWarnings("deprecation")
	@BeforeEach
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private JwtUserDetailsService userDetailsService;

	@MockBean
	private JwtTokenUtil jwtTokenUtil;

	@MockBean
	private AuthenticationManager authenticationManager;

	@Test
	void testBadRequestGenerateToken() throws Exception {
		this.mockMvc.perform(post("/login")).andExpect(status().isBadRequest());
	}

	@Test
	void testAuthorizedGenerateToken() throws Exception {
		User user = new User(1, "admin", "pass");
		UserDetails details = new org.springframework.security.core.userdetails.User(user.getUserName(),
				user.getPassword(), new ArrayList<>());
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
				"admin", "pass");
		when(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken("admin", "pass")))
				.thenReturn(usernamePasswordAuthenticationToken);
		when(jwtTokenUtil.generateToken(details)).thenReturn("token");
		when(userDetailsService.loadUserByUsername("admin")).thenReturn(details);
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = mapper.writeValueAsString(new JwtRequest("admin", "pass"));
		this.mockMvc.perform(
				MockMvcRequestBuilders.post("/login").contentType(MediaType.APPLICATION_JSON).content(jsonString))
				.andExpect(status().isOk());
	}

	@Test
	void testBadRequest() throws Exception {
		this.mockMvc.perform(post("/login")).andExpect(status().isBadRequest());
	}

	@Test
	void testRandomUrl() throws Exception {
		this.mockMvc.perform(get("/other/url")).andExpect(status().isNotFound());
	}

	@Test
	void testExistingUserAuthenticate() throws Exception {
		User user = new User(1, "admin", "pass");
		UserDetails details = new org.springframework.security.core.userdetails.User(user.getUserName(),
				user.getPassword(), new ArrayList<>());
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
				"admin", "pass");
		when(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken("admin", "pass")))
				.thenReturn(usernamePasswordAuthenticationToken);
		when(userDetailsService.loadUserByUsername("admin")).thenReturn(details);
		when(jwtTokenUtil.getUsernameFromToken("token")).thenReturn("admin");
		when(jwtTokenUtil.generateToken(details)).thenReturn("token");
		ObjectMapper mapper = new ObjectMapper();
		mockMvc.perform(MockMvcRequestBuilders.post("/login").contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(new JwtRequest("admin", "pass")))).andExpect(status().isOk());

	}

	@Test
	void testExistingUserAuthorize() throws Exception {
		User user = new User(1, "admin", "pass");
		UserDetails details = new org.springframework.security.core.userdetails.User(user.getUserName(),
				user.getPassword(), new ArrayList<>());
		when(userDetailsService.loadUserByUsername("admin")).thenReturn(details);
		when(jwtTokenUtil.getUsernameFromToken("token")).thenReturn("admin");
		mockMvc.perform(MockMvcRequestBuilders.get("/validate").header("Authorization", "Bearer token"))
				.andExpect(status().isOk());

	}

	@Test
	void testNullTokenAuthorize() throws Exception {
		User user = new User(1, "admin", "pass");
		UserDetails details = new org.springframework.security.core.userdetails.User(user.getUserName(),
				user.getPassword(), new ArrayList<>());
		when(userDetailsService.loadUserByUsername("admin")).thenReturn(details);
		when(jwtTokenUtil.getUsernameFromToken("token")).thenReturn("admin");
		mockMvc.perform(MockMvcRequestBuilders.get("/validate").header("Authorization", "")
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().is(406));

	}

	@Test
	void testAuthorizeThrowExpiredJwtException() throws Exception {
		User user = new User(1, "admin", "pass");
		UserDetails details = new org.springframework.security.core.userdetails.User(user.getUserName(),
				user.getPassword(), new ArrayList<>());
		when(userDetailsService.loadUserByUsername("admin")).thenReturn(details);
		when(jwtTokenUtil.getUsernameFromToken("token")).thenThrow(ExpiredJwtException.class);
		mockMvc.perform(MockMvcRequestBuilders.get("/validate").header("Authorization", "Bearer token"))
				.andExpect(status().isForbidden());

	}

	@Test
	void testAuthorizeThrowIllegalArgumentException() throws Exception {
		User user = new User(1, "admin", "pass");
		UserDetails details = new org.springframework.security.core.userdetails.User(user.getUserName(),
				user.getPassword(), new ArrayList<>());
		when(userDetailsService.loadUserByUsername("admin")).thenReturn(details);
		when(jwtTokenUtil.getUsernameFromToken("token")).thenThrow(IllegalArgumentException.class);
		mockMvc.perform(MockMvcRequestBuilders.get("/validate").header("Authorization", "Bearer token"))
				.andExpect(status().isForbidden());

	}

	@Test
	void testAuthenticateThrowDisabledException() throws Exception {
		when(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken("admin", "pass")))
				.thenThrow(DisabledException.class);
		ObjectMapper mapper = new ObjectMapper();
		mockMvc.perform(MockMvcRequestBuilders.post("/login").contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(new JwtRequest("admin", "pass"))))
				.andExpect(status().isUnauthorized());

	}

	@Test
	void testAuthenticateThrowBadCredentialsException() throws Exception {
		when(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken("admin", "pass")))
				.thenThrow(BadCredentialsException.class);
		ObjectMapper mapper = new ObjectMapper();
		mockMvc.perform(MockMvcRequestBuilders.post("/login").contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(new JwtRequest("admin", "pass"))))
				.andExpect(status().isUnauthorized());

	}

	@Test
	void heatlthCheck() throws Exception {
		this.mockMvc.perform(get("/health-check")).andExpect(status().isOk());
	}

}
