package steps.stepDefinitions;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import steps.generic.UserSteps;
import java.io.IOException;
import static org.junit.Assert.assertEquals;

public class UserStepsDef extends UserSteps {
    private static final Logger logger = LogManager.getLogger(UserStepsDef.class);
    /**
     * This class defines step definitions for user-related scenarios.
     */
    private final UserSteps userSteps = new UserSteps();
    public RequestSpecification httpRequest;
    public Response response;
    /**
     * Sets up the base URL for the API endpoint and loads data required for the test.
     */
    @Given("The url address of the API endpoint is accessed to obtain the list of users")
    public void theUrlAddressOfTheAPIEndpointIsAccessedToObtainTheListOfUsers() {
        // Set the base URI for the API endpoint
        RestAssured.baseURI = "https://reqres.in/";
        // Load data required for the test
        userSteps.loadData();

    }
    /**
     * Sends a GET request to the API endpoint with the URL for obtaining the list of users.
     */
    @When("The URL of the list of users is passed in the request")
    public void theURLOfTheListOfUsersIsPassedInTheRequest() {
        // Create a request specification and send a GET request to the API endpoint
        httpRequest = RestAssured.given();
        response = httpRequest.get("https://reqres.in/api/users?page=2");
    }
    /**
     * Verifies if the received response code matches the expected status code.
     *
     * @param expectedStatusCode The expected status code to be received
     */
    @Then("The response code {int} is received")
    public void theResponseCodeIsReceived(int expectedStatusCode) {
        // Get the actual status code from the response
        int actualStatusCode = response.getStatusCode();
        // Assert that the actual status code matches the expected status code
        logger.info("Status code matches the expected value: {}. Actual status code: {}", expectedStatusCode, actualStatusCode);
        assertEquals(expectedStatusCode, actualStatusCode);
    }
    @Then("The actual JSON response matches the expected Json response")
    public void theUserResponsePageIs() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            UserResponse actualResponse = objectMapper.readValue(response.getBody().asString(), UserResponse.class);
            // Compare and print the values for the 'page', 'per_page', and 'total' fields
            int expectedPage = userResponse.getPage();
            int actualPage = actualResponse.getPage();
            assertAndPrintValues("page", expectedPage, actualPage);
            int expectedPerPage = userResponse.getPer_page();
            int actualPerPage = actualResponse.getPer_page();
            assertAndPrintValues("per_page", expectedPerPage, actualPerPage);
            int expectedTotal = userResponse.getTotal();
            int actualTotal = actualResponse.getTotal();
            assertAndPrintValues("total", expectedTotal, actualTotal);
            System.out.println();
            // Get the response body in JSON format from HTTP
            // Compare the actual and expected responses in JSON format and print them
            String contentType = response.getContentType();
            System.out.println("Type of response content: " + contentType);
            String responseBody = response.getBody().asString();
            // Compare the actual JSON response with the expected one
            JsonNode actualJson = objectMapper.readTree(responseBody);
            System.out.println("The expected Json Response:");
            System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(expectedJson));
            System.out.println("The actual Json Response:");
            System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(actualJson));
            assertEquals(expectedJson, actualJson);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
