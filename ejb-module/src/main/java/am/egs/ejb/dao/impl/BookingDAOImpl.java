package am.egs.ejb.dao.impl;

import am.egs.ejb.dao.BookingDAO;
import am.egs.ejb.exception.RestException;
import am.egs.ejb.model.entity.BookingEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.core.Response;
import java.util.List;

@Stateless
public class BookingDAOImpl implements BookingDAO {

    @PersistenceContext
    private EntityManager entityManager;
    private static final Logger LOGGER = LoggerFactory.getLogger(BookingDAOImpl.class);

    @Override
    public List<BookingEntity> findAll() {
        final String SELECT_ALL = "SELECT b from BookingEntity b";
        Query query = entityManager.createQuery(SELECT_ALL);

        LOGGER.info("attempt to get all booking records from database");
        try {
            List<BookingEntity> bookings = query.getResultList();
            if (!bookings.isEmpty()) {
                return bookings;
            }
        } catch (RuntimeException e) {
            throw new RestException(e.getMessage(), Response.Status.INTERNAL_SERVER_ERROR);
        }
        throw new RestException("no booking data found in database", Response.Status.NOT_FOUND, "No booking records found");
    }

    @Override
    public void save(final BookingEntity bookingEntity) {
        LOGGER.info("attempt to save new booking record in database for book | title: {}", bookingEntity.getBook().getTitle());
        try {
            entityManager.persist(bookingEntity);
        } catch (RuntimeException e) {
            throw new RestException(e.getMessage(), Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<BookingEntity> findAllByCustomerId(final Long customerId) {
        final String SELECT_ALL_BY_CUSTOMER_ID = "FROM BookingEntity B WHERE B.user.id = :id";

        Query query = entityManager.createQuery(SELECT_ALL_BY_CUSTOMER_ID);
        query.setParameter("id", customerId);

        LOGGER.info("attempt to get all booking records from database for customer with id: {}", customerId);
        try {
            List<BookingEntity> bookingEntities = query.getResultList();
            if (!bookingEntities.isEmpty()) {
                return bookingEntities;
            }
        } catch (RuntimeException e) {
            throw new RestException(e.getMessage(), Response.Status.INTERNAL_SERVER_ERROR);
        }
        throw new RestException("no booking records found in database for user with id: " + customerId, Response.Status.NOT_FOUND, "No booking records found");
    }

    @Override
    public BookingEntity findByBookIdAndReceiptCode(final Long bookId,final String receiptCode) {
        String SELECT_BY_BOOK_ID_AND_RECEIPT_CODE = "FROM BookingEntity B WHERE B.book.id = :bookId and B.receiptCode = :rCode";

        Query query = entityManager.createQuery(SELECT_BY_BOOK_ID_AND_RECEIPT_CODE);
        query.setParameter("bookId", bookId);
        query.setParameter("rCode", receiptCode);

        LOGGER.info("attempt to get booking record from database by book_id: {} and receipt_code", bookId);
        try {
            List<BookingEntity> records = query.getResultList();
            if (!records.isEmpty()) {
                return records.get(0);
            }
        } catch (RuntimeException e) {
            throw new RestException(e.getMessage(), Response.Status.INTERNAL_SERVER_ERROR);
        }
        throw new RestException("no booking data found in database", Response.Status.NOT_FOUND, "Wrong booking details !");
    }

    @Override
    public void delete(BookingEntity bookingEntity) {
        LOGGER.info("attempt to remove booking record in database for book | title: {}", bookingEntity.getBook().getTitle());
        try {
            entityManager.remove(bookingEntity);
        } catch (RuntimeException e) {
            throw new RestException(e.getMessage(), Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public BookingEntity findOneById(Long id) {
        LOGGER.info("attempt to get booking record from database by id: {}", id);
        try {
           BookingEntity bookingEntity = entityManager.find(BookingEntity.class, id);
            if (bookingEntity != null) {
                return bookingEntity;
            }
        } catch (RuntimeException e) {
            throw new RestException(e.getMessage(), Response.Status.INTERNAL_SERVER_ERROR);
        }
        throw new RestException("no booking record found in database", Response.Status.NOT_FOUND, "No booking record found");
    }
}
