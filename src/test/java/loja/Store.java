package loja;


import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class Store {

    public String lerJson(String caminhoJson) throws IOException {
        return new String(Files.readAllBytes(Paths.get(caminhoJson)));
    }

    @Test
    public void criarPedido() throws IOException {
        given()
                .contentType("application/json")
                .body(lerJson("db/store.json"))
                .log().all()
        .when()
                .post("https://petstore.swagger.io/v2/store/order")
        .then()
                .log().all()
                .statusCode(200)
                .body("id", is(12893))
                .body("petId", is(81628))
                .body("quantity", is(1))
                .body("status", is("placed"))
                .body("complete", is(true))
        ;
    }
}
