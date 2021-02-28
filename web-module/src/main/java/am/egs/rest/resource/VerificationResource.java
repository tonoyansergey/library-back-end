package am.egs.rest.resource;

import am.egs.ejb.model.pojo.User;
import am.egs.ejb.model.pojo.Verification;
import am.egs.ejb.service.*;
import am.egs.ejb.service.impl.MailService;
import am.egs.ejb.util.NullValidator;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.RandomStringUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/verification")
@Produces(APPLICATION_JSON)
@NoArgsConstructor
public class VerificationResource {

    @EJB
    private UserService userService;
    @EJB
    private VerificationService verificationService;
    @EJB
    private MailService mailService;
    @EJB
    private BookingService orderService;
    @EJB
    private BookService bookService;
    @EJB
    private NullValidator validator;
    private static final Logger LOGGER = LoggerFactory.getLogger(VerificationResource.class);

    @POST
    @Path("/email")
    public Response verifyEmail(final Long userID) {
        validator.validateObjectForNull(userID,"user id from request", LOGGER);
        LOGGER.debug("User ID from request: {}", userID);

        ModelMapper mapper = new ModelMapper();
        final User user = mapper.map(userService.findOneById(userID), User.class);

        final String vCode = RandomStringUtils.random(6, true, true);
        verificationService.save(Verification.builder().user(user).vCode(vCode).build());
        new Thread(() -> mailService.sendMail(user.getEmail(), vCode)).start();

        return Response.ok().build();
    }
}
