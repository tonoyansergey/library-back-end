package am.egs.rest.resource.admin;

import am.egs.ejb.exception.RestException;
import am.egs.ejb.model.dto.AdminLoginDTO;
import am.egs.ejb.model.dto.UserDTO;
import am.egs.ejb.model.dto.UserLoginDTO;
import am.egs.ejb.model.pojo.Admin;
import am.egs.ejb.service.AdminService;
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
import static javax.ws.rs.core.Response.Status.*;

@Path("/admin/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
@NoArgsConstructor
public class AdminAuthResource {

    @EJB
    private AdminService adminService;
    @EJB
    private TokenProvider tokenProvider;
    @EJB
    private NullValidator validator;
    private static final Logger LOGGER = LoggerFactory.getLogger(AdminAuthResource.class);

    @POST
    @Path("/login")
    public Response authenticateAdmin(final AdminLoginDTO adminLoginDTO) {
        validator.validateObjectForNull(adminLoginDTO,"admin login form", LOGGER);
        try {
            LOGGER.info("login attempt with username {}", adminLoginDTO.getUserName());
            final Admin admin = adminService.findOneByUserNameAndPassword(adminLoginDTO);
            final String token = tokenProvider.createAdminToken(admin);

            return Response.ok().header("ADMIN_AUTHORIZATION", "Bearer " + token)
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
