package am.egs.ejb.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class RestExceptionMapper implements ExceptionMapper<RestException> {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestExceptionMapper.class);

    @Override
    public Response toResponse(RestException e) {
        Response.Status status = e.getStatus();

        if (status.equals(Response.Status.INTERNAL_SERVER_ERROR)) {
            e.setReason("Something went wrong. . .");
        }

        LOGGER.error(e.getMessage());
        return Response.status(status).entity(e.getReason()).build();
    }
}
