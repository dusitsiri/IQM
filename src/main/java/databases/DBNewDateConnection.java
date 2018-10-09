package databases;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.OrderListInTableView;

import java.sql.*;

public class DBNewDateConnection {  //ติดต่อฐานข้อมูล Newdate.db

    private Connection connection=null;
    private static String url="jdbc:sqlite:C:/Users/Admin/Documents/IQM2/IQMPatch3/Newdate.db";

    public Connection connect(){
        try {
            Class.forName("org.sqlite.JDBC");
            connection= DriverManager.getConnection(url);
            if(!connection.isClosed()){
                System.out.println();
                System.out.println("Connection to SQLite");
            }else{
                System.out.println("Cannot Connection to SQLite");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public ObservableList<OrderListInTableView> loadDate(){
        ObservableList<OrderListInTableView> dates = FXCollections.observableArrayList();
        OrderListInTableView orderListInTableView = null;
        try{
            connection = DriverManager.getConnection(url);
            if (connection != null){
                String query = "select * from newdate";
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                while (resultSet.next()){
                    orderListInTableView = new OrderListInTableView();
                    orderListInTableView.setId(resultSet.getInt("id"));
                    orderListInTableView.setDate(resultSet.getString("date"));
                    orderListInTableView.setId(orderListInTableView.getId());
                    orderListInTableView.setDate(orderListInTableView.getDate());
                    dates.add(orderListInTableView);
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return dates;
    }

    public void saveDate(int id, String date){
        try{
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(url);
            if (connection != null){
                String query = "insert into newdate(id, date) values (?, ?)";
                //update database by insert new record
                PreparedStatement p = connection.prepareStatement(query);
                p.executeUpdate();
                connection.close();
            }
        } catch (SQLException e){
            System.err.println("Connection to database has problem, otherwise insertion.");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public int getMaxId(){
        int maxId = 0;
        try{
            connection = DriverManager.getConnection(url);
            if (connection != null){
                String query = "select max(id) from newdate";
                //pull data in database that row form
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                maxId = resultSet.getInt("id");
                resultSet.close();
            }
            connection.close();
        } catch (SQLException e){
            System.err.println("Connection to database has problem, otherwise getMaxId.");
        }
        return maxId;
    }

}
