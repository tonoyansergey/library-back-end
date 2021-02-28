package am.egs.ejb.util;

import am.egs.ejb.model.dto.UserDTO;
import am.egs.ejb.model.pojo.Admin;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.RandomStringUtils;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.ejb.Stateless;
import java.util.Date;

@Stateless
@NoArgsConstructor
public class TokenProvider {

    private static final String KEY = RandomStringUtils.random(10, true, true);
    private static final Logger LOGGER = LoggerFactory.getLogger(TokenProvider.class);

    public String createToken(final UserDTO userDTO) {
        LocalDate localDate = LocalDate.now().plusDays(1);
        Date date = java.sql.Date.valueOf(String.valueOf(localDate));

        Algorithm algorithm = Algorithm.HMAC256(KEY);
        String role = userDTO.getRole().toString();
        LOGGER.info("creating new token for user: {}, with role: {}", userDTO.getUserName(), role);

        return JWT.create()
                .withExpiresAt(date)
                .withClaim("role", role)
                .sign(algorithm);
    }

    public String createAdminToken(final Admin admin) {
        LocalDate localDate = LocalDate.now().plusDays(1);
        Date date = java.sql.Date.valueOf(String.valueOf(localDate));

        Algorithm algorithm = Algorithm.HMAC256(KEY);
        String role = admin.getRole().toString();
        LOGGER.info("creating new token for admin: {}, with role: {}", admin.getUserName(), role);

        return JWT.create()
                .withExpiresAt(date)
                .withClaim("role", role)
                .sign(algorithm);
    }

    public DecodedJWT validateToken(String token) throws SignatureVerificationException {
        token = token.substring(7);
        LOGGER.info("verifying token: {}", token);

        Algorithm algorithm = Algorithm.HMAC256(KEY);
        JWTVerifier verifier = JWT.require(algorithm).build();

        return verifier.verify(token);
    }
}
