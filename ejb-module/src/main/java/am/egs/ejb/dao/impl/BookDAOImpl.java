package am.egs.ejb.dao.impl;

import am.egs.ejb.dao.BookDAO;
import am.egs.ejb.exception.RestException;
import am.egs.ejb.model.entity.BookEntity;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.ejb.Stateless;
import javax.persistence.*;
import javax.ws.rs.core.Response;
import java.util.List;

@Stateless
@Data
public class BookDAOImpl implements BookDAO {

    @PersistenceContext
    private EntityManager entityManager;
    private static final Logger LOGGER = LoggerFactory.getLogger(BookDAOImpl.class);

    @Override
    public List<BookEntity> findAll() {
        final String SELECT_ALL = "SELECT b from BookEntity b";
        Query query = entityManager.createQuery(SELECT_ALL);

        LOGGER.info("attempt to get all books from database");
        try {
            List<BookEntity> books = query.getResultList();
            if (!books.isEmpty()) {
                return books;
            }
        } catch (RuntimeException e) {
            throw new RestException(e.getMessage(), Response.Status.INTERNAL_SERVER_ERROR);
        }
        throw new RestException("no books data found in database", Response.Status.NOT_FOUND, "No books found");
    }

    @Override
    public void save(final BookEntity bookEntity) {
        LOGGER.info("attempt to save a book in database | title: {}", bookEntity.getTitle());
        try {
            entityManager.persist(bookEntity);
        } catch (RuntimeException e) {
            throw new RestException(e.getMessage(), Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void delete(BookEntity bookEntity) {
        LOGGER.info("attempt to remove book from database | id: {}", bookEntity.getId());
        try {
            entityManager.remove(bookEntity);
        } catch (RuntimeException e) {
            throw new RestException(e.getMessage(), Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void update(BookEntity bookEntity) {
        LOGGER.info("attempt to update book by given id: {}", bookEntity.getId());

        try {
            entityManager.merge(bookEntity);
        } catch (RuntimeException e) {
            throw new RestException(e.getMessage(), Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void incrementQuantityByOne(final Long id) {
        LOGGER.info("attempt to increment quantity by 1, book: {}", id);
        try {
            BookEntity bookEntity = findOneById(id);
            if (bookEntity != null) {
                bookEntity.setQuantity(bookEntity.getQuantity() + 1);
            } else {
                throw new RestException("no book data found in database", Response.Status.NOT_FOUND, "No book found");
            }
        } catch (RuntimeException e) {
            throw new RestException(e.getMessage(), Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void decrementQuantityByOne(final Long id) {
        LOGGER.info("attempt to decrement quantity by 1, book: {}", id);
        try {
            BookEntity bookEntity = findOneById(id);
            if (bookEntity != null) {
                if (bookEntity.getQuantity() != 0) {
                    bookEntity.setQuantity(bookEntity.getQuantity() - 1);
                } else {
                    throw new RestException("no book available, quantity: 0", Response.Status.NOT_FOUND, "Book is not available");
                }
            } else {
                throw new RestException("no book data found in database", Response.Status.NOT_FOUND, "No book found");
            }
        } catch (RuntimeException e) {
            throw new RestException(e.getMessage(), Response.Status.INTERNAL_SERVER_ERROR);
        }
    }


    @Override
    public BookEntity findOneById(final Long id) {
      LOGGER.info("attempt to get book from database by id: {}", id);
        try {
            BookEntity bookEntity = entityManager.find(BookEntity.class, id);
        if (bookEntity != null) {
            return bookEntity;
        }
        } catch (RuntimeException e) {
            throw new RestException(e.getMessage(), Response.Status.INTERNAL_SERVER_ERROR);
        }
        throw new RestException("no book data found in database", Response.Status.NOT_FOUND, "No book found");
    }
}
