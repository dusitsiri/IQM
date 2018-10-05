package databases;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import models.OrderList;
import models.OrderListInTableView;

import java.sql.*;
import java.util.ArrayList;

public class DBOrderListConnection {
    public int getCreateID() {
        try {
            Class.forName("org.sqlite.JDBC");
            String dbURL = "jdbc:sqlite:OrderList.db";
            Connection connection = DriverManager.getConnection(dbURL);
            if (connection != null) {
                String query = "Select max(id) from OrderList";
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                int minID = resultSet.getInt(1);
                connection.close();
                return minID;
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return 1;
    }

    public ObservableList loadMenu() {
        ObservableList<OrderListInTableView> menu = FXCollections.observableArrayList();
        try {
            Class.forName("org.sqlite.JDBC");
            String dbURL = "jdbc:sqlite:OrderList.db";
            Connection conn = DriverManager.getConnection(dbURL);
            if (conn != null) {
                String query = "select * from OrderList";
                Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                while (resultSet.next()) {
                    int id = resultSet.getInt(1);
                    int numDate = resultSet.getInt(2);
                    String year = String.valueOf(numDate).substring(0, 4);
                    String month = String.valueOf(numDate).substring(4, 6);
                    String day = String.valueOf(numDate).substring(6, String.valueOf(numDate).length());
                    String date = year + "-" + month + "-" + day;
                    Double price = resultSet.getDouble(3);
                    int qty = resultSet.getInt(4);
                    Double totalPrice = resultSet.getDouble(5);
                    menu.add(new OrderListInTableView(id, date, price, qty, totalPrice));
                }
                conn.close();
            }
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }
        return menu;
    }


    public void saveDB(int id, int date, double price, int qty, double totalPrice) {
        try {
            Class.forName("org.sqlite.JDBC");
            String dbURL = "jdbc:sqlite:OrderList.db";
            Connection  connection = DriverManager.getConnection(dbURL);
            if (connection != null) {
                String query = "insert into OrderList(id, date, price, qty, totalPrice) values (\'" + id + "\', \'" + date + "\', \'" + price + "\', \'" + qty + "\', \'" + totalPrice + "')";
                PreparedStatement p = connection.prepareStatement(query);
                p.executeUpdate();
                connection.close();
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
    public ObservableList showFiveOrderList() {
        ObservableList<OrderListInTableView> menu = FXCollections.observableArrayList();
        try {
            Class.forName("org.sqlite.JDBC");
            String dbURL = "jdbc:sqlite:OrderList.db";
            Connection connection = DriverManager.getConnection(dbURL);
            if (connection != null) {
                String query = "SELECT * FROM OrderList ORDER BY date DESC LIMIT 5";
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                while (resultSet.next()) {
                    int id = resultSet.getInt(1);
                    int numDate = resultSet.getInt(2);
                    String year = String.valueOf(numDate).substring(0, 4);
                    String month = String.valueOf(numDate).substring(4, 6);
                    String day = String.valueOf(numDate).substring(6, String.valueOf(numDate).length());
                    String date = year + "-" + month + "-" + day;
                    Double price = resultSet.getDouble(3);
                    int qty = resultSet.getInt(4);
                    Double totalPrice = resultSet.getDouble(5);
                    menu.add(new OrderListInTableView(id, date, price, qty, totalPrice));
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return menu;
    }
    public void editDB(int id, int date, double price, int qty, double totalPrice) {
        try {
            Class.forName("org.sqlite.JDBC");
            String dbURL = "jdbc:sqlite:OrderList.db";
            Connection connection = DriverManager.getConnection(dbURL);
            if (connection != null) {
                String query = "update OrderList set id=\'" + id + "\' ,date=\'" + date + "\' ,price=\'" + price + "\' ,qty=\'" + qty + "\' , totalPrice=\'"+totalPrice+"\' where date == \'" + date + "\'";
                System.out.println(query);
                PreparedStatement p = connection.prepareStatement(query);
                p.executeUpdate();
                connection.close();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void deleteDB(int date) {
        try {
            System.err.println("Delete date: " + date);
            Class.forName("org.sqlite.JDBC");
            String dbURL = "jdbc:sqlite:OrderList.db";
            Connection connection = DriverManager.getConnection(dbURL);
            if (connection != null) {
                String query = "Delete from OrderList where date == \'" + date + "\' ";
                PreparedStatement p = connection.prepareStatement(query);
                p.executeUpdate();
                connection.close();
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }



}
