package controllers;

import databases.MySQLConnection;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;

import java.sql.Connection;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class DateDiffController {

    @FXML
    private Label answerDateDiffLabel, chooseDatesDiffLabel, answerHourDiffLabel, time1Label, time2Label;

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
                String time1 = time1Label.getText().substring(0, 5);
                String time2 = time2Label.getText().substring(0, 5);
                int hourdiff = Math.abs(mySQLConnection.hourDiff(time1, time2));

                //setText datediff and hourdiff
                answerDateDiffLabel.setText("DateDiff: " + String.valueOf(datediff));
                answerHourDiffLabel.setText("HourDiff: " + String.valueOf(hourdiff));

                //show datediff, hourdiff and setText in fxml
                System.out.println("DATEDIFF: " + String.valueOf(datediff));
                System.out.println("HOURDIFF: " + String.valueOf(hourdiff));
                chooseDatesDiffLabel.setText("1st date chose:  " + firstDatePicked + "\n2nd date chose: " + secondDatePicked);

                //reset
                firstDatePicker.setValue(null);
                secondDatePicker.setValue(null);
                firstDatePicker.requestFocus();
            }
        } catch (NullPointerException e){
            System.err.println("Connection is failed");
        }
    }
}
