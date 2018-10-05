import databases.DBNewDateConnection;
import databases.MySQLConnection;
import models.OrderListInTableView;

import java.sql.*;
import java.util.ArrayList;

public class ChangeDatabase {
    private Connection connection;

    private ChangeDatabase(Connection connection) {
        this.connection = connection;
    }

    private ArrayList<OrderListInTableView> findAllDates() throws SQLException {
        ArrayList<OrderListInTableView> dates = new ArrayList<>();
        OrderListInTableView orderListInTableView = null;

        String sqlText = "select * from product";
        Statement statement = this.connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sqlText);

        while (resultSet.next()) {
            orderListInTableView = new OrderListInTableView();
            orderListInTableView.setDate(resultSet.getString("date"));
            orderListInTableView.setDate(orderListInTableView.getDate());
            dates.add(orderListInTableView);
        }
        resultSet.close();
        statement.close();
        return dates;
    }

    private void add(OrderListInTableView orderListInTableView) throws SQLException {
        OrderListInTableView newDate = orderListInTableView;

        String sqlText = "insert into newdate(date) values (?)";

        PreparedStatement preparedStatement = this.connection.prepareStatement(sqlText);
        preparedStatement.setString(1, newDate.getDate());

        preparedStatement.executeUpdate(); //มันอัพเดทค่าที่เราอินเสิทเข้าไปให้เลย  บรรทัดนนี้มันจะรีเทินค่ากลับมาเป็นจำนวนเรคคอร์ด ที่แอดเข้าไป คือ นับเป็นแถว1เเถว
        preparedStatement.close();
    }

    public static void main(String[] args) throws SQLException {
        MySQLConnection mySQLConnection = new MySQLConnection();
        Connection connection = mySQLConnection.connect();
        ChangeDatabase changeDatabase = new ChangeDatabase(connection);

        ArrayList<OrderListInTableView> dates = changeDatabase.findAllDates();
        System.out.println("MySQL database-----------");

        for (OrderListInTableView date : dates) {
            if (date.getDate().isEmpty()){
                date.setDate("n/a");
                mySQLConnection.update(date.getDate());
            }
            System.out.println("Date from mysql: " + date.getDate());
        }

        DBNewDateConnection dbNewDateConnection = new DBNewDateConnection();
        Connection con = dbNewDateConnection.connect();
        ChangeDatabase changeDatabase1 = new ChangeDatabase(con);
        System.out.println("Sqlite datebase----------");

        for (OrderListInTableView date : dates) {
            changeDatabase1.add(date);
            System.out.println("Date in sqlite: " + date.getDate());
        }
    }
}
