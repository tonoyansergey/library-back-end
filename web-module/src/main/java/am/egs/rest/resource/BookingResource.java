package am.egs.rest.resource;

import am.egs.ejb.model.dto.BookingDTO;
import am.egs.ejb.model.dto.OrderDataDTO;
import am.egs.ejb.model.dto.UserDTO;
import am.egs.ejb.model.pojo.Booking;
import am.egs.ejb.model.pojo.Verification;
import am.egs.ejb.service.*;
import am.egs.ejb.util.NullValidator;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.RandomStringUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN;

@Path("/booking")
@Produces({APPLICATION_JSON,TEXT_PLAIN})
@NoArgsConstructor
public class BookingResource {

    @EJB
    private UserService userService;
    @EJB
    private VerificationService verificationService;
    @EJB
    private BookingService bookingService;
    @EJB
    private BookService bookService;
    @EJB
    private NullValidator validator;
    private static final Logger LOGGER = LoggerFactory.getLogger(BookingResource.class);

    @POST
    @Path("/order")
    public Response checkVCode(final OrderDataDTO orderData) {
        validator.validateObjectForNull(orderData, "order data", LOGGER);
        validator.constraintValidation(orderData);

        final Long userID = orderData.getUserID();
        final Long bookID = orderData.getBookID();
        final String vCode = orderData.getVerCode();


        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, 1);
        date = cal.getTime();


            final Verification verification = verificationService.findOneByVCodeAndUserId(vCode, userID);
            final String rCode = RandomStringUtils.random(4, true, true);

            ModelMapper mapper = new ModelMapper();
            final UserDTO user = userService.findOneById(userID);

            final Booking bRecord = Booking.builder()
                    .expireDate(date)
                    .book(bookService.findOneById(bookID))
                    .user(user)
                    .receiptCode(rCode)
                    .build();

            validator.validateObjectForNull(bRecord,"booking record", LOGGER);
            bookingService.save(bRecord);
            bookService.decrementQuantityByOne(bookID);
            verificationService.delete(verification.getId());

            return Response.ok(rCode).build();
    }

    @Path("/records/{id}")
    @GET
    public Response getBookingRecordsByCustomerId(@PathParam("id") final Long customerId) {
        validator.validateObjectForNull(customerId,"customer id", LOGGER);
        final List<BookingDTO> bookings = bookingService.findAllByCustomerId(customerId);
        GenericEntity<List<BookingDTO>> genericEntity = new GenericEntity<List<BookingDTO>>(bookings){};

        return Response.ok(genericEntity).build();
    }

    @DELETE
    @Path("/records/delete/{id}")
    public Response deleteBookingData(@PathParam("id") final Long id) {
        validator.validateObjectForNull(id, "booking data record id", LOGGER);
        bookingService.delete(id);
        return Response.ok().build();
    }

    @GET
    @Path("/records")
    public Response getBookings() {
        final List<Booking> bookings = bookingService.findAll();
        GenericEntity<List<Booking>> genericEntity = new GenericEntity<List<Booking>>(bookings){};

        return Response.ok(genericEntity).build();
    }
}
