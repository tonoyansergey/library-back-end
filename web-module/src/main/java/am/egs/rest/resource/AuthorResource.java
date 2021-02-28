package am.egs.rest.resource;

import am.egs.ejb.model.pojo.Author;
import am.egs.ejb.model.pojo.Genre;
import am.egs.ejb.service.AuthorService;
import am.egs.ejb.util.NullValidator;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/authors")
@Produces(MediaType.APPLICATION_JSON)
@NoArgsConstructor
public class AuthorResource {

    @EJB
    private AuthorService authorService;
    @EJB
    private NullValidator validator;
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorResource.class);

    @GET
    public Response getAllAuthors() {
        final List<Author> authors = authorService.findAll();

        GenericEntity<List<Author>> genericEntity = new GenericEntity<List<Author>>(authors){};
        return Response.ok(genericEntity).build();
    }

    @POST
    @Path("/add")
    public Response signUser(final Author author) {
        validator.validateObjectForNull(author,"author from request", LOGGER);
        authorService.save(author);
        return Response.ok().build();
    }
}
