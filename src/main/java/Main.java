import controllers.DatabaseController;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.OrderList;
import models.OrderListInTableView;

import java.util.Scanner;

public class Main extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/database.fxml"));
        primaryStage.setTitle("Database Order");
        primaryStage.setScene(new Scene(root, 1028, 640));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
