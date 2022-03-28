package steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.ru.Тогда;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.Driver;
import utils.Specifications;
import utils.Variables;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class MyStepdefs {


    private final static Logger logger = LoggerFactory.getLogger(MyStepdefs.class);
    private final static String URL = "https://reqres.in/";

    public Variables variables = Variables.getVariables();
    public Response response;

    @Тогда("^Присвоить переменной ([^\"]*) значение ([^\"]*)$")
    public void setVariables(String var, String value) {
        variables.getMap().put(var, value);
        String a1 = variables.getMap().get(var);
    }
    @Тогда("^Отправить гет запрос на endpoint ([^\"]*)$")
    public Response checkAvatarsNoPojoTest(String endpoint) {
        endpoint = Driver.validateVariables(endpoint);
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecOK200());
        response = given()
                .when()
                .get(endpoint)
                .then().log().all()
                .extract().response();
        return response;
    }
    @Тогда("^Проверить наличие следующих полей:$")
    public void checkFields(DataTable dataTable) {
        logger.info("ASD");
        Map<String, String> maps = dataTable.asMap(String.class, String.class);
        JsonPath jsonPath = response.jsonPath();

        List<String> emails = jsonPath.get("data.email");
        List<String> avatars = jsonPath.get("data.avatar");
        List<String> ids = new ArrayList<>();
        for (Map.Entry<String, String> entry : maps.entrySet()) {
            Assert.assertEquals(jsonPath.get(entry.getKey()).toString(), entry.getValue());
        }
    }


}
