package am.egs.rest.resource;

import am.egs.ejb.dao.LoanDAO;
import am.egs.ejb.model.dto.PeriodDTO;
import am.egs.ejb.model.dto.TopFiveDTO;
import am.egs.ejb.model.pojo.Loan;
import am.egs.ejb.service.BookService;
import am.egs.ejb.service.LoanService;
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

@Path("/loan")
@Produces({APPLICATION_JSON})
@NoArgsConstructor
public class LoanResource {

    @EJB
    private LoanService loanService;
    @EJB
    private BookService bookService;
    @EJB
    private LoanDAO loanDAO;
    @EJB
    private NullValidator validator;
    private static final Logger LOGGER = LoggerFactory.getLogger(LoanResource.class);

    @GET
    @Path("/records")
    public Response getLoans() {
        final List<Loan> loans = loanService.findAll();
        GenericEntity<List<Loan>> genericEntity = new GenericEntity<List<Loan>>(loans){};

        return Response.ok(genericEntity).build();
    }

    @POST
    @Path("/records/add")
    public Response addLoan(final Loan loan) {
        validator.validateObjectForNull(loan,"loan from request", LOGGER);
        loanService.save(loan);
        return Response.ok().build();
    }

    @DELETE
    @Path("/records/delete/{id}")
    public Response deleteBookingData(@PathParam("id") final Long id) {
        validator.validateObjectForNull(id, "booking data record id", LOGGER);
        loanService.delete(id);
        bookService.incrementQuantityByOne(id);

        return Response.ok().build();
    }

    @GET
    @Path("/top/book")
    public Response getTopFiveBooks() {
        final List<TopFiveDTO> topBooks = loanService.findTopFive(null);
        GenericEntity<List<TopFiveDTO>> genericEntity = new GenericEntity<List<TopFiveDTO>>(topBooks){};

        return Response.ok(genericEntity).build();
    }

    @POST
    @Path("/top/book")
    public Response getStatistics(final PeriodDTO periodDTO) {
        final List<TopFiveDTO> topBooks = loanDAO.findByPeriod(periodDTO);
        GenericEntity<List<TopFiveDTO>> genericEntity = new GenericEntity<List<TopFiveDTO>>(topBooks){};

        return Response.ok(genericEntity).build();
    }

    @GET
    @Path("/top/book/{group-by}")
    public Response getTopFive(@PathParam("group-by") final String groupBy) {
        final List<TopFiveDTO> topBooks = loanService.findTopFive(groupBy);
        GenericEntity<List<TopFiveDTO>> genericEntity = new GenericEntity<List<TopFiveDTO>>(topBooks){};

        return Response.ok(genericEntity).build();
    }
}
