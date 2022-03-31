package utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Jdbs {
    public Connection connection;
    private static Jdbs jdbs;

    private Jdbs() {
    }

    public static Jdbs getJdbs() {
        if (jdbs == null) {
            jdbs = new Jdbs();
        }
        return jdbs;
    }

    public Connection getCnnection() throws IOException, SQLException {
        Properties properties = new Properties();
        FileInputStream in = new FileInputStream("src/main/resources/jdbs.properties");
        properties.load(in);


        this.connection = DriverManager.getConnection(properties.getProperty("url"), properties.getProperty("user"),
                properties.getProperty("password"));

        return connection;
    }
}
