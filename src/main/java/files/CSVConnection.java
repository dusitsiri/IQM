package files;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class CSVConnection {
    public static void main(String[] args) {
        try{
            BufferedReader reader = new BufferedReader(new FileReader("name.txt"));
            String line = "";
            while((line = reader.readLine()) != null){
                System.out.println(line);
            }
            reader.close();
        } catch (IOException e ){
            System.err.println("Cannot open file");
        }
    }
}
