package loja;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;


public class Pet {

    public String lerJson(String caminhoJson) throws IOException {
        return new String(Files.readAllBytes(Paths.get(caminhoJson)));
    }

    @Test
    public void ordemDaExecucao() throws IOException {
        incluirPet();
        consultarPet();
        alterarPet();
        excluirPet();
    }

    //Create/Incluir/POST
    @Test
    public void incluirPet() throws IOException {
        //Ler o conteúdo do arquivo pet.json
        String jsonBody = lerJson("db/pet.json");

        given() // Dado que
            .contentType("application/json")  //Tipo de conteúdo dessa requisição
                                              //"text/xml" para web services comuns
                                              //"application/json" para APIs REST
            .log().all()                      //Gerar um log completo da requisição
            .body(jsonBody)                   //Conteúdo do corpo da requisição
        .when()                               //Quando
            .post("https://petstore.swagger.io/v2/pet") //Operação e endpoint
        .then()                               //Então
            .log().all()                      //Gerar um log completo da resposta
            .statusCode(200)                  //Validou o código de status da requisição como 200
            .body("id", is(81628))  //Validou a tag id com o conteúdo esperado
            .body("name", is("Cristal")) // Validou a tag name como Esmeralda
            .body("tags.name", contains("adoption")) //Validou a tag name filha da tag tags
        ;
        System.out.println("Executou o serviço");
    }

    //Reach or Research/Consultar/GET
    @Test
    public void consultarPet(){
        String petId = "81628";

        given()                              //Dado que
            .contentType("application/json") //Tipo de conteúdo dessa requisição
                                             //"text/xml" para web services comuns
                                             //"application/json" para APIs REST
            .log().all()                     //"Gerar um log completo da requisição
        .when()                              //Quando
            .get("https://petstore.swagger.io/v2/pet/" + petId) //Consulta pelo petId
        .then()                              //Então
            .log().all()                     //Gerar um log completo da resposta
            .statusCode(200)                 //Validou que a operação foi realizada
            .body("name", is ("Cristal")) //Validou o nome do pet
            .body("category.name", is("cat")) //Validou a espécie do pet
        ;

    }

    //Update/Alterar ou Atualizar/PUT
    @Test
    public void alterarPet() throws IOException {
        String jsonBody = lerJson("db/petPut.json");
        given()
            .contentType("application/json")  //Tipo de conteúdo dessa requisição
                                              //"text/xml" para web services comuns
                                              //"application/json" para APIs REST
            .log().all()                      //Gerar um log completo da requisição
            .body(jsonBody)                   //Conteúdo do corpo da requisição
        .when()
            .post("https://petstore.swagger.io/v2/pet")
        .then()
            .log().all()
            .statusCode(200)
            .body("name", is("Cristal"))
            .body("id", is(81628))
            .body("status", is("adopted"))
        ;
    }

    //Delete/Excluir/Delete
    @Test
    public void excluirPet(){
        String petId = "81628";

        given()                                //Dado que
            .contentType("application/json")   //Tipo de conteúdo dessa requisição
                                               //"text/xml" para web services comuns
                                               //"application/json" para APIs REST
            .log().all()                       //"Gerar um log completo da requisição
        .when()                                //Quando
            .delete("https://petstore.swagger.io/v2/pet/" + petId) //Deleta pelo petId
        .then()
            .log().all()
            .statusCode(200)
            .body("code", is(200))
        ;

    }

    //Login
    @Test
    public void loginUser(){
        String token =
        given()
            .contentType("application/json")
            .log().all()
        .when()
            .get("https://petstore.swagger.io/v2/user/login?username=esmeralda&password=joia")
        .then()
            .log().all()
            .statusCode(200)
            .body("message", containsString("logged in user session:"))
            .extract()
            .path("message")
        ;
        System.out.println("O token válido é: " + token.substring(23));

    }

}
