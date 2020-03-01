package pl.sda.jdbc.starter;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {

//    private static final String DB_SERVER_NAME = "127.0.0.1";
//    private static final String DB_NAME = "classicmodels";
//    private static final String DB_USER = "root";
//    private static final String DB_PASSWORD = "Gudzienki90";
//    private static final int DB_PORT = 3306;

    private static Logger logger = LoggerFactory.getLogger(ConnectionFactory.class);

    private MysqlDataSource dataSource = new MysqlDataSource();

    public ConnectionFactory(String filename) {
//        MysqlDataSource dataSource;
        Properties dataBaseProperties = getDataBaseProperties(filename);
        try {
//            dataSource = new MysqlDataSource();
            dataSource.setServerName(dataBaseProperties.getProperty("pl.sda.jdbc.db.server"));
            dataSource.setDatabaseName(dataBaseProperties.getProperty("pl.sda.jdbc.db.name"));
            dataSource.setUser(dataBaseProperties.getProperty("pl.sda.jdbc.db.user"));
            dataSource.setPassword(dataBaseProperties.getProperty("pl.sda.jdbc.db.password"));
            dataSource.setPort(Integer.parseInt(dataBaseProperties.getProperty("pl.sda.jdbc.db.port")));
            dataSource.setServerTimezone("Europe/Warsaw");
            dataSource.setUseSSL(false);
            dataSource.setCharacterEncoding("UTF-8");
        } catch (SQLException e) {
            logger.error("Error during creating MysqlDataSource", e);
        }
    }

    public ConnectionFactory(){
        this("database.properties");
//        this("/database.properties"); dodając / przed nazwą pliku poszerzamy zakres przeszukiwania
//        pliku ("ścieżka bezwlgędna w katalogu z zasobami")
    }

//    private  MysqlDataSource createDataSource(){
//        try {
//            dataSource = new MysqlDataSource();
//            dataSource.setServerName(DB_SERVER_NAME);
//            dataSource.setDatabaseName(DB_NAME);
//            dataSource.setUser(DB_USER);
//            dataSource.setPassword(DB_PASSWORD);
//            dataSource.setPort(DB_PORT);
//            dataSource.setServerTimezone("Europe/Warsaw");
//            dataSource.setUseSSL(false);
//            dataSource.setCharacterEncoding("UTF-8");
//        } catch (SQLException e) {
//            logger.error("Error during creating MysqlDataSource", e);
//        }
//        return dataSource;
//    }

    private Properties getDataBaseProperties(String filename) {
        Properties properties = new Properties();
        try {
            /**
             * Pobieramy zawartość pliku za pomocą classloadera, plik musi znajdować się w katalogu ustawionym w CLASSPATH
             */
            InputStream propertiesStream = ConnectionFactory.class.getClassLoader().getResourceAsStream(filename);
            // dodając wyżej / nie musimy dodawać getClassLoader
            if(propertiesStream == null) {
                throw new IllegalArgumentException("Can't find file: " + filename);
            }
            /**
             * Pobieramy dane z pliku i umieszczamy w obiekcie klasy Properties
             */
            properties.load(propertiesStream);
        } catch (IOException e) {
            logger.error("Error during fetching properties for database", e);
            return null;
        }

        return properties;
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public static void main(String[] args) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        try (Connection connection = connectionFactory.getConnection()){
            logger.info("Connected database successfully...");
            logger.info("Connection = " + connection);
            logger.info("Database name = " + connection.getCatalog());
        } catch(SQLException e){
            logger.error("Error during using connection", e);
        }
    }
}