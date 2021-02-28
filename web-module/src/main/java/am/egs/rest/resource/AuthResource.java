package am.egs.rest.resource;

import am.egs.ejb.exception.RestException;
import am.egs.ejb.model.dto.UserDTO;
import am.egs.ejb.model.dto.UserLoginDTO;
import am.egs.ejb.service.UserService;
import am.egs.ejb.util.NullValidator;
import am.egs.ejb.util.TokenProvider;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
@NoArgsConstructor
public class AuthResource {

    @EJB
    private UserService userService;
    @EJB
    private TokenProvider tokenProvider;
    @EJB
    private NullValidator validator;
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthResource.class);

    @POST
    @Path("/login")
    public Response authenticateUser(final UserLoginDTO userLoginDTO) {
        validator.validateObjectForNull(userLoginDTO,"user login form", LOGGER);
        try {
            LOGGER.info("login attempt with username {}", userLoginDTO.getUserName());
            final UserDTO user = userService.findOneByUserNameAndPassword(userLoginDTO);
            final String token = tokenProvider.createToken(user);

            return Response.ok().header(AUTHORIZATION, "Bearer " + token)
                    .entity(user)
                    .build();
        } catch (RestException e) {
            LOGGER.error(e.getMessage());
            if (e.getStatus().equals(NOT_FOUND)) {
                return Response.status(UNAUTHORIZED).entity("Wrong username or password").build();
            } else {
                throw new RestException(e.getMessage(), Response.Status.INTERNAL_SERVER_ERROR);
            }
        }
    }
}
