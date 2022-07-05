package controller;

import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class DashBoardNewFormController {
    public AnchorPane DashBoardNewContext;
    public Label lblEmployeeCount;


    @FXML
    private AreaChart<?, ?> SellChart;

    public void initialize() throws SQLException, ClassNotFoundException {
        setLineChartOrder();
        loadLabelData();

    }
    private void setLineChartOrder() throws SQLException, ClassNotFoundException {

        ResultSet result =CrudUtil.execute("SELECT orderDate,totalCost FROM `Order`");

        double janCost=0;
        double febCost=0;
        double marCost=0;
        double aprCost=0;
        double mayCost=0;
        double junCost=0;
        double julCost=0;
        double aguCost=0;
        double sepCost=0;
        double octCost=0;
        double novCost=0;
        double decCost=0;

        while(result.next()){
            java.sql.Date dt=result.getDate(1);
            LocalDate localDate = dt.toLocalDate();

            if(localDate.getMonthValue()==1){
                janCost+= result.getDouble(2);
            }else if(localDate.getMonthValue()==2){
                febCost+= result.getDouble(2);
            }else if(localDate.getMonthValue()==3){
                marCost+=result.getDouble(2);
            }else if (localDate.getMonthValue()==4){
                aprCost+=result.getDouble(2);
            }else if (localDate.getMonthValue()==5){
                mayCost+=result.getDouble(2);
            }else if (localDate.getMonthValue()==6){
                junCost+=result.getDouble(2);
            }else if (localDate.getMonthValue()==7){
                julCost+=result.getDouble(2);
            }else if (localDate.getMonthValue()==8){
                aguCost+=result.getDouble(2);
            }else if (localDate.getMonthValue()==9){
                sepCost+=result.getDouble(2);
            }else if (localDate.getMonthValue()==10){
                octCost+=result.getDouble(2);
            }else if (localDate.getMonthValue()==11){
                novCost+=result.getDouble(2);
            }else if (localDate.getMonthValue()==12){
                decCost+=result.getDouble(2);
            }


        }

        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Month");

        final LineChart<String, Number> lineChart =
                new LineChart<String, Number>(xAxis, yAxis);



        XYChart.Series series = new XYChart.Series();
        series.setName("Month");

        series.getData().add(new XYChart.Data("Jan", janCost));
        series.getData().add(new XYChart.Data("Feb", febCost));
        series.getData().add(new XYChart.Data("Mar", marCost));
        series.getData().add(new XYChart.Data("Apr", aprCost));
        series.getData().add(new XYChart.Data("May", mayCost));
        series.getData().add(new XYChart.Data("Jun", junCost));
        series.getData().add(new XYChart.Data("Jul", julCost));
        series.getData().add(new XYChart.Data("Aug", aguCost));
        series.getData().add(new XYChart.Data("Sep", sepCost));
        series.getData().add(new XYChart.Data("Oct", octCost));
        series.getData().add(new XYChart.Data("Nov", novCost));
        series.getData().add(new XYChart.Data("Dec", decCost));



        SellChart.getData().add(series);
    }
    public void loadLabelData() throws SQLException, ClassNotFoundException {
        ResultSet resultSet= CrudUtil.execute("SELECT COUNT(empId) FROM Employee");
        if (resultSet.next()){
            lblEmployeeCount.setText(String.valueOf(resultSet.getInt(1)));
        }
    }

}
