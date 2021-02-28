package am.egs.rest.resource;

import am.egs.ejb.model.pojo.Genre;
import am.egs.ejb.service.GenreService;
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

@Path("/genres")
@Produces(MediaType.APPLICATION_JSON)
@NoArgsConstructor
public class GenreResource {

    @EJB
    private GenreService genreService;
    @EJB
    private NullValidator validator;
    private static final Logger LOGGER = LoggerFactory.getLogger(GenreResource.class);

    @GET
    public Response getAllGenres() {
        final List<Genre> genres = genreService.findAll();

        GenericEntity<List<Genre>> genericEntity = new GenericEntity<List<Genre>>(genres){};
        return Response.ok(genericEntity).build();
    }

    @POST
    @Path("/add")
    public Response signUser(final Genre genre) {
        validator.validateObjectForNull(genre,"genre from request", LOGGER);
        genreService.save(genre);
        return Response.ok().build();
    }
}
