package controller;

import db.DBConnection;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

import javafx.util.Duration;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import util.CrudUtil;

public class DashBoardFormController {
    public Label lblDate;
    public Label lblTime;
    public AnchorPane DashBoardFormContext;
    public AnchorPane DashBoard2Context;
    public Label lblEmployeeCount;


    @FXML
    private AreaChart<?, ?> OrderChart;

    public void initialize() throws SQLException, ClassNotFoundException {
       /* XYChart.Series set1=new XYChart.Series();

        set1.getData().add(new XYChart.Data("Jan",3));
        set1.getData().add(new XYChart.Data("Feb",4));
        set1.getData().add(new XYChart.Data("Mar",6));
        set1.getData().add(new XYChart.Data("Apr",20));
        set1.getData().add(new XYChart.Data("May",30));
        set1.getData().add(new XYChart.Data("Jun",37));
        set1.getData().add(new XYChart.Data("Jul",45));
        set1.getData().add(new XYChart.Data("Aug",56));
        set1.getData().add(new XYChart.Data("Sep",78));
        set1.getData().add(new XYChart.Data("Oct",80));
        set1.getData().add(new XYChart.Data("Nov",97));
        set1.getData().add(new XYChart.Data("Dec",100));

        OrderChart.getData().addAll(set1);*/

        loadDateAndTime();
        loadLabelData();
        setLineChartOrder();
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



        OrderChart.getData().add(series);
    }


    public void loadLabelData() throws SQLException, ClassNotFoundException {
        ResultSet resultSet= CrudUtil.execute("SELECT COUNT(empId) FROM Employee");
        if (resultSet.next()){
            lblEmployeeCount.setText(String.valueOf(resultSet.getInt(1)));
        }
    }


    public void EmployeeOnAction(ActionEvent actionEvent) throws IOException {
        DashBoard2Context.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/EmployeeForm.fxml"));
        DashBoard2Context.getChildren().add(parent);

    }

    public void UserOnAction(ActionEvent actionEvent) throws IOException {
        DashBoard2Context.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/UserForm.fxml"));
        DashBoard2Context.getChildren().add(parent);

    }

    public void CustomerOnAction(ActionEvent actionEvent) throws IOException {
        DashBoard2Context.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/CustomerForm.fxml"));
        DashBoard2Context.getChildren().add(parent);
    }

    public void DeliverOnAction(ActionEvent actionEvent) throws IOException {
        DashBoard2Context.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/DeliverForm.fxml"));
        DashBoard2Context.getChildren().add(parent);

    }

    public void ItemOnAction(ActionEvent actionEvent) throws IOException {
        DashBoard2Context.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/ItemForm.fxml"));
        DashBoard2Context.getChildren().add(parent);
    }

    public void SupplierOnAction(ActionEvent actionEvent) throws IOException {
        DashBoard2Context.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/SupplierForm.fxml"));
        DashBoard2Context.getChildren().add(parent);
    }

    public void PaymentOnaction(ActionEvent actionEvent) throws IOException {
        DashBoard2Context.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/PaymentForm.fxml"));
        DashBoard2Context.getChildren().add(parent);

    }

    public void OrderOnAction(ActionEvent actionEvent) throws IOException {
        DashBoard2Context.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/OrderForm.fxml"));
        DashBoard2Context.getChildren().add(parent);
    }

    public void ReportOnaction(ActionEvent actionEvent) throws IOException {
        try {
            JasperReport compileReport = (JasperReport) JRLoader.loadObject(this.getClass().getResource("/view/report/QtyChart.jasper"));
            Connection connection = DBConnection.getInstance().getConnection();
            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, null, connection);
            JasperViewer.viewReport(jasperPrint,false);

        } catch (JRException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        /*DashBoard2Context.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/ReportForm.fxml"));
        DashBoard2Context.getChildren().add(parent);*/
    }

    public void BackOnAction(ActionEvent actionEvent) throws IOException {
        setUI("LoginForm");
    }
    private void setUI(String location) throws IOException {
        Stage stage=(Stage) DashBoardFormContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/"+location+".fxml"))));
    }

    private void loadDateAndTime() {
        /* set Date*/
        lblDate.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        /* set Date*/
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            DateFormat dateFormat = new SimpleDateFormat("hh : mm : ss aa");
            String dateString = dateFormat.format(new Date()).toString();
            lblTime.setText(dateString);
        }),
                new KeyFrame(Duration.seconds(1))

        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

    public void DashBoardMouseClicked(MouseEvent mouseEvent) throws IOException {
        DashBoard2Context.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/DashBoardNewForm.fxml"));
        DashBoard2Context.getChildren().add(parent);

    }

    public void SupplierOrderOnAction(ActionEvent actionEvent) throws IOException {
        DashBoard2Context.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/supplierorderForm.fxml"));
        DashBoard2Context.getChildren().add(parent);
    }
}
