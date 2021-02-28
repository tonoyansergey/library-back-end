package am.egs.ejb.dao.impl;

import am.egs.ejb.dao.UserDAO;
import am.egs.ejb.exception.RestException;
import am.egs.ejb.model.dto.UserLoginDTO;
import am.egs.ejb.model.entity.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.core.Response;
import java.util.List;

@Stateless
public class UserDAOImpl implements UserDAO {

    @PersistenceContext
    private EntityManager entityManager;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserDAOImpl.class);

    @Override
    public List<UserEntity> findAll() {
        final String SELECT_ALL = "SELECT u from UserEntity u";
        Query query = entityManager.createQuery(SELECT_ALL);

        LOGGER.info("attempt to get all users from database");
        try {
            List<UserEntity> users = query.getResultList();
            if (!users.isEmpty()) {
                return users;
            }
        } catch (RuntimeException e) {
            throw new RestException(e.getMessage(), Response.Status.INTERNAL_SERVER_ERROR);
        }
        throw new RestException("no users data found in database", Response.Status.NOT_FOUND, "No users found");
    }

    @Override
    public void save(final UserEntity userEntity) {
        LOGGER.info("attempt to save user in database | username: {}", userEntity.getUserName());
        try {
            entityManager.persist(userEntity);
        } catch (RuntimeException e) {
            throw new RestException(e.getMessage(), Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void delete(UserEntity userEntity) {
        LOGGER.info("attempt to remove user from database | id: {}", userEntity.getId());
        try {
            entityManager.remove(userEntity);
        } catch (RuntimeException e) {
            throw new RestException(e.getMessage(), Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public UserEntity findOneByUserNameAndPassword(final UserLoginDTO userLoginDTO) {
        final String SELECT_BY_USERNAME_AND_PASSWORD = "FROM UserEntity U WHERE U.userName = :user_name AND U.password = :password";
        final String userName = userLoginDTO.getUserName();

        Query query = entityManager.createQuery(SELECT_BY_USERNAME_AND_PASSWORD);
        query.setParameter("user_name", userName);
        query.setParameter("password", userLoginDTO.getPassword());

        LOGGER.info("attempt to get user from database by username & password: {}", userName);
        try {
            List<UserEntity> users = query.getResultList();
            if (!users.isEmpty()) {
                return users.get(0);
            }
        } catch (RuntimeException e) {
            throw new RestException(e.getMessage(), Response.Status.INTERNAL_SERVER_ERROR);
        }
        throw new RestException("no user data found in database", Response.Status.NOT_FOUND, "No user found");
    }

    @Override
    public UserEntity findOneById(final Long id) {
       LOGGER.info("attempt to get user from database by id: {}", id);

        try {
            UserEntity userEntity = entityManager.find(UserEntity.class, id);
            if (userEntity != null) {
                return userEntity;
            }
        } catch (RuntimeException e) {
            throw new RestException(e.getMessage(), Response.Status.INTERNAL_SERVER_ERROR);
        }
        throw new RestException("no user data found in database", Response.Status.NOT_FOUND, "No user found");
    }

    @Override
    public UserEntity findOneByUserName(final String userName) {
        final String SELECT_BY_USERNAME = "FROM UserEntity U WHERE U.userName = :userName";

        Query query = entityManager.createQuery(SELECT_BY_USERNAME);
        query.setParameter("userName", userName);

        LOGGER.info("attempt to get user from database by username: {}", userName);
        try {
            List<UserEntity> users = query.getResultList();
            if (!users.isEmpty()) {
                return users.get(0);
            }
        } catch (RuntimeException e) {
            throw new RestException(e.getMessage(), Response.Status.INTERNAL_SERVER_ERROR);
        }
        throw new RestException("no user data found in database", Response.Status.NOT_FOUND, "No user found");
    }

    @Override
    public void update(final UserEntity userEntity) {
        final Long id = userEntity.getId();
        LOGGER.info("attempt to update user by given id: {}", id);

        UserEntity userEntity1 = findOneById(id);
        try {
            userEntity.setPassword(userEntity1.getPassword());
            entityManager.merge(userEntity);
        } catch (RuntimeException e) {
            throw new RestException(e.getMessage(), Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void updatePassword(final Long userId, final String newPassword) {
        LOGGER.info("attempt to update password for user id: {}", userId);
        try {
            UserEntity user = findOneById(userId);
            if (user != null) {
                user.setPassword(newPassword);
            } else {
                throw new RestException("no user data found in database", Response.Status.NOT_FOUND, "No user found");
            }
        } catch (RuntimeException e) {
            throw new RestException(e.getMessage(), Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public boolean isExist(String userName) {
        final String SELECT_BY_USERNAME = "FROM UserEntity U WHERE U.userName = :userName";

        Query query = entityManager.createQuery(SELECT_BY_USERNAME);
        query.setParameter("userName", userName);

        LOGGER.info("attempt to check user existence in database by username: {}", userName);
        try {
            List<UserEntity> users = query.getResultList();
            if (!users.isEmpty()) {
                return true;
            }
        } catch (RuntimeException e) {
            throw new RestException(e.getMessage(), Response.Status.INTERNAL_SERVER_ERROR);
        }
        return false;
    }
}
