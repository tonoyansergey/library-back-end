package am.egs.ejb.dao.impl;

import am.egs.ejb.dao.VerificationDAO;
import am.egs.ejb.exception.RestException;
import am.egs.ejb.model.entity.VerificationEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.core.Response;
import java.util.List;

@Stateless
public class VerificationDAOImpl implements VerificationDAO {

    @PersistenceContext
    private EntityManager entityManager;
    private static final Logger LOGGER = LoggerFactory.getLogger(VerificationDAOImpl.class);

    @Override
    public VerificationEntity findOneByVCodeAndUserId(final String vCode, final Long userId) {
        String SELECT_BY_V_CODE_AND_USER_ID = "FROM VerificationEntity V WHERE V.vCode = :vCode and V.user.id = :userId";

        Query query = entityManager.createQuery(SELECT_BY_V_CODE_AND_USER_ID);
        query.setParameter("vCode", vCode);
        query.setParameter("userId", userId);

        LOGGER.info("attempt to get verification record from database by user_id: {} and v_code", userId);
        try {
            List<VerificationEntity> records = query.getResultList();
            if (!records.isEmpty()) {
                return records.get(0);
            }
        } catch (RuntimeException e) {
            throw new RestException(e.getMessage(), Response.Status.INTERNAL_SERVER_ERROR);
        }
        throw new RestException("no verification data found in database", Response.Status.NOT_FOUND, "Wrong verification code !");
    }

    @Override
    public void save(final VerificationEntity verificationEntity) {
        LOGGER.info("attempt to save verification record in database for user with id: {}", verificationEntity.getUser().getId());
        try {
            entityManager.persist(verificationEntity);
        } catch (RuntimeException e) {
            throw new RestException(e.getMessage(), Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void delete(VerificationEntity verificationEntity) {
        LOGGER.info("attempt to remove verification record from database | id: {}", verificationEntity.getId());
        try {
            entityManager.remove(verificationEntity);
        } catch (RuntimeException e) {
            throw new RestException(e.getMessage(), Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public VerificationEntity findOneById(Long id) {
        LOGGER.info("attempt to get verification record from database by id: {}", id);
        try {
            VerificationEntity verificationEntity = entityManager.find(VerificationEntity.class, id);
            if (verificationEntity != null) {
                return verificationEntity;
            }
        } catch (RuntimeException e) {
            throw new RestException(e.getMessage(), Response.Status.INTERNAL_SERVER_ERROR);
        }
        throw new RestException("no verification record found in database", Response.Status.NOT_FOUND, "No verification record found");
    }
}
