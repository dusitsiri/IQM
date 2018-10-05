package databases;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.OrderListInTableView;

import java.sql.*;

public class DBNewDateConnection {
    private Connection conn=null;
    private static String url="jdbc:sqlite:C:/Users/Admin/Documents/IQM2/IQMPatch3/Newdate.db";
    public Connection connect(){
        try {
            conn= DriverManager.getConnection(url);
            if(!conn.isClosed()){
                System.out.println();
                System.out.println("Connection to SQLite");
            }else{
                System.out.println("Cannot Connection to SQLite");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public ObservableList<OrderListInTableView> loadDate(){
        ObservableList<OrderListInTableView> dates = FXCollections.observableArrayList();
        OrderListInTableView orderListInTableView = null;
        try{
            conn = DriverManager.getConnection(url);
            if (conn != null){
                String query = "select * from newdate";
                Statement statement = conn.createStatement();
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
            conn = DriverManager.getConnection(url);
            if (conn != null){
                String query = "insert into newdate(id, date) values (?, ?)";
                //update database by insert new record
                PreparedStatement p = conn.prepareStatement(query);
                p.executeUpdate();
                conn.close();
            }
        } catch (SQLException e){
            System.err.println("Connection to database has problem, otherwise insertion.");
        }
    }

    public int getMaxId(){
        int maxId = 0;
        try{
            conn = DriverManager.getConnection(url);
            if (conn != null){
                String query = "select max(id) from newdate";
                //pull data in database that row form
                Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                maxId = resultSet.getInt("id");
                resultSet.close();
            }
            conn.close();
        } catch (SQLException e){
            System.err.println("Connection to database has problem, otherwise getMaxId.");
        }
        return maxId;
    }

}
