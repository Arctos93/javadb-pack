package pl.sda.jdbc.starter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionViaDriverManager {
    private static Logger logger = LoggerFactory.getLogger(ConnectionViaDriverManager.class);

    /**
     * Url do bazy danych musi być podany w postaci: <br /><br />
     * <b>{{prefix}//{adres_serwera}:{port}/{nazwa_bazy_danych}?{parametry}</b><br /><br />
     * np.: jdbc:mysql://localhost:3306/jdbc_test?useSSL=false&serverTimezone=Europe/Warsaw<br />
     * <br />
     * <b>{prefix}</b> - identyfikuje rodzaj bazy danych z którą chcemy się połączyć, dzięki niemu można znaleźć odpowiedni sterownik do bazy <br />
     * <b>{adres_serwera}</b> - oznacza domenę lub adres IP serwera na którym znajduje się serwer bazy danych<br />
     * <b>{port}</b> - port pod którym znajduje się baza danych, dla MySQL domyślnym portem jest: 3306<br />
     * <b>{nazwa_bazy_danych}</b> - nazwa bazy z którą chcemy się połączyć<br />
     * <b>{parametry}</b> - dodatkowe parametry, które chcemy przekazać przy łączeniu się z bazą - są opcjonalne<br />
     */
    private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/classicmodels?useSSL=false&serverTimezone=Europe/Warsaw";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Gudzienki90";

    public static void main(String[] args) {
        try {
            /**
             * Krok 1: Rejestrujemy sterownik JDBC - od wersji JDBC 4.0 krok opcjonalny
             */
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            logger.error("Error during loading db driver", e);
        }

        /**
         * Krok 2: Otwieramy połączenie do bazy danych
         */
        logger.info("Connecting to a selected database...");

        /**
         * Używamy konstrukcji try-with-resources dostępnej od Java 7 do otworzenia i zamknięcia połączenia z bazą danych
         */
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)){
            logger.info("Connected database successfully...");

            /**
             * Krok 3: Pobieramy informacje o bazie danych i połączeniu
             */
            logger.info("Connection = " + connection);
            logger.info("Database name = " + connection.getCatalog());
        } catch (SQLException e) {
            /**
             * Krok 4: Obsługa wyjątków które mogą pojawić się w trakcie pracy z bazą danych
             */
            logger.error("Error during using connection", e);
        }
        /**
         * Krok 5: Zawsze zamykamy połączenie po skończonej pracy! - tutaj w sposób niejawny przez mechanizm try-with-resource
         */
    }
}