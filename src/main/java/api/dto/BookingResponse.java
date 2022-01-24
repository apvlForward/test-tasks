package api.dto;

public class BookingResponse {

    Integer bookingid;
    BookingRequest booking;

    public BookingResponse() { }

    public BookingResponse(Integer bookingid, BookingRequest booking) {
        this.bookingid = bookingid;
        this.booking = booking;
    }

    public Integer getBookingid() {
        return bookingid;
    }

    public void setBookingid(Integer bookingid) {
        this.bookingid = bookingid;
    }

    public BookingRequest getBooking() {
        return booking;
    }

    public void setBooking(BookingRequest booking) {
        this.booking = booking;
    }
}
