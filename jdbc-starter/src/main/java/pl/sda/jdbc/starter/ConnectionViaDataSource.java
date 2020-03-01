package pl.sda.jdbc.starter;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionViaDataSource {
    private static Logger logger = LoggerFactory.getLogger(ConnectionViaDataSource.class);

    private static final String DB_SERVER_NAME = "127.0.0.1";
    private static final String DB_NAME = "classicmodels";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Gudzienki90";
    private static final int DB_PORT = 3306;

    public static void main(String[] args) {
        /**
         * Krok 1: Tworzymy obiekt klasy DataSource
         */
        MysqlDataSource dataSource;
        try {
            dataSource = new MysqlDataSource();
            dataSource.setServerName(DB_SERVER_NAME);
            dataSource.setDatabaseName(DB_NAME);
            dataSource.setUser(DB_USER);
            dataSource.setPassword(DB_PASSWORD);
            dataSource.setPort(DB_PORT);
            dataSource.setServerTimezone("Europe/Warsaw");
            dataSource.setUseSSL(false);
            dataSource.setCharacterEncoding("UTF-8");
        } catch (SQLException e) {
            logger.error("Error during creating MysqlDataSource", e);
            return;
        }

        logger.info("Connecting to a selected database...");

        /**
         * Krok 2: Otwieramy połączenie do bazy danych
         */
//        Connection connection = null;
        try (Connection connection = dataSource.getConnection()){
//            connection = dataSource.getConnection();
            logger.info("Connected database successfully...");

            /**
             * Krok 3: Pobieramy informacje o bazie danych i połączeniu
             */
            logger.info("Connection = " + connection);
            logger.info("Database name = " + connection.getCatalog());
        } catch(SQLException e){
            /**
             * Krok 4: Obsługa wyjątków które mogą pojawić się w trakcie pracy z bazą danych
             */
            logger.error("Error during using connection", e);
        }
        /**
         * Krok 5: Zawsze zamykamy połączenie po skończonej pracy!
         */

    }
}

