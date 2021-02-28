package am.egs.ejb.util;

import am.egs.ejb.exception.RestException;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.ejb.Stateless;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.ws.rs.core.Response;
import java.util.Set;

@Stateless
@NoArgsConstructor
public class NullValidator {

    private static Validator validator;
    private static final Logger LOGGER = LoggerFactory.getLogger(NullValidator.class);


    private Validator getValidator() {
        if (validator == null) {
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            validator = factory.getValidator();
        }
        return validator;
    }

    public <T> void validateObjectForNull(T argument, String message, Logger LOGGER) throws RestException {
            if (argument == null) {
                LOGGER.warn("{} can not be null, throwing RuntimeException", message);
                throw new RestException(Response.Status.BAD_REQUEST, argument + "can not be null");
            }
        }

     public void validateStringNotEmpty(String s, String message, Logger LOGGER) {
        if (s.equals("") || s == null) {
            LOGGER.warn("{} can not be null or empty, throwing RuntimeException", message);
            throw new RestException(Response.Status.BAD_REQUEST, message + "can not be null or empty");
        }
    }

    public <T> void constraintValidation(T object) {
        Set<ConstraintViolation<T>> constraintViolations = getValidator().validate(object);

        if(constraintViolations.size() > 0) {
            for (ConstraintViolation<T> cv : constraintViolations) {
                LOGGER.warn(cv.getRootBeanClass().getName() + "." + cv.getPropertyPath() + " " + cv.getMessage());
                LOGGER.warn("throwing exception");
            }
            throw new RestException(Response.Status.BAD_REQUEST, "null or empty fields");
        }
    }
}
