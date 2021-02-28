package am.egs.ejb.exception;

import lombok.Data;
import javax.ejb.ApplicationException;
import javax.ws.rs.core.Response;

@ApplicationException
@Data
public class RestException extends RuntimeException {

    private Response.Status status;
    private String reason;

    public RestException(final String message, final Response.Status status, final  String reason) {
        super(message);
        this.status = status;
        this.reason = reason;
    }

    public RestException(final Response.Status status, final  String reason) {
        this.status = status;
        this.reason = reason;
    }

    public RestException(final String message) {
        super(message);
    }

    public RestException(final String message, final Response.Status status) {
        super(message);
        this.status = status;
    }
}
