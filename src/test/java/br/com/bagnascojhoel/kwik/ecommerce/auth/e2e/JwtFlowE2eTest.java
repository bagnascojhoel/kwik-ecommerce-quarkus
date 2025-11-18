package br.com.bagnascojhoel.kwik.ecommerce.auth.e2e;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import br.com.bagnascojhoel.kwik.ecommerce.auth.domain.user.UserRepository;
import br.com.bagnascojhoel.kwik.ecommerce.auth.infra_driven.library.PasswordEncryptionServiceAdapter;
import br.com.bagnascojhoel.kwik.ecommerce.auth.object_mother.UserMother;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.response.Response;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class JwtFlowE2eTest {

  @Inject
  UserRepository userRepository;

  @BeforeEach
  @Transactional
  public void setup() {
    // Ensure a known user exists for the tests
    userRepository.persist(UserMother.kwikAdmin(new PasswordEncryptionServiceAdapter()));
  }

  @Test
  public void successfulLoginViaRestApi() {
    final String body = "{\"user\":\"kwik-admin\",\"password\":\"password\"}";

    given()
        .contentType("application/json")
        .body(body)
        .when()
        .post("/api/auth/flows/jwt/login")
        .then()
        .statusCode(200)
        .cookie("jwt", notNullValue());
  }

  @Test
  public void refreshTokenViaRestApi() {
    final String body = "{\"user\":\"kwik-admin\",\"password\":\"password\"}";

    Response loginResponse =
        given().contentType("application/json").body(body).post("/api/auth/flows/jwt/login");

    String jwtCookie = loginResponse.getCookie("jwt");

    given()
        .cookie("jwt", jwtCookie)
        .contentType("application/json")
        .body("{}")
        .post("/api/auth/flows/jwt/refresh")
        .then()
        .statusCode(200)
        .cookie("jwt");
  }

  @Test
  public void checkStateViaRestApi() {
    final String body = "{\"user\":\"kwik-admin\",\"password\":\"password\"}";

    Response loginResponse =
        given().contentType("application/json").body(body).post("/api/auth/flows/jwt/login");

    String jwtCookie = loginResponse.getCookie("jwt");

    given()
        .cookie("jwt", jwtCookie)
        .contentType("application/json")
        .body("{}")
        .post("/api/auth/flows/jwt/check-state")
        .then()
        .statusCode(200)
        .body("valid", equalTo(true));
  }
}
