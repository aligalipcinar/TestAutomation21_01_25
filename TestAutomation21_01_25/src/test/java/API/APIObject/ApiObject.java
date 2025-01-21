package API.APIObject;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Random;
import java.util.logging.Logger;

public class ApiObject {

    private static final Logger logger = Logger.getLogger(ApiObject.class.getName());
    private static final String BASE_URL;
    private static final String ACCESS_TOKEN;

    static {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("src/test/java/config/config.properties"));
            BASE_URL = properties.getProperty("Base_Url");
            ACCESS_TOKEN = properties.getProperty("Access_Token");
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config properties", e);
        }
    }

    public static String generateRandomString() {
        String characters = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder(5);
        for (int i = 0; i < 5; i++) {
            sb.append(characters.charAt(random.nextInt(characters.length())));
        }
        return sb.toString();
    }

    public static String createBooking() {
        RestAssured.baseURI = BASE_URL;
        RequestSpecification request = RestAssured.given();

        String requestBody = "{" +
                "\"firstname\": \"Alex\"," +
                "\"lastname\": \"Galip\"," +
                "\"totalprice\": 123," +
                "\"depositpaid\": true," +
                "\"bookingdates\": {\"checkin\": \"2025-01-01\", \"checkout\": \"2025-01-02\"}," +
                "\"additionalneeds\": \"Breakfast\"}";

        request.header("Content-Type", "application/json");
        request.body(requestBody);

        Response response = request.request(Method.POST, "/booking");
        Assert.assertEquals(response.getStatusCode(), 200, "Failed to create booking");

        JsonPath jsonPath = response.jsonPath();
        String bookingId = jsonPath.getString("bookingid");
        logger.info("Booking created with ID: " + bookingId);
        return bookingId;
    }

    public static void getBooking(String bookingId) {
        RestAssured.baseURI = BASE_URL;
        RequestSpecification request = RestAssured.given();

        Response response = request.request(Method.GET, "/booking/" + bookingId);
        Assert.assertEquals(response.getStatusCode(), 200, "Failed to get booking");

        JsonPath jsonPath = response.jsonPath();
        logger.info("Booking details: " + jsonPath.prettyPrint());
    }

    public static void updateBooking(String bookingId) {
        RestAssured.baseURI = BASE_URL;
        RequestSpecification request = RestAssured.given();

        String requestBody = "{" +
                "\"firstname\": \"Ali\"," +
                "\"lastname\": \"Cinar\"," +
                "\"totalprice\": 200," +
                "\"depositpaid\": false," +
                "\"bookingdates\": {\"checkin\": \"2025-02-01\", \"checkout\": \"2025-02-02\"}," +
                "\"additionalneeds\": \"Lunch\"}";

        request.header("Content-Type", "application/json");
        request.header("Authorization", "Bearer " + ACCESS_TOKEN);
        request.body(requestBody);

        Response response = request.request(Method.PUT, "/booking/" + bookingId);
        Assert.assertEquals(response.getStatusCode(), 200, "Failed to update booking");

        logger.info("Booking updated for ID: " + bookingId);
    }

    public static void deleteBooking(String bookingId) {
        RestAssured.baseURI = BASE_URL;
        RequestSpecification request = RestAssured.given();

        request.header("Authorization", "Bearer " + ACCESS_TOKEN);

        Response response = request.request(Method.DELETE, "/booking/" + bookingId);
        Assert.assertEquals(response.getStatusCode(), 201, "Failed to delete booking");

        logger.info("Booking deleted for ID: " + bookingId);
    }
}
