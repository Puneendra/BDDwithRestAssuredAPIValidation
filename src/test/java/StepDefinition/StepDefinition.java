package StepDefinition;

import PojoClasess.AddLocation;
import PojoClasess.LocationDetails;
import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.List;

public class StepDefinition {

    ResponseSpecification responseSpec;
    RequestSpecification reqSpec;
    AddLocation location1;
    JsonPath js;

    @Given("create api request for adding a location")
    public void creatingApiRequest() {

        RestAssured.baseURI = "https://rahulshettyacademy.com";

        location1 = new AddLocation();
        LocationDetails locationDetails = new LocationDetails();
        locationDetails.setLat(-38.383494);
        locationDetails.setLng(33.427362);
        location1.setLocation(locationDetails);
        location1.setAccuracy(50);
        location1.setName("Puneendra");
        location1.setPhone_number("7337238146");
        location1.setAddress("Puneendra from hyderabad");

        List<String> types = new ArrayList<>();
        types.add("Temple");
        types.add("Naidu street");
        location1.setTypes(types);

        location1.setWebsite("http://google.com");
        location1.setLanguage("Telugu");

        reqSpec = new RequestSpecBuilder()
                .setBaseUri("https://rahulshettyacademy.com")
                .addQueryParam("key", "qaclick123")
                .setContentType(ContentType.JSON)
                .build();

        responseSpec = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON)
                .build();
    }

    @When("Do post call with query peramaters")
    public void CallingAPI() {

        String responseResult = RestAssured.given()
                .spec(reqSpec)
                .body(location1)
                .when()
                .post("/maps/api/place/add/json")
                .then()
                .spec(responseSpec)
                .log().all()
                .extract()
                .asString();

        System.out.println("Response: " + responseResult);
        js = new JsonPath(responseResult);
    }

    @Then("Verify status as OK")
    public void verify_status_as_ok() {
        String actualStatus = js.getString("status");
        Assert.assertEquals("Status code is not correct in response", "OK", actualStatus);
    }

    @And("also verify scope as APP")
    public void also_verify_scope_as_app() {
        String actualScope = js.getString("scope");
        Assert.assertEquals("Scope value is not correct in response", "APP", actualScope);
    }
}
