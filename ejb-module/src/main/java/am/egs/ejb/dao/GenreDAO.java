package am.egs.ejb.dao;

import am.egs.ejb.model.entity.GenreEntity;
import javax.ejb.Local;
import java.util.List;

@Local
public interface GenreDAO {

    List<GenreEntity> findAll();

    boolean isExist(final String genreName);

    void save(final GenreEntity genreEntity);
}
