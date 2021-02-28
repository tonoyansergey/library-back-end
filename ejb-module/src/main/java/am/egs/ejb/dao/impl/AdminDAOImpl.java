package am.egs.ejb.dao.impl;

import am.egs.ejb.dao.AdminDAO;
import am.egs.ejb.exception.RestException;
import am.egs.ejb.model.dto.AdminLoginDTO;
import am.egs.ejb.model.entity.AdminEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.core.Response;
import java.util.List;

@Stateless
public class AdminDAOImpl implements AdminDAO {

    @PersistenceContext
    private EntityManager entityManager;
    private static final Logger LOGGER = LoggerFactory.getLogger(AdminDAOImpl.class);

    @Override
    public List<AdminEntity> findAll() {
        final String SELECT_ALL = "SELECT a from AdminEntity a";
        Query query = entityManager.createQuery(SELECT_ALL);

        LOGGER.info("attempt to get all admins from database");
        try {
            List<AdminEntity> admins = query.getResultList();
            if (!admins.isEmpty()) {
                return admins;
            }
        } catch (RuntimeException e) {
            throw new RestException(e.getMessage(), Response.Status.INTERNAL_SERVER_ERROR);
        }
        throw new RestException("no admin data found in database", Response.Status.NOT_FOUND, "No admins found");
    }

    @Override
    public AdminEntity findOneByUserNameAndPassword(final AdminLoginDTO adminLoginDTO) {
        final String SELECT_BY_USERNAME_AND_PASSWORD = "FROM AdminEntity A WHERE A.userName = :user_name AND A.password = :password";
        final String userName = adminLoginDTO.getUserName();

        Query query = entityManager.createQuery(SELECT_BY_USERNAME_AND_PASSWORD);
        query.setParameter("user_name", userName);
        query.setParameter("password", adminLoginDTO.getPassword());

        LOGGER.info("attempt to get user from database by username & password: {}", userName);
        try {
            List<AdminEntity> admins = query.getResultList();
            if (!admins.isEmpty()) {
                return admins.get(0);
            }
        } catch (RuntimeException e) {
            throw new RestException(e.getMessage(), Response.Status.INTERNAL_SERVER_ERROR);
        }
        throw new RestException("no admin data found in database", Response.Status.NOT_FOUND, "No admin found");
    }
}
