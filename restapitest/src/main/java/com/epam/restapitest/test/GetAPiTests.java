package com.epam.restapitest.test;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.epam.restapitest.api.RESTManager;

public class GetAPiTests {

	private static final String URL = "http://swapi.co/api";

	private static RESTManager executor;

	@BeforeClass
	public static void setUp() {
		// RestManager object to execute api calls
		executor = new RESTManager(URL);
	}

	
	@Test // Test GET All
	public void testGetAll() {

		executor.get("/planets")
				.expectCode(200)
				.expectMessage("OK")
				.expectHeader("Content-Type", "application/json")  //validate content type
				.expectInBody("\"count\":61")		// validate total elements
				.expectInBody("\"next\":\"http://swapi.co/api/planets/?page=2\"") // validate pagination is available
				.expectInBody("\"previous\":null"); 	//validate for the first call there is no previous page

	}
	

		
	@Test // Test GET All with pagination
	public void testGetAllWithPagination() {
		
		executor.get("/planets/?page=3")
				.expectCode(200)
				.expectMessage("OK")
				.expectHeader("Content-Type", "application/json")  //validate content type
				.expectInBody("\"count\":61")		// validate total elements
				.expectInBody("\"next\":\"http://swapi.co/api/planets/?page=4\"") // validate next page
				.expectInBody("\"previous\":\"http://swapi.co/api/planets/?page=2\""); //validate previous page

	}
	
	@Test // Test GET All for last page
	public void testGetAllWithLastPage() {

		executor.get("/planets/?page=7")
				.expectCode(200)
				.expectMessage("OK")
				.expectHeader("Content-Type", "application/json")  //validate content type
				.expectInBody("\"count\":61")		// validate total elements
				.expectInBody("\"next\":null") // validate next page
				.expectInBody("\"previous\":\"http://swapi.co/api/planets/?page=6\""); //validate previous page

	}
	
	@Test // Test GET All for unavailable page
	public void testGetAllforUnAvailablePage() {

		executor.get("/planets/?page=8")		//we know only 7 pages are present
				.expectCode(404)
				.expectMessage("Not Found");
				
	}
	
	
	@Test // Test GET for a specific item by ID and validate all details
	public void testGetByIdAndValidateDetails() {
		
		executor.get("/planets/2")
				.expectCode(200)
				.expectMessage("OK")
				.expectHeader("Content-Type", "application/json")
				.expectInBody("\"name\":\"Alderaan\"")  // validate specific details
				.expectInBody("\"rotation_period\":\"24\"")
				.expectInBody("\"orbital_period\":\"364\"")
				.expectInBody("\"diameter\":\"12500\"")
				.expectInBody("\"population\":\"2000000000\"")
				.expectInBody("\"url\":\"http://swapi.co/api/planets/2/\"");
		
	}

	
	@Test // Test GET for unavailable URI
	public void testGetForUnAvailableURI() {

		executor.get("/planets/200")
				.expectCode(404)
				.expectMessage("Not Found");

	}
}
