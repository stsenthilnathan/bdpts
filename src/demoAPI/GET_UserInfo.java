package demoAPI;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
//import io.restassured.response.*;
import static io.restassured.RestAssured.*;
//import static org.hamcrest.Matchers.*;

public class GET_UserInfo {

	public static void main(String[] args) {
				
		RestAssured.baseURI = "http://bpdts-test-app-v2.herokuapp.com";
		String res = when().get("/users").
					 then().assertThat().statusCode(200).	
					 extract().response().asString();  // Extracts the response of this endpoint in RAW format, which is then converted to String.
				
		System.out.println(res);
				
		//Converting string response in to json format to parse & fetch the details 
		JsonPath responseJson = new JsonPath(res);
		int userId = responseJson.get("[1].id");
		int count = responseJson.getInt("array.size()");
		System.out.println("Total no. of records: " +count);
		System.out.println("Id of second user is: " +userId);
				
		System.out.println("\n\n\nFETCHING SECOND USER DETAILS \n");
		String userIdRes = given().log().all(). 
				when().get("/user/"+userId).
				then().log().all().assertThat().statusCode(200).	
				extract().response().asString();  // Extracts the response of this endpoint in RAW format, which is then converted to String.

		//Negative case
				given().log().all(). 
				when().get("/user/1001").
				then().log().all().assertThat().statusCode(404).	
				extract().response().asString();
		
				//Converting string response in to json format to parse & fetch city details		
		JsonPath userResponseJson = new JsonPath(userIdRes);
		String cityName = userResponseJson.get("city");
		System.out.println("\n\nCity name: " +cityName);
				
		System.out.println("\n\n\nFETCHING USER DETAILS BY CITY \n");
		        given().log().all(). 
				when().get("/city/"+cityName+"/users").
				then().log().all().assertThat().statusCode(200);
			
					
			}

	}

