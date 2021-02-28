package am.egs.rest.filter;

import am.egs.ejb.util.TokenProvider;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.jboss.resteasy.core.ResourceMethodInvoker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.lang.reflect.Method;

@Provider
@Secured
public class OAuthFilter implements ContainerRequestFilter {

    @EJB
    private TokenProvider tokenProvider;
    private static final Logger LOGGER = LoggerFactory.getLogger(OAuthFilter.class);

    @Override
    public void filter(ContainerRequestContext containerRequestContext) {
        LOGGER.info("filter working on request");

        ResourceMethodInvoker methodInvoker = (ResourceMethodInvoker) containerRequestContext.getProperty("org.jboss.resteasy.core.ResourceMethodInvoker");
        Method method = methodInvoker.getMethod();




        if (!method.isAnnotationPresent(PermitAll.class)) {
            if (method.isAnnotationPresent(DenyAll.class)) {
                LOGGER.info("access is denied for this resource");
                containerRequestContext.abortWith(
                        Response.status(Response.Status.FORBIDDEN).entity("Access is denied").build());
                return;
            }
        }

        String authToken = containerRequestContext.getHeaderString("auth");
        if (authToken == null || !authToken.startsWith("Bearer")) {
            LOGGER.info("token is not valid: {}", authToken);
            containerRequestContext.abortWith(
                    Response.status(Response.Status.UNAUTHORIZED).entity("Authorization token is not valid").build());
            return;
        }

        DecodedJWT jwt = null;
        try {
            jwt = tokenProvider.validateToken(authToken);
        } catch (Exception e) {
            LOGGER.info("token is not valid: {}", authToken);
            containerRequestContext.abortWith(
                    Response.status(Response.Status.UNAUTHORIZED).entity("Authorization token is not valid").build());
            return;
        }

        RolesAllowed rolesAllowed = null;
        if (method.isAnnotationPresent(RolesAllowed.class)) {
            rolesAllowed = method.getAnnotation(RolesAllowed.class);
        }

        String userRole = jwt.getClaim("role").asString();
        LOGGER.info("checking if user can access resource with role: {}", userRole);

        String[] methodRoles = rolesAllowed.value();
        boolean roleAllowed = false;
        for (String role: methodRoles) {
            if (role.equals(userRole)) {
                roleAllowed = true;
                break;
            }
        }

        if (!roleAllowed) {
                LOGGER.info("no permission to access resource");
                containerRequestContext.abortWith(
                        Response.status(Response.Status.FORBIDDEN).entity("Access is denied").build());
                return;
        }
    }
}
