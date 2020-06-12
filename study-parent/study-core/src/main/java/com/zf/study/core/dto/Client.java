package com.zf.study.core.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class Client implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5415129544794489087L;

	// client_id
	private String client_id;

	// client_name
	private String client_name;

	// client_secret
	private String client_secret;

	// grant_type
	private String grant_type;

	// scope
	private String scope;

}
