package am.egs.rest.resource;

import am.egs.ejb.dao.BookDAO;
import am.egs.ejb.model.pojo.Book;
import am.egs.ejb.service.BookService;
import am.egs.ejb.util.NullValidator;
import am.egs.rest.filter.Secured;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;

@Path("/books")
@Produces(MediaType.APPLICATION_JSON)
@NoArgsConstructor
//@Secured
public class BookResource {

    @EJB
    private BookService bookService;
    @EJB
    private BookDAO bookDAO;
    @EJB
    private NullValidator validator;
    private static final Logger LOGGER = LoggerFactory.getLogger(BookResource.class);

    @GET
//    @RolesAllowed({"ADMIN","USER"})
    public Response getAllBooks() {
        final List<Book> books = bookService.findAll();

        GenericEntity<List<Book>> genericEntity = new GenericEntity<List<Book>>(books){};
        return Response.ok(genericEntity).build();
    }

    @Path("{id}")
    @GET
    public Response getBookById(@PathParam("id") final Long id) {
        validator.validateObjectForNull(id, "book id", LOGGER);
        final Book book = bookService.findOneById(id);
        return Response.ok(book).build();
    }

    @DELETE
    @Path("/delete/{id}")
    public Response deleteUser(@PathParam("id") final Long id) {
        validator.validateObjectForNull(id,"book id from request", LOGGER);
        bookService.delete(id);
        return Response.ok().build();
    }

    @PUT
    @Path("/update")
    public Response updateBook(final Book book) {
        validator.validateObjectForNull(book, "book from request", LOGGER);
        bookService.update(book);
        return Response.ok().build();
    }

    @POST
    @Path("/add")
    public Response signUser(final Book book) {
        validator.validateObjectForNull(book,"book to add from request", LOGGER);
        bookService.save(book);
        return Response.ok().build();
    }
}
