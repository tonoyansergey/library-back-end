package am.egs.ejb.service;

import am.egs.ejb.model.pojo.Verification;
import javax.ejb.Local;

@Local
public interface VerificationService {

    Verification findOneByVCodeAndUserId(final String vCode, final Long userId);

    void save(final Verification verification);

    void delete(final Long id);
}
