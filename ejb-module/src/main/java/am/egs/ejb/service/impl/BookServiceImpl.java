package am.egs.ejb.service.impl;

import am.egs.ejb.dao.BookDAO;
import am.egs.ejb.model.entity.BookEntity;
import am.egs.ejb.model.pojo.Book;
import am.egs.ejb.service.BookService;
import am.egs.ejb.util.NullValidator;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.lang.reflect.Type;
import java.util.List;

@Stateless
@NoArgsConstructor
public class BookServiceImpl implements BookService {

    @EJB
    private BookDAO bookDAO;
    @EJB
    private NullValidator validator;
    private static final Logger LOGGER = LoggerFactory.getLogger(BookServiceImpl.class);
    private static final ModelMapper mapper = new ModelMapper();


    @Override
    public List<Book> findAll() {
        final Type targetListType = new TypeToken<List<Book>>() {}.getType();
        final List<Book> books = mapper.map(bookDAO.findAll(), targetListType);
        LOGGER.info("got all books from database, qnt: {}",books.size());
        return books;
    }

    @Override
    public Book findOneById(final Long id) {
        final Book book = mapper.map(bookDAO.findOneById(id), Book.class);
        LOGGER.info("got book from database with id: {}", id);
        return book;
    }

    @Override
    public void save(final Book book) {
        validator.constraintValidation(book);
        bookDAO.save(mapper.map(book, BookEntity.class));
        LOGGER.info("successfully saved new book with title: {}", book.getTitle());
    }

    @Override
    public void incrementQuantityByOne(final Long id) {
        bookDAO.incrementQuantityByOne(id);
        LOGGER.info("successfully incremented quantity of book: {}", id);
    }

    @Override
    public void decrementQuantityByOne(final Long id) {
        bookDAO.decrementQuantityByOne(id);
        LOGGER.info("successfully decremented quantity of book: {}", id);
    }

    @Override
    public void delete(final Long id) {
        bookDAO.delete(bookDAO.findOneById(id));
        LOGGER.info("successfully deleted book from database");
    }

    @Override
    public void update(Book book) {
        validator.constraintValidation(book);

        bookDAO.update(mapper.map(book, BookEntity.class));
        LOGGER.info("successfully updated book by given id: {}", book.getId());

    }
}
