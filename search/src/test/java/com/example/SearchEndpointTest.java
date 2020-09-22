package com.example;

import io.quarkus.test.Mock;
import io.quarkus.test.junit.QuarkusTest;
import io.vertx.core.json.JsonObject;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class SearchEndpointTest {

    @Mock
    public static class QueryForwarderMock extends QueryForwarder {
        @Override
        public void send(JsonObject payload) {
            System.out.println("should've sent " + payload.toString());
        }
    }


    @Test
    public void testHelloEndpoint() {
        given().queryParam("userId", "Michal")
                .queryParam("query", "java")
          .when().get("/search")
          .then()
             .statusCode(200)
             .body(containsString("quarkus"));
    }

}