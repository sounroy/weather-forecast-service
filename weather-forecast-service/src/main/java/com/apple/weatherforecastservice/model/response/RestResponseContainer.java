package com.apple.weatherforecastservice.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Rest response container that contains the API specific dao in it
 */
@JsonInclude(Include.NON_EMPTY)
public class RestResponseContainer implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonProperty(value = "status", required = true)
	@org.jetbrains.annotations.NotNull
	public ResponseStatus status;

	@JsonProperty(value = "data", required = true)
	@NotNull
	public Object data;

	public RestResponseContainer() {
	}

	public RestResponseContainer(ResponseStatus status, Object data) {
		this.status = status;
		this.data = data;
	}
	
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

	/**
	 * @return the status
	 */
	public ResponseStatus getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(ResponseStatus status) {
		this.status = status;
	}

	/**
	 * @return the data
	 */
	public Object getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(Object data) {
		this.data = data;
	}
}
