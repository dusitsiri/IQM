package controllers;

import databases.DBOrderListConnection;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import models.OrderListInTableView;

import java.time.LocalDate;

public class DatabaseController {
    public static DBOrderListConnection dbOrderListConnection = new DBOrderListConnection();
    public static ObservableList<OrderListInTableView> dbLoadFiveMenu;
    public static ObservableList<OrderListInTableView> dbLoadAllMenu;

    @FXML
    private TableView<OrderListInTableView> databaseTableView;
    @FXML
    private TextField textPrice, textQty;
    @FXML
    private Label labelAvgTotalPrice, labelSumTotalPrice, labelAllProducts;
    @FXML
    private DatePicker datePicker = null;
    @FXML
    private Button btnOpenNewWindow;
    private LocalDate datePicked;
    private int min = 99999999;
    private int max, stringDatePicked = 0;
    private boolean checkDate = true;

    //Initialize
    public void initialize() {
        setDatabaseTableView();
        datePicker.setOnAction(new EventHandler() {
            public void handle(Event t) {
                datePicked = datePicker.getValue();
                if (datePicker != null && datePicked != null) {
                    String[] splitDatePicked = String.valueOf(datePicked).split("-");
                    stringDatePicked = Integer.parseInt(splitDatePicked[0] + splitDatePicked[1] + splitDatePicked[2]);
                    System.err.println("Selected date: " + datePicked);
                }
                if (dbLoadFiveMenu.size() > 0 && datePicker != null && datePicked != null) {
                    findMinDate();
                    findMaxDate();
                    for (int i = 0; i < dbLoadFiveMenu.size(); i++) {
                        String[] dateWithSlash = dbLoadFiveMenu.get(i).getDate().split("/");
                        String dateNoSlash = dateWithSlash[0] + dateWithSlash[1] + dateWithSlash[2];
                        int eachDate = Integer.parseInt(dateNoSlash);
                        if ((stringDatePicked == eachDate) || (stringDatePicked >= min && stringDatePicked <= max) ||
                                (min > stringDatePicked)) {
                            checkDate = false;
                            Alert alert = new Alert(Alert.AlertType.WARNING, "วันที่ดังกล่าวไม่สามารถเลือกได้ กรุณาเลือกวันที่ใหม่อีกครั้ง", ButtonType.CLOSE);
                            alert.show();
                            datePicker.setValue(null);
                            clearAndLoadData();
                            break;
                        } else checkDate = true;
                    }
                }
            }
        });
    }

    public void findMinDate() {
        min = 99999999;
        for (int i = 0; i < dbLoadFiveMenu.size(); i++) {
            String[] dateWithSlash = dbLoadFiveMenu.get(i).getDate().split("/");
            String dateNoSlash = dateWithSlash[0] + dateWithSlash[1] + dateWithSlash[2];
            int eachDate = Integer.parseInt(dateNoSlash);
            if (min > eachDate) {
                min = eachDate;
            }
        }
    }

    public void findMaxDate() {
        max = 0;
        for (int i = 0; i < dbLoadFiveMenu.size(); i++) {
            String[] dateWithSlash = dbLoadFiveMenu.get(i).getDate().split("/");
            String dateNoSlash = dateWithSlash[0] + dateWithSlash[1] + dateWithSlash[2];
            int eachDate = Integer.parseInt(dateNoSlash);
            if (max < eachDate) {
                max = eachDate;
            }
        }
    }

    public void addMenu() {
        if (datePicked != null && !textPrice.getText().isEmpty() && !textQty.getText().isEmpty()) {
            if (textPrice.getText().matches("[0-9]+") && textQty.getText().matches("[0-9]+") && checkDate == true) {
                saveDataIntoDatabase();
                datePicker.setValue(null);
                clearAndLoadData();
            } else if (!textPrice.getText().matches("[0-9]+") && !textQty.getText().matches("[0-9]+")) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "กรุณาระบุราคาต่อหน่วยและจำนวนเป็นตัวเลข", ButtonType.CLOSE);
                alert.show();
                clearAndLoadData();
            } else if (!textPrice.getText().matches("[0-9]+") && textQty.getText().matches("[0-9]+")) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "กรุณาระบุราคาต่อหน่วยเป็นตัวเลข", ButtonType.CLOSE);
                alert.show();
                textPrice.clear();
            } else if (textPrice.getText().matches("[0-9]+") && !textQty.getText().matches("[0-9]+")) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "กรุณาระบุจำนวนเป็นตัวเลข", ButtonType.CLOSE);
                alert.show();
                textQty.clear();
            }
        } else if (datePicked == null || textPrice.getText().isEmpty() || textQty.getText().isEmpty()) {
            if (datePicked == null && !textPrice.getText().isEmpty() && !textQty.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "กรุณาเลือกวันที่", ButtonType.CLOSE);
                alert.show();
            } else if (datePicked != null && textPrice.getText().isEmpty() && !textQty.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "กรุณากรอกราคาต่อหน่วย", ButtonType.CLOSE);
                alert.show();
            } else if (datePicked != null && !textPrice.getText().isEmpty() && textQty.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "กรุณากรอกจำนวนสินค้า", ButtonType.CLOSE);
                alert.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING, "กรุณากรอกข้อมูลให้ครบถ้วน", ButtonType.CLOSE);
                alert.show();
            }
        }

    }

    public void setDatabaseTableView() {
        dbLoadFiveMenu = dbOrderListConnection.showFiveOrderList();
        dbLoadAllMenu = dbOrderListConnection.loadMenu();
        databaseTableView.setItems(dbLoadFiveMenu);
        calculatorTotalAverage();
    }

    public void saveDataIntoDatabase() {
        int id = dbOrderListConnection.getCreateID();
        String[] splitDatePicked = String.valueOf(datePicked).split("-");
        int date = Integer.parseInt(splitDatePicked[0] + splitDatePicked[1] + splitDatePicked[2]);
        double price = Double.parseDouble(textPrice.getText());
        int quality = Integer.parseInt(textQty.getText());
        double totalPrice = Double.parseDouble(String.valueOf(price * quality));
        dbOrderListConnection.saveDB(id, date, price, quality, totalPrice);
        setDatabaseTableView();
    }

    public void calculatorTotalAverage() {
        double avgTotalPrice, totalPriceAll  = 0;
        for (int i = 0; i < dbLoadAllMenu.size(); i++) {
            totalPriceAll += dbLoadAllMenu.get(i).getTotalPrice();
        }
        avgTotalPrice = totalPriceAll / dbLoadAllMenu.size();
//        labelSumTotalPrice.setText(String.valueOf(totalPriceAll));
//        labelAllProducts.setText(String.valueOf(dbLoadAllMenu.size()));
        if (dbLoadAllMenu.size()>0){
            labelAvgTotalPrice.setText(String.format(String.format("%.2f", avgTotalPrice)));
        }else labelAvgTotalPrice.setText("0.00");
    }

    public void clearAndLoadData() {
        textPrice.clear();
        textQty.clear();
    }

    public void deleteMenu() {
        try {
            if (!databaseTableView.getSelectionModel().getSelectedItems().equals(null) && dbLoadFiveMenu.size() > 0) {
                String[] dateList = databaseTableView.getSelectionModel().getSelectedItem().getDate().split("/");
                int dateRecord = Integer.parseInt(dateList[0] + dateList[1] + dateList[2]);
                dbOrderListConnection.deleteDB(dateRecord);
                setDatabaseTableView();
            }
        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "กรุณาเลือกรายการที่จะลบ", ButtonType.CLOSE);
            alert.show();
        }
    }
}
