package com.zf.study.auth.scso.auth.client;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class StudyClient implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2508783804956240979L;

	private String clientName;

	private String clientId;

	private String clientSecret;

	private List<String> scope = new ArrayList<String>();

	private List<String> resourceIds = new ArrayList<String>();

	private List<String> authorizedGrantTypes = new ArrayList<String>();

	private List<String> authorities = new ArrayList<String>();

	/**
	 * 对象值校验
	 * 
	 * @return
	 */
	public boolean validate() {
		if (clientId == null || "".equals(clientId.trim())) {
			return false;
		} else {
			this.clientId = clientId.trim();
		}

		if (clientSecret == null || "".equals(clientSecret.trim())) {
			return false;
		}else {
			this.clientId = clientId.trim();
		}

		if (resourceIds == null || resourceIds.size() == 0) {
			return false;
		}

		if (authorizedGrantTypes == null || authorizedGrantTypes.size() == 0) {
			return false;
		}

		if (authorities == null || authorities.size() == 0) {
			return false;
		}
		return true;
	}
}
