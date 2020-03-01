package pl.sda.jdbc.starter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ClassicModelsManager {
    private static Logger logger = LoggerFactory.getLogger(ConnectionViaDataSource.class);

    public void printAllOffices() {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        try (Statement statement = connectionFactory.getConnection().createStatement()) {
            String query = "select * from offices";
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                for (int i = 1; i < result.getMetaData().getColumnCount() + 1; i++) {
                    System.out.print(" " + result.getMetaData().getColumnName(i) + " = " + result.getObject(i) + " | ");
                }
                System.out.println(" ");
            }
//            while (result.next()) {
//
////result.getMetaData().getCatalogName() // poczytać
//                int id = result.getInt("officeCode");
//                String city = result.getString("city");
//                String phone = result.getString("phone");
//                String addressLine1 = result.getString("addressLine1");
//                logger.info("officeCode: {}", id);
//                logger.info("city: {}", city);
//                logger.info("addressLine1: {}", addressLine1);
//                logger.info("phone:{}", phone);
//            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateProductPrices() {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        try (Statement statement = connectionFactory.getConnection().createStatement()) {
            String query = "UPDATE products SET buyPrice = buyPrice*1.1 ";
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void printAllCustomersWithSalesRepName() {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        try (Statement statement = connectionFactory.getConnection().createStatement()) {
            String query = "select  customers.contactLastName,employees.firstName,employees.lastName from customers right join employees on customers.salesRepEmployeeNumber=employees.EmployeeNumber";
            ResultSet result = statement.executeQuery(query);
            System.out.println(result);
            while (result.next()) {
                for (int i = 1; i < result.getMetaData().getColumnCount() + 1; i++) {
                    System.out.print(" " + result.getMetaData().getColumnName(i) + " = " + result.getObject(i) + " | ");
                }
                System.out.println(" ");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Product> findProductByName(String nameMatcher){

        ConnectionFactory connectionFactory = new ConnectionFactory();
        /**
         * Gdyby zamiast like ? było wpisane "%" + nameMatcher + "%", to bazadanych jest podatna na ataki
         * hackerskie i ściągnięcie lub nawet edycję danych gdy bedzię użyty executeUpdate
         */
        String query = "select * from products where productName like  ?";
        List<Product> productsList = new ArrayList<>();
        try(PreparedStatement statement = connectionFactory.getConnection().prepareStatement(query)) {
                statement.setString(1, "%" + nameMatcher + "%");
            ResultSet result = statement.executeQuery();
            //wyciągnąć każdy wiersz i dodać do products zwrócic listę
            while (result.next()) {
                Product product = new Product();
                product.setProductCode(result.getString("productCode"));
                product.setProductName(result.getString("productName"));
                product.setProductLine(result.getString("productLine"));
                product.setProductScale(result.getString("productScale"));
                product.setProductVendor(result.getString("productVendor"));
                product.setProductDescription(result.getString("productDescription"));
                productsList.add(product);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return productsList;
    }

    public List<Order> findOrdersByDate(Date from, Date to){
    ConnectionFactory connectionFactory = new ConnectionFactory();
    String query = "select * from orders where orderDate between ? and  ? ";
    List<Order> orderList = new ArrayList<>();
        try(PreparedStatement statement = connectionFactory.getConnection().prepareStatement(query)) {
        statement.setDate(1, from);
        statement.setDate(1, to);
        ResultSet result = statement.executeQuery();
        while (result.next()) {
            Order order = new Order();
            order.setOrderNumber(result.getInt("orderNumber"));
            order.setOrderDate(result.getDate("orderDate"));
            order.setRequiredDate(result.getDate("requiredDate"));
            order.setShippedDate(result.getDate("shippedDate"));
            order.setStatus(result.getString("status"));
            order.setComments(result.getString("comments"));
            order.setCustomerNumber(result.getInt("customerNumber"));
            orderList.add(order);

        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

        return orderList;
}

    public static void main(String[] args) {
        ClassicModelsManager classicModelsManager = new ClassicModelsManager();

        /**
         * Żeby lista ładniej sie wyświetliłą można przejść  fori/foreach po każdym produkcie/wierszu
         * osobno lub wykorzystać stream
         */
//            System.out.println(productsList.get(i).getProductName());
//            System.out.println(productsList.get(i).getProductName());
        List<Product> productsList = classicModelsManager.findProductByName("ford");
        System.out.println(productsList);

//        List<Order> orderList = classicModelsManager.findOrdersByDate(2003-01-05, 2003-02-18);


    }





//        System.out.println("Menu: " +
//                "\n 1.Wyświetle wszystkie info o biurach " +
//                "\n 2.Zwiększ ceny wszystkich produktów o 10% " +
//                "\n 3.Wyświetl sprzedawców przypisanych do klientów");
//
//        Scanner scanner = new Scanner(System.in);
//        int chose = scanner.nextInt();
//
//        switch (chose) {
//            case 1:
//                classicModelsManager.printAllOffices();
//            case 2:
//                classicModelsManager.updateProductPrices();
//            case 3:
//                classicModelsManager.printAllCustomersWithSalesRepName();
//        }
    }


