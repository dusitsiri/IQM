package controllers;

import databases.MySQLConnection;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;

import java.sql.Connection;
import java.time.LocalDate;

public class DateDiffController {

    @FXML
    private Label answerDateDiffLabel, chooseDatesDiffLabel;

    @FXML
    private Button dateDiffButton;

    @FXML
    private DatePicker firstDatePicker, secondDatePicker;

    private LocalDate firstDatePicked;
    private LocalDate secondDatePicked;

    //Choose two dates
    public void initialize() {
        firstDatePicker.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                firstDatePicked = firstDatePicker.getValue();
            }
        });
        secondDatePicker.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                secondDatePicked = secondDatePicker.getValue();
                if (firstDatePicked != null && secondDatePicked != null) {
                    chooseDatesDiffLabel.setText("1st date chose:  " + firstDatePicked + "\n2nd date chose: " + secondDatePicked);
                }
            }
        });
    }

    @FXML
    public void handle_DateDiff_Button(ActionEvent event) {
        MySQLConnection mySQLConnection = new MySQLConnection();
        Connection connection = mySQLConnection.connect();  //connect to MySQL database
        try {
            if (firstDatePicked != null && secondDatePicked != null && connection != null) {
                //use function DATEDIFF
                int datediff = Math.abs(mySQLConnection.dateDiff(String.valueOf(firstDatePicked), String.valueOf(secondDatePicked)));
                answerDateDiffLabel.setText("Datediff: " + String.valueOf(datediff));
                System.out.println("DATEDIFF: " + String.valueOf(datediff));
            }
            //reset
            firstDatePicker.setValue(null);
            secondDatePicker.setValue(null);
            firstDatePicker.requestFocus();
        } catch (NullPointerException e){
            System.err.println("Connection is failed");
        }
    }
}
