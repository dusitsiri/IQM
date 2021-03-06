package databases;

import java.sql.*;

public class MySQLConnection {  //ติดต่อฐานข้อมูล MySQL
    private static final String URL="jdbc:mysql://localhost:3307/productdate";
    private static final String USER ="root";
    private static final String PASSWORD="root";
    private Connection connection;

    public Connection connect(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            this.connection= DriverManager.getConnection(URL,USER,PASSWORD);
            if(!this.connection.isClosed()){
                System.out.println("MySQL connected");
            }else
                System.out.println("MySQL connect failed");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("MySQL server was closed");
        }
        return this.connection;
    }

    public void update(String date) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            this.connection= DriverManager.getConnection(URL,USER,PASSWORD);
            if (connection != null) {
                String query = "update product set date(date) values (?)";
                PreparedStatement p = connection.prepareStatement(query);
                p.executeUpdate();
                connection.close();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Null value");
        }
    }

    public int dateDiff(String date1, String date2) {
        int computeDateDiff = 0;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection= DriverManager.getConnection(URL,USER,PASSWORD);
            if (connection != null) {
                String query = "SELECT DATEDIFF(\'"+ date1 + "\', \'"+ date2 +"\')";
                System.out.println(query);
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                while (resultSet.next()){
                    computeDateDiff = resultSet.getInt(1);
                }
                resultSet.close();
                connection.close();
            }
        } catch (ClassNotFoundException e){
            System.err.println("Finding class error");
        } catch (SQLException e) {
            System.err.println("Please connect to MySQL server before execute program");
        }
        return computeDateDiff;
    }
    public int hourDiff(String time1, String time2) {
        int hourDiff = 0;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection= DriverManager.getConnection(URL,USER,PASSWORD);
            if (connection != null) {
                String query = "SELECT TIMEDIFF(\'"+ time1 + "\', \'"+ time2 +"\')";
                System.out.println(query);
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                while (resultSet.next()){
                    hourDiff = resultSet.getInt(1);
                }
                resultSet.close();
                connection.close();
            }
        } catch (ClassNotFoundException e){
            System.err.println("Finding class error");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hourDiff;
    }
}
