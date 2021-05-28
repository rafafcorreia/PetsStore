package loja;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class User {
    public String lerJson(String caminhoJson) throws IOException {
        return new String(Files.readAllBytes(Paths.get(caminhoJson)));
    }

    @Test
    public void testPlaylist() throws IOException {
        incluirUsuario();
        consultarUsuario();
        alterarUsuario();
        deletarUsuario();
    }

    @Test
    public void incluirUsuario() throws IOException {
        given()
                .contentType("application/json")
                .log().all()
                .body(lerJson("db/user.json"))
        .when()
                .post("https://petstore.swagger.io/v2/user")
        .then()
                .log().all()
                .statusCode(200)
                .body("code", is(200))
                .body("message", is("18731279"))
        ;

    }

    @Test
    public void consultarUsuario(){
        String username = "fulanoCiclan";

        given()
                .contentType("application/json")
                .log().all()
        .when()
                .get("https://petstore.swagger.io/v2/user/" + username)
        .then()
                .log().all()
                .statusCode(200)
                .body("username", is(username))
                .body("id", is(18731279))
                .body("firstName", is("Fulano"))
                .body("lastName", is("Ciclano"))
                .body("email", is("fulanociclano@exemplo.com"))
        ;
    }

    @Test
    public void alterarUsuario() throws IOException {
       String username = "fulanoCiclan";

        given()
                .contentType("application/json")
                .log().all()
                .body(lerJson("db/userPut.json"))
        .when()
                .put("https://petstore.swagger.io/v2/user/" + username)
        .then()
                .log().all()
                .statusCode(200)
                .body("code", is(200))
                .body("message", is("18731279"))
        ;
    }

    @Test
    public void deletarUsuario(){
        String username = "ashCation";

        given()
                .contentType("application/json")
                .log().all()
        .when()
                .delete("https://petstore.swagger.io/v2/user/" + username)
        .then()
                .log().all()
                .statusCode(200)
                .body("code", is(200))
                .body("message", is(username))
        ;
    }
}
