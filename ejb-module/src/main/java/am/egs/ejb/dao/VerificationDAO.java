package am.egs.ejb.dao;

import am.egs.ejb.model.entity.VerificationEntity;
import javax.ejb.Local;

@Local
public interface VerificationDAO {

    VerificationEntity findOneByVCodeAndUserId(final String vCode, final Long userId);

    void save(final VerificationEntity verificationEntity);

    VerificationEntity findOneById(final Long id);

    void delete(final VerificationEntity verificationEntity);
}
