package com.champ.base.response;

import java.util.Map;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GetUserPropertiesResponse extends BaseResponse {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8288112338468173135L;

	private Map<String, String> properties;

	public Map<String, String> getProperties() {
		return properties;
	}

	public void setProperties(Map<String, String> properties) {
		this.properties = properties;
	}

}
