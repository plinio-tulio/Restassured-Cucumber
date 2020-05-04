package steps;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import br.com.automacao.entity.Empregado;
import br.com.automacao.factory.EmpregadoFactory;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.Assert;

import static io.restassured.RestAssured.given;

public class EmpregadoSteps {

    public Response response;
    final private String baseURI ="http://dummy.restapiexample.com";
    final private String basePath ="/api/v1";
    Empregado empregado;

    @Before(order = 1, value = {"@api"})
    public void setUp() {
        RestAssured.baseURI = baseURI;
        RestAssured.basePath =  basePath;
    }

    @After(order = 1, value = {"@api"})
    public void deletarUsuario() {
        //NÃO PERMITE EXCLUSÃO DO REGISTRO
        given()
                .log().all()
        .when()
                .delete("/delete/"+response.jsonPath().get("data.id"))
        .then()
                .log().all()
        ;
    }

    @Given("^que efetuo o cadastro de um novo empregado valido$")
    public void que_efetuo_o_cadastro_de_um_novo_empregado_valido() throws Throwable {
        empregado = EmpregadoFactory.obterEmpregadoValido();
        response =
                given()
                        .log().all()
                        .contentType("application/json")
                        .body(empregado)
                .when()
                        .post("/create")
                .then()
                        .body("status", Matchers.is("success"))
                        .log().all()
                        .extract()
                        .response()
        ;
    }

    @When("^verifico o retorno$")
    public void verifico_o_retorno() throws Throwable {

    }

    @Then("^devo obter o retorno de sucesso$")
    public void devo_obter_o_retorno_de_sucesso() throws Throwable {
        Assert.assertEquals(response.getStatusCode(),200);
        Assert.assertEquals("success",response.jsonPath().get("status"));
    }

    @Then("^dados são gravados corretamente$")
    public void dados_sao_gravados_corretamente() throws Throwable {
        Assert.assertEquals(empregado.getName(),response.jsonPath().get("data.name"));
        Assert.assertEquals(empregado.getSalary(),response.jsonPath().get("data.salary"));
        Assert.assertEquals(empregado.getAge(),response.jsonPath().get("data.age"));
        Assert.assertThat(response.jsonPath().get("data.id"), Matchers.notNullValue());
    }
}
