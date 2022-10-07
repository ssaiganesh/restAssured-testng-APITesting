import static org.testng.Assert.assertEquals;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.authentication.AuthenticationScheme;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class simpleBookAPI_GET{
	// Specify base URI
	String baseURL = "https://simple-books-api.glitch.me" ;
	
	@Test
	void getStatus() 
	{
		RestAssured.baseURI = baseURL;
		
		// Request Object
		RequestSpecification httpRequest = RestAssured.given();
		
		// Response Object
		Response response = httpRequest.request(Method.GET, "/status");
		
		// Print response in console region
		String responseBody=response.getBody().asString();
		System.out.println("Response Body is: " + responseBody);
		
	}
	
	@Test
	void getBooks()
	{
		RestAssured.baseURI = baseURL;
		
		// Request Object
		RequestSpecification httpRequest = RestAssured.given();
		
		// Response Object
		Response response = httpRequest.request(Method.GET, "/books");
		
		// Print response in console region
		String responseBody=response.getBody().asString();
		System.out.println("Response Body is: " + responseBody);
		
		int statusCode = response.getStatusCode();
		System.out.println("Status Code is: " + statusCode);
	
		Assert.assertEquals(statusCode, 200);
		
		// status line verification
		String statusLine = response.getStatusLine();
		System.out.println("Status Line: " + statusLine);
		Assert.assertEquals(statusLine, "HTTP/1.1 200 OK");
	}
	
	@Test
	void getBook()
	{
		RestAssured.baseURI = baseURL + "/books";
		
		// Request Object
		RequestSpecification httpRequest = RestAssured.given();
		
		// Response Object
		Response response = httpRequest.basePath("{bookId}").pathParam("bookId", 5).get();
		
		
		// Print response in console region
		String responseBody=response.getBody().asString();
		System.out.println("Response Body is: " + responseBody);
			
		Assert.assertEquals(response.getStatusCode(), 200);
		
		Assert.assertEquals(responseBody.contains("5"), true);
		
		JsonPath jsonpath = response.jsonPath();
		
		System.out.println(jsonpath.get("id"));
		System.out.println(jsonpath.get("name"));
		System.out.println(jsonpath.get("author"));
		System.out.println(jsonpath.get("type"));
		System.out.println(jsonpath.get("price"));
		System.out.println(jsonpath.get("current-stock"));
		System.out.println(jsonpath.get("available"));
		
		Assert.assertEquals(jsonpath.get("name"), "Untamed");
		Assert.assertEquals(jsonpath.get("author"), "Glennon Doyle");
		Assert.assertEquals(jsonpath.get("type"), "non-fiction");
		
		
		Assert.assertEquals(jsonpath.get("current-stock"), 20);
//		Assert.assertEquals(jsonpath.get("price"),"15.5"); // This does not work
		Assert.assertEquals((Float) jsonpath.get("price"),15.5 , 0.0f); 
		
		
		Assert.assertEquals(jsonpath.get("available"), true); // "true" failed
	}
	
}
