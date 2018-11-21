import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Connector {
    private static Connection connection;

    public void createConnection() throws SQLException, IOException, ClassNotFoundException {
        Properties props = new Properties();
        FileInputStream fis = new FileInputStream("src/main/db.properties");
        props.load(fis);

        // create the connection now
        connection = DriverManager.getConnection(props.getProperty("DB_URL"),
                props.getProperty("DB_USERNAME"),
                props.getProperty("DB_PASSWORD"));
    }
    public Connection getConnection(){
        return connection;
    }
}
