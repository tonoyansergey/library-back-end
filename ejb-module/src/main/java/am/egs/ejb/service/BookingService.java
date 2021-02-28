package am.egs.ejb.service;

import am.egs.ejb.model.dto.BookingDTO;
import am.egs.ejb.model.entity.BookingEntity;
import am.egs.ejb.model.pojo.Booking;
import javax.ejb.Local;
import java.util.List;

@Local
public interface BookingService {

    List<Booking> findAll();

    void save(final Booking booking);

    List<BookingDTO> findAllByCustomerId(final Long customerId);

    BookingDTO findByBookIdAndReceiptCode(final Long bookId, final String receiptCode);

    void delete(final Long id);
}
