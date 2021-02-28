package am.egs.ejb.dao;

import am.egs.ejb.model.entity.BookEntity;
import javax.ejb.Local;
import java.util.List;

@Local
public interface BookDAO {

    List<BookEntity> findAll();

    BookEntity findOneById(final Long id);

    void save(final BookEntity bookEntity);

    void delete(final BookEntity bookEntity);

    void update(final BookEntity bookEntity);

    void incrementQuantityByOne(final Long id);

    void decrementQuantityByOne(final Long id);
}
