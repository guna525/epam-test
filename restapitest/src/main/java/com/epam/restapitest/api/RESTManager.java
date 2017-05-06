package com.epam.restapitest.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RESTManager {


	private static Logger LOGGER = Logger.getLogger(RESTManager.class.getName());

	private String url;

	public RESTManager(String url) {
		this.url = url;
	}

	public RESTValidator get(String path) {
		return get(path, null);
	}

	/**
	 * Executes GET request and returns response as a json.
	 * 
	 * @param path
	 * @param headers
	 * @return
	 */
	public RESTValidator get(String path, HashMap<String, String> headers) {

		RESTResponse restResponse = new RESTResponse();
		StringBuffer responseString = new StringBuffer();

		HttpURLConnection connection = null;

		try {
			URL u = new URL(url + path);
			LOGGER.log(Level.INFO, "Sending Get Request to  : "+ url+path);
			connection = (HttpURLConnection) u.openConnection();
			connection
					.setRequestProperty(
							"User-Agent",
							"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
			connection.setRequestProperty("Accept", "application/json");
			connection.setRequestMethod("GET");
			connection.setDoOutput(true);

			// Get response body from the response stream.
			BufferedReader rd = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			String line = "";
			while ((line = rd.readLine()) != null) {
				responseString.append(line);
			}
			LOGGER.log(Level.INFO, "Response Body : "+ responseString.toString());
			// create response object
			restResponse.setResponseBody(responseString.toString());
			restResponse.setResponseCode(connection.getResponseCode());
			restResponse.setResponseMessage(connection.getResponseMessage());
			
			Map<String, List<String>> map = connection.getHeaderFields();
			for (String key : map.keySet()) {
				String s = String.join(",", map.get(key));
				restResponse.setHeader(key, s);
			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
			LOGGER.log(Level.SEVERE, "Exception in Sending GET Request");
		} catch (IOException e) {
			restResponse.setResponseCode(404);
			restResponse.setResponseMessage("Not Found");
		}

		return new RESTValidator(restResponse);
	}

}