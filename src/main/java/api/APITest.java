package api;

import api.dto.Bookingdates;
import api.dto.BookingRequest;
import api.dto.BookingResponse;
import com.google.gson.Gson;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;

public class APITest {

    Gson gson = new Gson();

    @DataProvider(name="UserData")
    public Object[][] getUserData() {
        return new Object[][] {
                new Object[] {"Jim", "Brown", 111, true, "2018-01-01", "2019-01-01", "Breakfast"},
                new Object[] {"Jane", "Doe", 250, true, "2020-02-10", "2020-03-15", "Breakfast and Lunch"},
                new Object[] {"Michael", "Davidson", 45, false, "2021-06-15", "2021-06-22", "none"},
        };
    }

    @Test(dataProvider="UserData")
    public void createBooking(String firstname, String lastname, Integer totalprice, Boolean depositpaid,
                              String checkin, String checkout, String additionalneeds) {

        BookingRequest createBookingRequest = new BookingRequest(firstname, lastname, totalprice, depositpaid,
                                                                 new Bookingdates(checkin, checkout), additionalneeds);

        Response response = given().contentType("application/json")
                .body(gson.toJson(createBookingRequest))
                .when().post("https://restful-booker.herokuapp.com/booking");
               response.then().statusLine("HTTP/1.1 200 OK");

        BookingResponse postBookingResponseBody = gson.fromJson(response.body().asString(), BookingResponse.class);

        Assert.assertNotNull(postBookingResponseBody.getBookingid());
        Assert.assertEquals(postBookingResponseBody.getBooking().getFirstname(), createBookingRequest.getFirstname());
        Assert.assertEquals(postBookingResponseBody.getBooking().getLastname(), createBookingRequest.getLastname());
        Assert.assertEquals(postBookingResponseBody.getBooking().getTotalprice(), createBookingRequest.getTotalprice());
        Assert.assertEquals(postBookingResponseBody.getBooking().getDepositpaid(), createBookingRequest.getDepositpaid());
        Assert.assertEquals(postBookingResponseBody.getBooking().getBookingdates().getCheckin(), createBookingRequest.getBookingdates().getCheckin());
        Assert.assertEquals(postBookingResponseBody.getBooking().getBookingdates().getCheckout(), createBookingRequest.getBookingdates().getCheckout());
        Assert.assertEquals(postBookingResponseBody.getBooking().getAdditionalneeds(), createBookingRequest.getAdditionalneeds());
    }

    @Test(dataProvider="UserData")
    public void getBooking(String firstname, String lastname, Integer totalprice, Boolean depositpaid,
                           String checkin, String checkout, String additionalneeds) {

        //Performing POST request
        BookingRequest createBookingRequest = new BookingRequest(firstname, lastname, totalprice, depositpaid,
                                                                 new Bookingdates(checkin, checkout), additionalneeds);

        Response postResponse = given().contentType("application/json")
                .body(gson.toJson(createBookingRequest))
                .when().post("https://restful-booker.herokuapp.com/booking");
        postResponse.then().statusLine("HTTP/1.1 200 OK");

        BookingResponse postBookingResponseBody = gson.fromJson(postResponse.body().asString(), BookingResponse.class);
        Integer bookingid = postBookingResponseBody.getBookingid();

        //Validating it with GET request
        Response getResponse = given().accept("application/json")
                .when().get("https://restful-booker.herokuapp.com/booking/" + bookingid);
        getResponse.then().statusLine("HTTP/1.1 200 OK");

        BookingRequest getBookingResponseBody = gson.fromJson(getResponse.body().asString(), BookingRequest.class);
        Assert.assertEquals(getBookingResponseBody.getFirstname(), firstname);
        Assert.assertEquals(getBookingResponseBody.getLastname(), lastname);
        Assert.assertEquals(getBookingResponseBody.getTotalprice(), totalprice);
        Assert.assertEquals(getBookingResponseBody.getDepositpaid(), depositpaid);
        Assert.assertEquals(getBookingResponseBody.getBookingdates().getCheckin(), checkin);
        Assert.assertEquals(getBookingResponseBody.getBookingdates().getCheckout(), checkout);
        Assert.assertEquals(getBookingResponseBody.getAdditionalneeds(), additionalneeds);
    }
}
