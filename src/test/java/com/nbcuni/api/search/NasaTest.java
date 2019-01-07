package com.nbcuni.api.search;

import com.nbcuni.api.common.TestBase;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;
import org.testng.annotations.Test;
import java.io.IOException;
import java.util.*;


public class NasaTest extends TestBase {
    /**
     * Test cases to verify nasa images API
     */

    String FILENAME = "search_api_input_params.json";

    /**
     * verifySearch    - Basic search to make sure API is working
     */
    @Test
    public void verifySearch() {
        given().
                queryParam("q","apollo 2011").
                queryParam("description","moon landing").
                queryParam("media_type","image,audio,video,123").
        when().
                get(RestAssured.baseURI).
        then().
                statusCode(200);
    }

    /**
     * verifyWithAllParameters - API is functional if all optional parameters are passed as query params
     */
    @Test
    public void verifyWithAllParameters() throws IOException{
        HashMap map = buildQueryParams(FILENAME);
        given().queryParams(map).
                when().
                get(RestAssured.baseURI).
                then().
                statusCode(200).
                body("collection.href", equalTo("https://images-api.nasa.gov/search?secondary_creator=John&keywords=Moon%20tree%2CINFINITY&year_end=2012&center=SSC&description=moon%20landing&nasa_id=SSC-20110203-S00095&title=Moon%20tree%20ceremony&q=apollo%202011&media_type=image%2Cvideo&year_start=2010&location=Houston&photographer=John&description_508=description"));
    }

    /**
     * verifyWithIncorrectURI - API is returning proper status code 404 for any unknown resources
     */
    @Test
    public void verifyWithIncorrectURI() {
        given().
                //API call with incorrect URI "invalidsearch"
                queryParam("q","apollo 2011").
                when().
                get("https://images-api.nasa.gov/invalidsearch").
                then().
                statusCode(404);
    }

    /**
     * verifyWithoutAnyParams - API is returning proper status code 400 for query param is not passed
     */
    @Test
    public void verifyWithoutAnyParams() {
        given().
                //query parameters are not passed in API call
        when().
                get(RestAssured.baseURI).
        then().
                statusCode(400).
                body("reason", equalTo("Expected 'q' text search parameter or other keywords."));
    }

    /**
     * verifyWithInvalidParam - API is returning proper status code 400 for invalid query param
     */
    @Test
    public void verifyWithInvalidParam() {
        given().
                //query parameters passed in API call, not based on the data contract
                queryParam("org","Google").
        when().
                get(RestAssured.baseURI).
        then().
                statusCode(400);
    }

    /**
     * verifyJsonResponseConformsToSchema - API response confirm to the schema definition vs a response for a valid request
     */
    @Test
    public void verifyJsonResponseConformsToSchema(){
        given().
                //q=apollo%2011%20&description=moon%20landing%20&media_type=image"
                queryParam("q","apollo 2011").
                queryParam("description","moon landing").
                queryParam("media_type","image,audio,video,123").
        when().
                get(RestAssured.baseURI).
        then().
                statusCode(200).
                assertThat().
                body(matchesJsonSchemaInClasspath("search/search_response_schema.json"));
    }
}
