// Updated ApiTest Class
package API.APITest;

import API.APIObject.ApiObject;
import org.testng.annotations.Test;

public class ApiTest extends ApiObject {

    @Test(priority = 1, testName = "TC_0001 Create and Retrieve Booking Test")
    public void createAndRetrieveBookingTest() {
        String bookingId = createBooking();
        getBooking(bookingId);
    }

    @Test(priority = 2, testName = "TC_0002 Update Booking Test")
    public void updateBookingTest() {
        String bookingId = createBooking();
        updateBooking(bookingId);
    }

    @Test(priority = 3, testName = "TC_0003 Delete Booking Test")
    public void deleteBookingTest() {
        String bookingId = createBooking();
        deleteBooking(bookingId);
    }

    @Test(priority = 4, testName = "TC_0004 Full Booking Lifecycle Test")
    public void fullBookingLifecycleTest() {
        String bookingId = createBooking();
        getBooking(bookingId);
        updateBooking(bookingId);
        deleteBooking(bookingId);
    }
}
