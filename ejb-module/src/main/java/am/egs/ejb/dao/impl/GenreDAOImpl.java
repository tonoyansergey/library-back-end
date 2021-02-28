package am.egs.ejb.dao.impl;

import am.egs.ejb.dao.GenreDAO;
import am.egs.ejb.exception.RestException;
import am.egs.ejb.model.entity.GenreEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.core.Response;
import java.util.List;

@Stateless
public class GenreDAOImpl implements GenreDAO {

    @PersistenceContext
    private EntityManager entityManager;
    private static final Logger LOGGER = LoggerFactory.getLogger(GenreDAOImpl.class);

    @Override
    public List<GenreEntity> findAll() {
        final String SELECT_ALL = "SELECT g from GenreEntity g";
        Query query = entityManager.createQuery(SELECT_ALL);

        LOGGER.info("attempt to get all genres from database");
        try {
            List<GenreEntity> genres = query.getResultList();
            if (!genres.isEmpty()) {
                return genres;
            }
        } catch (RuntimeException e) {
            throw new RestException(e.getMessage(), Response.Status.INTERNAL_SERVER_ERROR);
        }
        throw new RestException("no genre data found in database", Response.Status.NOT_FOUND, "No genres found");
    }

    @Override
    public boolean isExist(String genreName) {
        final String SELECT_BY_GENRE_NAME = "FROM GenreEntity G WHERE G.genreName = :genreName";

        Query query = entityManager.createQuery(SELECT_BY_GENRE_NAME);
        query.setParameter("genreName", genreName);

        LOGGER.info("attempt to check genre existence in database by name: {}", genreName);
        try {
            List<GenreEntity> genres = query.getResultList();
            if (!genres.isEmpty()) {
                return true;
            }
        } catch (RuntimeException e) {
            throw new RestException(e.getMessage(), Response.Status.INTERNAL_SERVER_ERROR);
        }
        return false;
    }

    @Override
    public void save(final GenreEntity usgenreEntityrEntity) {
        LOGGER.info("attempt to save genre in database | name: {}", usgenreEntityrEntity.getGenreName());
        try {
            entityManager.persist(usgenreEntityrEntity);
        } catch (RuntimeException e) {
            throw new RestException(e.getMessage(), Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
}
