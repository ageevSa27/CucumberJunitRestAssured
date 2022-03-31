package steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.ru.Тогда;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.Driver;
import utils.Jdbs;
import utils.Specifications;
import utils.Variables;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static io.restassured.RestAssured.given;

public class MyStepdefs {


    private final static Logger logger = LoggerFactory.getLogger(MyStepdefs.class);
    private final static String URL = "https://reqres.in/";

    public Variables variables = Variables.getVariables();
    public Response response;
    public static Jdbs jdbs = Jdbs.getJdbs();

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

    @Тогда("^создать массив переменных  сохранить его в переменную ([^\"]*):$")
    public void variablesFields(String string, DataTable dataTable) {
        Map<String, String> maps1 = dataTable.asMap(String.class, String.class);
        Map<String, String> maps2 = new HashMap<>();
        for (Map.Entry<String, String> entry : maps1.entrySet()) {
            maps2.put(entry.getKey(), Driver.validateVariables(entry.getValue()));
        }
        variables.addMap2(string, (HashMap<String, String>) maps2);
    }

    @Тогда("^Сравнить массив ([^\"]*) и массив ([^\"]*)$")
    public void assertMap(String s1, String s2) {

        Map<String, String> map = variables.getMap2(s1);
        Map<String, String> map2 = variables.getMap2(s2);

        Assert.assertTrue("Хуево", variables.getMap2(s1).equals(variables.getMap2(s2)));


    }

    @Тогда("^Послать запрос$")
    public void select() throws IOException, SQLException {
        String query = "select * from schema1.table1";
        Properties properties = new Properties();
        properties.load(new FileInputStream("src/main/resources/jdbs.properties"));
        System.out.println(properties.getProperty("url"));


        ResultSet resultSet = DriverManager.getConnection(properties.getProperty("url"), properties.getProperty("name"),
                properties.getProperty("password")).createStatement().executeQuery(query);
        HashMap<String, String> map = new HashMap();


        int c = 1;
        while (resultSet.next()) {
            map.put(resultSet.getMetaData().getColumnName(c),
                    resultSet.getString(resultSet.getMetaData().getColumnName(c)));
            c++;
        }
        variables.addMap2("response", map);
        for (Map.Entry<String, String> x : map.entrySet()) {
            System.out.println(x.getKey() + " " + x.getValue());
        }


    }


}
