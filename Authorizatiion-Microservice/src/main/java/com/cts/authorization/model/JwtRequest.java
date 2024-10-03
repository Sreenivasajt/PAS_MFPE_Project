package com.cts.authorization.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter 
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JwtRequest implements Serializable {

	//private static final long serialVersionUID = 5926468583005150707L;
	
	private String id;
	private String password;
	
	
}