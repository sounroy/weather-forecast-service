package com.apple.weatherforecastservice.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;

/**
 * Response status
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseStatus implements Serializable {
	
	public ResponseStatus() {
	}
	
	public ResponseStatus(int code, String errorMessage) {
		this.code = code;
		this.errorMessage = errorMessage;
	}

	private static final long serialVersionUID = 1L;

	@JsonProperty("code")
	//@ApiModelProperty(required = true)
	public int code = 2000;
	
	@JsonProperty("type")
	public String type;
	
	@JsonProperty("errorMessage")
	public String errorMessage;

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

}
