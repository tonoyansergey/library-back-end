package am.egs.ejb.dao.impl;

import am.egs.ejb.dao.AuthorDAO;
import am.egs.ejb.exception.RestException;
import am.egs.ejb.model.entity.AuthorEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.core.Response;
import java.util.List;

@Stateless
public class AuthorDAOImpl implements AuthorDAO {

    @PersistenceContext
    private EntityManager entityManager;
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorDAOImpl.class);

    @Override
    public List<AuthorEntity> findAll() {
        final String SELECT_ALL = "SELECT a from AuthorEntity a";
        Query query = entityManager.createQuery(SELECT_ALL);

        LOGGER.info("attempt to get all genres from database");
        try {
            List<AuthorEntity> authors = query.getResultList();
            if (!authors.isEmpty()) {
                return authors;
            }
        } catch (RuntimeException e) {
            throw new RestException(e.getMessage(), Response.Status.INTERNAL_SERVER_ERROR);
        }
        throw new RestException("no author data found in database", Response.Status.NOT_FOUND, "No authors found");
    }

    @Override
    public boolean isExist(String authorName) {
        final String SELECT_BY_AUTHOR_NAME = "FROM AuthorEntity A WHERE A.authorName = :authorName";

        Query query = entityManager.createQuery(SELECT_BY_AUTHOR_NAME);
        query.setParameter("authorName", authorName);

        LOGGER.info("attempt to check author existence in database by name: {}", authorName);
        try {
            List<AuthorEntity> authors = query.getResultList();
            if (!authors.isEmpty()) {
                return true;
            }
        } catch (RuntimeException e) {
            throw new RestException(e.getMessage(), Response.Status.INTERNAL_SERVER_ERROR);
        }
        return false;
    }

    @Override
    public void save(final AuthorEntity authorEntity) {
        LOGGER.info("attempt to save author in database | name: {}", authorEntity.getAuthorName());
        try {
            entityManager.persist(authorEntity);
        } catch (RuntimeException e) {
            throw new RestException(e.getMessage(), Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
}
