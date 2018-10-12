package databases;

import java.sql.*;

public class DBCsvConnection {
    private static final String URL = "jdbc:sqlite:datacsv.db";
    private Connection connection;

    public Connection connect(){
        try{
            connection = DriverManager.getConnection(URL);
            if (!connection.isClosed()){
                System.out.println("Connected datacsv.db ");
            } else{
                System.out.println("Cannot connect datacsv.db");
            }
        } catch (SQLException e){
            System.err.println("Connection failed");
        }
        return connection;
    }

    public int getMaxId(){
        int maxId = 0;
        try{
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(URL);
            if (!connection.isClosed()) {
                String query = "select max(id) from datacsv";
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                maxId = resultSet.getInt(1);
                return maxId;
            }
        } catch (ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }
        return 1;
    }

    public void insert(int id, String title, String firstname, String lastname, String address, String telephone){
        try{
            if (!connection.isClosed()) {
                String query = "insert into datacsv(id, title, firstname, lastname, address, telephone) " +
                        "values (\'"+id+"\',\'"+title+"\',\'"+firstname+"\',\'"+lastname+"\',\'"+address+"\',\'"+telephone+"\')";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.executeUpdate();
                System.out.println("Insertion success");
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}
