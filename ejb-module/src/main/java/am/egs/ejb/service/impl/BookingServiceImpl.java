package am.egs.ejb.service.impl;

import am.egs.ejb.dao.BookingDAO;
import am.egs.ejb.model.dto.BookingDTO;
import am.egs.ejb.model.entity.BookingEntity;
import am.egs.ejb.model.pojo.Booking;
import am.egs.ejb.service.BookService;
import am.egs.ejb.service.BookingService;
import am.egs.ejb.util.NullValidator;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.List;
import java.lang.reflect.Type;


@Stateless
@NoArgsConstructor
public class BookingServiceImpl implements BookingService {

    @EJB
    private BookingDAO bookingDAO;
    @EJB
    private NullValidator validator;
    @EJB
    private BookService bookService;
    private static final ModelMapper mapper = new ModelMapper();
    private static final Logger LOGGER = LoggerFactory.getLogger(BookingServiceImpl.class);

    @Override
    public List<Booking> findAll() {
        final Type targetListType = new TypeToken<List<Booking>>() {}.getType();
        final List<Booking> bookings = mapper.map(bookingDAO.findAll(), targetListType);

        LOGGER.info("got all bookings from database, quantity: {}", bookings.size());
        return bookings;
    }

    @Override
    public void save(final Booking booking) {
        validator.constraintValidation(booking);
        bookingDAO.save(mapper.map(booking, BookingEntity.class));
        LOGGER.info("successfully saved new booking record in database for user: {}", booking.getUser().getUserName());
    }

    @Override
    public List<BookingDTO> findAllByCustomerId(final Long customerId) {
        final Type targetListType = new TypeToken<List<BookingDTO>>() {}.getType();
        final List<BookingDTO> bookings = mapper.map(bookingDAO.findAllByCustomerId(customerId), targetListType);
        LOGGER.info("got all booking records from database for user with id: {}, qnt: {}",customerId, bookings.size());
        return bookings;
    }

    @Override
    public BookingDTO findByBookIdAndReceiptCode(final Long bookId, final String receiptCode) {
        return null;
    }

    @Override
    public void delete(final Long id) {
        BookingEntity bookingEntity = bookingDAO.findOneById(id);
        bookingDAO.delete(bookingEntity);
        bookService.incrementQuantityByOne(bookingEntity.getBook().getId());
        LOGGER.info("successfully deleted booking record in database");
    }
}
