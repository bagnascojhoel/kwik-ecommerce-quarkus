package br.com.bagnascojhoel.kwik.ecommerce.auth.e2e;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import io.quarkus.test.junit.QuarkusIntegrationTest;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

@QuarkusIntegrationTest
public class JwtFlowE2eTest {

  @Test
  public void successfulLoginViaRestApi() {
    final String body = "{\"user\":\"kwik-admin\",\"password\":\"integrationTests\"}";

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
    final String body = "{\"user\":\"kwik-admin\",\"password\":\"integrationTests\"}";

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
    final String body = "{\"user\":\"kwik-admin\",\"password\":\"integrationTests\"}";

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
