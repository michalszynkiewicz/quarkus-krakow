package com.example;

import io.quarkus.test.Mock;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;

@QuarkusTest
public class SearchEndpointTest {

    @Mock
    public static class QueryForwarderMock extends QueryForwarder {
        public void send(String userId, String query) {
            System.out.println("should've sent " + userId + " , " + query);
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