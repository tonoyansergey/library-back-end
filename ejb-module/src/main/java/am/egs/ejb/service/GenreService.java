package am.egs.ejb.service;

import am.egs.ejb.model.pojo.Genre;

import javax.ejb.Local;
import java.util.List;

@Local
public interface GenreService {

    List<Genre> findAll();

    void save(final Genre genre);
}
