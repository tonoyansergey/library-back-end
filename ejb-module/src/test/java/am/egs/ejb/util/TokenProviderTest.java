package am.egs.ejb.util;

import am.egs.ejb.model.UserRole;
import am.egs.ejb.model.dto.UserDTO;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.*;

import static org.assertj.core.api.Assertions.*;

class TokenProviderTest {

    private TokenProvider tokenProvider;
    private UserDTO user;
    private String token;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        this.tokenProvider = new TokenProvider();
        this.user = UserDTO.builder()
                .id(1L)
                .firstName("Tom")
                .lastName("Smith")
                .userName("tommy")
                .email("some@mail.ru")
                .role(UserRole.USER)
                .build();
        this.token = tokenProvider.createToken(user);
    }

//    @org.junit.jupiter.api.Test
//    @DisplayName("Creating token")
//    void createToken() {
//
//        assertNotNull(this.token);
//    }

    @org.junit.jupiter.api.Test
    @DisplayName("Validate token")
    void validateToken() {
        DecodedJWT jwt = tokenProvider.validateToken(token);
        assertEquals(jwt.getClaim("role").asString(), user.getRole().toString());
    }
}
