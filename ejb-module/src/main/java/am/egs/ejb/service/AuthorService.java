package am.egs.ejb.service;

import am.egs.ejb.model.pojo.Author;

import javax.ejb.Local;
import java.util.List;

@Local
public interface AuthorService {

    List<Author> findAll();

    void save(final Author author);
}
