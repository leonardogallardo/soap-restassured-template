package servicos;

import geral.BaseTest;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import utils.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static custom_requests.CustomRestAssured.givenSoap;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.hasXPath;

@RunWith(Parameterized.class)
public class ExampleTest extends BaseTest {

    @Parameterized.Parameter(0)
    public String nome;

    @Parameterized.Parameter(1)
    public String origem;

    @Test
    public void positivoTest() {

        // Criando input do teste
        Map<String, Object> env = jsonToMap("environment.json");
        env.put("origem", origem);

        Map<String, Object> mapa = new HashMap<>();
        mapa.put("env", env);

        // Teste
        ValidatableResponse response =
            givenSoap(Url.get("TestService"), "action")
                .body(Payload.get("service01/payload01.xml", mapa))
            .when()
                .post()
            .then()
                .statusCode(200)
                .body(hasXPath("//nome", equalTo(env.get(nome).toString())));

        mapa.put("transf", Xml.filter(response, "//subElement"));

        givenSoap(Url.get("TestService"), "action")
            .body(Payload.get("service01/payload02.xml", mapa))
        .when()
            .post()
        .then()
            .statusCode(200)
            .body(not(containsString("<error>")));

    }

    @Parameterized.Parameters(name = "{index}: {0}")
    public static Collection<Object[]> getParametersPositivo() {
        return Arrays.asList(
                new Object[] { "Ano 0", "IB"},
                new Object[] { "Ano 1", "IB"},
                new Object[] { "Ano 2", "IB"});
    }


}
