package com.epam.restapitest.api;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class RESTValidator {



	private RESTResponse response;

	RESTValidator(RESTResponse response) {
		this.response = response;
	}

	public RESTValidator expectCode(int expectedCode) {
		assertEquals(
				 response.getResponseCode(),
				expectedCode, "Unexpected Response Code : " +response.getResponseCode());
		return this;
	}

	public RESTValidator expectMessage(String message) {
		assertEquals(
				 response.getResponseMessage(),
				message, "Unexpected Response Message : " +response.getResponseMessage());
		return this;
	}

	public RESTValidator expectHeader(String headerName, String headerValue) {
		assertEquals(
				response.getHeader(headerName), headerValue,"Unexpected header : " + headerName);
		return this;
	}


	public RESTValidator expectInBody(String content) {
		assertTrue(response
				.getResponseBody().contains(content), "Body does not contain string : " + content);
		return this;
	}



	public RESTResponse getResponse() {
		return response;
	}

}