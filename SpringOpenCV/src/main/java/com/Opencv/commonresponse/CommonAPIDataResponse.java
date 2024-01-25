package com.Opencv.commonresponse;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({ "validationMessage", "checkValidationFailed", "checkForUnAuthorized" })
public class CommonAPIDataResponse {


	//@JsonProperty("validationMessage")
	private String validationMessage;

	//@JsonProperty("checkValidationFailed")
	private boolean checkValidationFailed;

	//@JsonProperty("checkForUnAuthorized")
	private boolean checkForUnAuthorized;

	//@JsonInclude(JsonInclude.Include.NON_EMPTY)
	//@JsonProperty("message")
	private String message;
}
