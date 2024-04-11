package steps.generic;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import pojo.User;

import static org.junit.Assert.assertEquals;
/**
 * This class contains generic steps for user-related scenarios.
 */
public class UserSteps extends User {
   public static  UserResponse userResponse;
   public static JsonNode expectedJson;
    /**
     * Loads user data from a JSON string.
     */
    public void loadData() {
        String jsonString = "{\"page\":2,\"per_page\":6,\"total\":12,\"total_pages\":2,\"data\":[{\"id\":7,\"email\":\"michael.lawson@reqres.in\",\"first_name\":\"Michael\",\"last_name\":\"Lawson\",\"avatar\":\"https://reqres.in/img/faces/7-image.jpg\"},{\"id\":8,\"email\":\"lindsay.ferguson@reqres.in\",\"first_name\":\"Lindsay\",\"last_name\":\"Ferguson\",\"avatar\":\"https://reqres.in/img/faces/8-image.jpg\"},{\"id\":9,\"email\":\"tobias.funke@reqres.in\",\"first_name\":\"Tobias\",\"last_name\":\"Funke\",\"avatar\":\"https://reqres.in/img/faces/9-image.jpg\"},{\"id\":10,\"email\":\"byron.fields@reqres.in\",\"first_name\":\"Byron\",\"last_name\":\"Fields\",\"avatar\":\"https://reqres.in/img/faces/10-image.jpg\"},{\"id\":11,\"email\":\"george.edwards@reqres.in\",\"first_name\":\"George\",\"last_name\":\"Edwards\",\"avatar\":\"https://reqres.in/img/faces/11-image.jpg\"},{\"id\":12,\"email\":\"rachel.howell@reqres.in\",\"first_name\":\"Rachel\",\"last_name\":\"Howell\",\"avatar\":\"https://reqres.in/img/faces/12-image.jpg\"}],\"support\":{\"url\":\"https://reqres.in/#support-heading\",\"text\":\"To keep ReqRes free, contributions towards server costs are appreciated!\"}}";
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            userResponse = objectMapper.readValue(jsonString, UserResponse.class);
            expectedJson = objectMapper.readTree(jsonString);
            System.out.println("Page: " + userResponse.getPage());
            System.out.println("Per Page: " + userResponse.getPer_page());
            System.out.println("Total: " + userResponse.getTotal());
            System.out.println("Total Pages: " + userResponse.getTotal_pages());
            System.out.println("Support URL: " + userResponse.getSupport().getUrl());
            System.out.println("Support Text: " + userResponse.getSupport().getText());
            for (
                    User.UserData userData : userResponse.getData()) {
                System.out.println("User ID: " + userData.getId());
                System.out.println("Email: " + userData.getEmail());
                System.out.println("First Name: " + userData.getFirst_name());
                System.out.println("Last Name: " + userData.getLast_name());
                System.out.println("Avatar: " + userData.getAvatar());
                System.out.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Asserts and prints the values of the expected and actual responses.
     *
     * @param fieldName the name of the field being compared
     * @param expected the expected value
     * @param actual the actual value
     */
    protected static void assertAndPrintValues(String fieldName, int expected, int actual) {
        System.out.println("Expected Response '" + fieldName + "': " + expected);
        System.out.println("Actual Response '" + fieldName + "': " + actual);
        assertEquals(expected, actual);
    }
}


