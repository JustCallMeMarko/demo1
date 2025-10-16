package com.example.demo1;

import com.example.demo1.Backend.Admin;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;

import java.time.LocalDate;
import java.time.Month;
import java.util.Map;

public class AnalyticsController {

    @FXML
    private BarChart<String, Number> weeklySalesChart;

    @FXML
    private LineChart<String, Number> monthlySalesChart;

    @FXML
    private BarChart<String, Number> performanceChart;

    @FXML
    private ChoiceBox<String> monthChoice;

    @FXML
    private Label salesLabel;

    @FXML
    private Label transactionsLabel;

    @FXML
    public void initialize() {
        for (Month m : Month.values()) {
            monthChoice.getItems().add(m.name());
        }
        String currentMonth = LocalDate.now().getMonth().name();
        monthChoice.setValue(currentMonth);

        updateMonthlyData(currentMonth);

        monthChoice.setOnAction(e -> updateMonthlyData(monthChoice.getValue()));
        addWeekly();
        addMonthly();
        addUserSales();
    }

    private void updateMonthlyData(String monthName) {
        int year = LocalDate.now().getYear();
        int monthNumber = Month.valueOf(monthName).getValue(); // convert name → number (1–12)

        Map<String, Integer> data = Admin.getSalesByMonth(year, monthNumber);

        salesLabel.setText("₱" + data.getOrDefault("total_sales", 0));
        transactionsLabel.setText("Transactions: " + data.getOrDefault("total_transactions", 0));
    }

    public void addWeekly(){
        Map<String, Integer> weeklySales = Admin.getWeeklySales();

        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Day of Week");
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Total Sales");

        weeklySalesChart.setTitle("Sales for the Last 7 Days");

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Weekly Sales");

        for (Map.Entry<String, Integer> entry : weeklySales.entrySet()) {
            series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }

        weeklySalesChart.getData().add(series);
    }

    public void addMonthly(){
        int year = LocalDate.now().getYear(); // example year, can be made dynamic
        Map<String, Integer> revenueData = Admin.getMonthlyRevenue(year);

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Monthly Revenue - " + year);

        for (Map.Entry<String, Integer> entry : revenueData.entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue());
            series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }

        monthlySalesChart.getData().add(series);
    }

    private void addUserSales() {
        Map<String, Integer> userSales = Admin.getUserPerformance();

        // Create a new series for the chart
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Total Sales");

        // Add data from the Map
        for (Map.Entry<String, Integer> entry : userSales.entrySet()) {
            series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }

        performanceChart.getData().clear();
        performanceChart.getData().add(series);
    }
}
