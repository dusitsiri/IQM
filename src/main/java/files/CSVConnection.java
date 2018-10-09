package files;

import com.opencsv.CSVReader;
import databases.DBCsvConnection;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;

public class CSVConnection {
    private Connection connection;

    public CSVConnection(Connection connection){
        this.connection = connection;
    }

    public static void main(String[] args) {
        DBCsvConnection dbCsvConnection = new DBCsvConnection();
        Connection connection = dbCsvConnection.connect();
        CSVConnection csvConnection = new CSVConnection(connection);

        dbCsvConnection.insert(dbCsvConnection.getMaxId()+1, "Mr.", "Dusit", "Siri", "97/60", "0848656486");

//        CSVReader reader = null;
//        try{
//            reader = new CSVReader(new FileReader("data1.csv"));
//            String[] line;
//            while((line = reader.readNext()) != null){
//                System.out.print(line[0]+" ");
//                System.out.print(line[1]+" ");
//                System.out.print(line[2]+" ");
//                System.out.println(line[3]);
//            }
//            reader.close();
//        } catch (IOException e ){
//            System.err.println("Cannot open file");
//        }
    }
}
