import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import junit.framework.Assert;

public class googleMapApi_GET {

	String baseURL = "https://maps.googleapis.com" ;
	
	@Test
	void googleMapTest() 
	{
		RestAssured.baseURI = baseURL;
		
		// Request Object
		RequestSpecification httpRequest = RestAssured.given();

		// Response Object
		Response response = httpRequest.request(Method.GET, "/maps/api/place/search/xml?location=37.7836320,-122.3998260&radius=1500&key=AIzaSyBOVOFjwzXv0z2SJYrSk9gRGze8vPE9h9E");
		
		// Print response in console region
//		String responseBody=response.getBody().asString();
//		System.out.println("Response Body is: " + responseBody);
		
		// Capture all headers
		Headers allheaders = response.headers();
		System.out.println(allheaders.toString());
		
		for (Header header:allheaders)
		{
			System.out.println(header.getName()+ "\t\t" + header.getValue());
			
		}
		
		// Capture and Validate details of Content-Type Header
		String contentType = response.header("Content-Type");
		Assert.assertEquals(contentType, "application/xml; charset=UTF-8");
		
		String contentEncoding = response.header("Content-Encoding");
		Assert.assertEquals(contentEncoding, "gzip");
		
	}
}
