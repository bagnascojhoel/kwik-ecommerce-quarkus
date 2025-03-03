package br.com.bagnascojhoel.kwik.ecommerce.product.integration;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class JsonProductIntegration {

  @Test
  public void testList() {
    given()
        .when().get("/api/products")
        .then()
        .statusCode(200)
        .body("$.size()", is(2),
            "name", containsInAnyOrder("Apple", "Pineapple"),
            "description", containsInAnyOrder("Winter fruit", "Tropical fruit"));
  }

}
