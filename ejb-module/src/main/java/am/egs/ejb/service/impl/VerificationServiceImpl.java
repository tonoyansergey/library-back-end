package am.egs.ejb.service.impl;

import am.egs.ejb.dao.VerificationDAO;
import am.egs.ejb.model.entity.VerificationEntity;
import am.egs.ejb.model.pojo.Verification;
import am.egs.ejb.service.VerificationService;
import am.egs.ejb.util.NullValidator;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
@NoArgsConstructor
public class VerificationServiceImpl implements VerificationService {

    @EJB
    private VerificationDAO verificationDAO;
    @EJB
    private NullValidator validator;
    private static final ModelMapper mapper = new ModelMapper();
    private static final Logger LOGGER = LoggerFactory.getLogger(VerificationServiceImpl.class);

    @Override
    public Verification findOneByVCodeAndUserId(final String vCode, final Long userId) {
        final Verification verification = mapper.map(verificationDAO.findOneByVCodeAndUserId(vCode, userId), Verification.class);
        LOGGER.info("got verification record with ver code for user: {}", userId);
        return verification;
    }

    @Override
    public void save(final Verification verification) {
        validator.constraintValidation(verification);
        verificationDAO.save(mapper.map(verification, VerificationEntity.class));
        LOGGER.info("successfully saved new verification record for user: {}", verification.getUser().getUserName());
    }

    @Override
    public void delete(final Long id) {
        VerificationEntity verificationEntity = verificationDAO.findOneById(id);
        verificationDAO.delete(verificationEntity);
        LOGGER.info("successfully deleted booking record in database");
    }
}
