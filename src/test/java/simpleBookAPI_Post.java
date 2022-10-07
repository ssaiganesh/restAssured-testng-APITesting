import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;


public class simpleBookAPI_Post extends simpleBookAPI_GET {
		
		String accessToken = null;
		String name = null;
		String email = null;
		@Test
		void register() 
		{
				RestAssured.baseURI = baseURL;
		
				// Request Object
				RequestSpecification httpRequest = RestAssured.given();
				
				// Response Object
				
				JSONObject requestParams= new JSONObject();
				
				Faker faker = new Faker();
				this.name = faker.name().fullName();
				this.email = faker.internet().emailAddress();
				
				requestParams.put("clientName", this.name );
				requestParams.put("clientEmail",this.email);
				httpRequest.header("Content-Type","application/json");
				
				httpRequest.body(requestParams.toJSONString()); // attach data to the request
				
				Response response = httpRequest.request(Method.POST, "/api-clients");
				
				// Print response in console region
//				String responseBody=response.getBody().asString();
//				System.out.println("Response Body is: " + responseBody);
				response.prettyPrint();
				
				int statusCode = response.getStatusCode();
				System.out.println("Status Code is: " + statusCode);
			
				Assert.assertEquals(statusCode, 201);
				
				this.accessToken = response.jsonPath().get("accessToken"); 
				
		}
		
		@Test (dependsOnMethods = "register")
		void order() {
			RestAssured.baseURI = baseURL;
			
			// Request Object
			RequestSpecification httpRequest = RestAssured.given();
			
			// Response Object
			
			JSONObject requestParams= new JSONObject();
			
			Faker faker = new Faker();
			String name = faker.name().fullName();
			
			requestParams.put("bookId", 5);
			requestParams.put("clientName", name );
			
			System.out.println("Access Token in order method: " + this.accessToken);
			
			httpRequest.header("Authorization", "Bearer "+this.accessToken);
			httpRequest.header("Content-Type", "application/json");
			httpRequest.body(requestParams.toJSONString()); // attach data to the request
			
			Response response = httpRequest.request(Method.POST, "/orders");
			response.prettyPrint();
			
			int statusCode = response.getStatusCode();
			System.out.println("Status Code is: " + statusCode);
		
			Assert.assertEquals(statusCode, 201);
			
		}
		
		@Test(dependsOnMethods = "register")
		void duplicateRegister()
		{
			RestAssured.baseURI = baseURL;
			
			// Request Object
			RequestSpecification httpRequest = RestAssured.given();
			
			// Response Object
			
			JSONObject requestParams= new JSONObject();
			
			Faker faker = new Faker();
			String duplicateName = this.name;
			String duplicateEmail = this.email;
			
			requestParams.put("clientName", duplicateName );
			requestParams.put("clientEmail",duplicateEmail);
			httpRequest.header("Content-Type","application/json");
			
			httpRequest.body(requestParams.toJSONString()); // attach data to the request
			
			Response response = httpRequest.request(Method.POST, "/api-clients");
			
			// Print response in console region
//			String responseBody=response.getBody().asString();
//			System.out.println("Response Body is: " + responseBody);
			response.prettyPrint();
			
			int statusCode = response.getStatusCode();
			System.out.println("Status Code is: " + statusCode);
		
			Assert.assertEquals(statusCode, 409);

		}
		
}
