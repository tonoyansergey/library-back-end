package am.egs.rest.resource;

import am.egs.ejb.model.dto.UserDTO;
import am.egs.ejb.model.dto.UserPasswordUpdateDTO;
import am.egs.ejb.model.pojo.User;
import am.egs.ejb.service.UserService;
import am.egs.ejb.util.NullValidator;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import java.util.List;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/users")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
@NoArgsConstructor
public class UserResource {

    @EJB
    private UserService userService;
    @EJB
    private NullValidator validator;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserResource.class);

    @GET
    public Response getUsers() {
        final List<UserDTO> users = userService.findAll();
        GenericEntity<List<UserDTO>> genericEntity = new GenericEntity<List<UserDTO>>(users){};

        return Response.ok(genericEntity).build();
    }

    @Path("{id}")
    @GET
    public Response getUserById(@PathParam("id") final Long id) {
        validator.validateObjectForNull(id,"user id", LOGGER);
        final UserDTO user = userService.findOneById(id);
        return Response.ok(user).build();
    }

    @PUT
    @Path("/update/{id}")
    public Response updateUser(@PathParam("id") final Long id, final UserDTO userDTO) {
        validator.validateObjectForNull(id, "update user", LOGGER);
        validator.validateObjectForNull(userDTO, "update user", LOGGER);
        userService.update(id, userDTO);
        return Response.ok().build();
    }

    @PUT
    @Path("/update/password/{id}")
    public Response updateUserPassword(@PathParam("id") final Long id, final UserPasswordUpdateDTO passwordUpdateDTO) {
        validator.validateObjectForNull(passwordUpdateDTO, "update password", LOGGER);
        validator.validateObjectForNull(id, "id from request", LOGGER);

        userService.updatePassword(id, passwordUpdateDTO);
        return Response.ok().build();
    }

    @DELETE
    @Path("/delete/{id}")
    public Response deleteUser(@PathParam("id") final Long id) {
        validator.validateObjectForNull(id,"user id", LOGGER);
        userService.delete(id);
        return Response.ok().build();
    }

    @POST
    @Path("/add")
    public Response signUser(final User user) {
        validator.validateObjectForNull(user,"user sign up form", LOGGER);
        userService.save(user);
        return Response.ok().build();
    }
}
