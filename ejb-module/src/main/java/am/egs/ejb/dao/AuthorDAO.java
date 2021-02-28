package am.egs.ejb.dao;

import am.egs.ejb.model.entity.AuthorEntity;

import javax.ejb.Local;
import java.util.List;

@Local
public interface AuthorDAO {

    List<AuthorEntity> findAll();

    boolean isExist(final String authorName);

    void save(final AuthorEntity authorEntity);
}
