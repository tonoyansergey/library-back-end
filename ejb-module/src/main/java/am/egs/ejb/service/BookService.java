package am.egs.ejb.service;

import am.egs.ejb.model.pojo.Book;
import javax.ejb.Local;
import java.util.List;

@Local
public interface BookService {

    List<Book> findAll();

    Book findOneById(final Long id);

    void save(final Book book);

    void delete(final Long id);

    void update(final Book book);

    void incrementQuantityByOne(final Long id);

    void decrementQuantityByOne(final Long id);
}
