package am.egs.ejb.dao;

import am.egs.ejb.model.entity.BookingEntity;
import javax.ejb.Local;
import java.util.List;

@Local
public interface BookingDAO {

    List<BookingEntity> findAll();

    void save(final BookingEntity bookingEntity);

    List<BookingEntity> findAllByCustomerId(final Long customerId);

    BookingEntity findByBookIdAndReceiptCode(final Long bookId, final String receiptCode);

    void delete(final BookingEntity bookingEntity);

    BookingEntity findOneById(final Long id);
}
