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

public class StepDefinition
{
    ResponseSpecification responseaobj;
    RequestSpecification aobj;
    JsonPath js;

    @Given("create api request for adding a location")
    public void creatingApiRequest()
    {
        RestAssured.baseURI = "https://rahulshettyacademy.com";
        AddLocation location1 = new AddLocation();
        LocationDetails locationaobj = new LocationDetails();

        locationaobj.setLat(-38.383494);
        locationaobj.setLng(33.427362);
        location1.setLocation(locationaobj);


        location1.setAccuracy(50);
        location1.setName("Puneendra");
        location1.setPhone_number("7337238146");
        location1.setAddress("Puneendra from hyderabad");


        List<String> types = new ArrayList<>();
        types.add("Temple");
        types.add("Naidu street ");
        location1.setTypes(types);

        location1.setWebsite("http://google.com");
        location1.setLanguage("Telugu");

        System.out.println(location1);

         aobj = new RequestSpecBuilder()
                .setBaseUri("https://rahulshettyacademy.com")
                .addQueryParam("key", "qaclick123")
                .setContentType(ContentType.JSON).build();


         responseaobj = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON).build();


        String responseResult =responseaobj


                .when()
                .post("/maps/api/place/add/json")

                .then().spec(responseaobj)
                .log().all().extract().asString();
    }
    @When("Do post call with query peramaters")
    public void CallingAPI() {
        String responseResult =responseaobj


                .when()
                .post("/maps/api/place/add/json")

                .then().spec(responseaobj)
                .log().all().extract().asString();
        System.out.println("Response: " + responseResult);

         js = new JsonPath(responseResult);
    }


    @Then("Verify status as OK")
    public void verify_status_as_ok()
    {
//        String id = js.getString("id");
//        String placeid = js.getString("place_id");
//
//        System.out.println("ID: " + id);
//        System.out.println("Place ID: " + placeid);

String ActualStatus = js.getString("status");
        Assert.assertEquals(ActualStatus,"OK","Status code is not correct in respone code");

    }
    @And("also verify scope as APP")
    public void also_verify_scope_as_app() {

        String ActualScope = js.getString("scope");
        Assert.assertEquals(ActualScope,"APP","Scope is not correct in respone code");



    }
}
