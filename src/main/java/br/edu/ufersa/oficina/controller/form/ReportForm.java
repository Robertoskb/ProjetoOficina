package br.edu.ufersa.oficina.controller.form;

import br.edu.ufersa.oficina.controller.BaseController;
import br.edu.ufersa.oficina.model.Entity.Budget;
import br.edu.ufersa.oficina.model.Entity.Order;
import br.edu.ufersa.oficina.model.Services.BudgetService;
import br.edu.ufersa.oficina.model.Services.OrderService;
import br.edu.ufersa.oficina.ui.ScreenManager;
import br.edu.ufersa.oficina.utils.ReportGenerator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;

public class ReportForm extends BaseController {
    @FXML private ComboBox<String> reportTypeComboBox;

    @FXML private TextField titleField;

    @FXML private DatePicker startDatePicker;

    @FXML private DatePicker endDatePicker;

    @FXML private TextField pathField;

    @FXML private Button browseButton;

    @FXML private Button generateButton;

    private final OrderService orderService = new OrderService();
    private final BudgetService budgetService = new BudgetService();

    public ReportForm(ScreenManager screenManager) {
        super(screenManager);
    }

    public void initialize(){
        reportTypeComboBox.getItems().addAll("Orçamento", "Ordem de Serviço");
        reportTypeComboBox.setValue(reportTypeComboBox.getItems().get(0));

        endDatePicker.setValue(LocalDate.now());

    }

    @FXML public void browse(ActionEvent event){
        FileChooser fileChooser = new FileChooser();

        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter(
                        "PDF",
                        "*.pdf"
                )
        );

        fileChooser.setInitialFileName(titleField.getText());

        Window stage = ((Node) event.getSource()).getScene().getWindow();

        File selectedFile = fileChooser.showSaveDialog(stage);

        if (selectedFile != null) {
            pathField.setText(selectedFile.getAbsolutePath());
        }
    }

    @FXML public void generate(){
        if (titleField.getText().trim().isEmpty() || pathField.getText().trim().isEmpty())
            return;

        if (startDatePicker.getValue() == null || endDatePicker.getValue() == null)
            return;

        LocalDate start = startDatePicker.getValue();
        LocalDate end = endDatePicker.getValue().plusDays(1);

        try {
            if (reportTypeComboBox.getValue().equals(reportTypeComboBox.getItems().get(0))) {
                ArrayList<Budget> budgets = budgetService.getTreatmentByPeriod(start, end);
                for (Budget budget : budgets) {
                    budget.setParts(budgetService.getPartsByTreatment(budget.getId()));
                    budget.setServices(budgetService.getServiceByTreatment(budget.getId()));
                }

                generateBudgetReport(budgets);
            }

            if (reportTypeComboBox.getValue().equals(reportTypeComboBox.getItems().get(1))) {
                ArrayList<Order> orders = orderService.getTreatmentByPeriod(start, end);
                for (Order order : orders) {
                    order.setParts(orderService.getPartsByTreatment(order.getId()));
                    order.setServices(orderService.getServiceByTreatment(order.getId()));
                }

                generateOrderReport(orders);
            }

            success("Relatório salvo com sucesso!");
        }

        catch (Exception e){
            alert(e.getMessage());
        }
    }

    public void generateBudgetReport(ArrayList<Budget> budgets) throws IOException {
        ReportGenerator.generateReport(titleField.getText().trim(), budgets, Paths.get(pathField.getText()));
    }

    public void generateOrderReport(ArrayList<Order> orders) throws IOException {
        ReportGenerator.generateReport(titleField.getText().trim(), orders, Paths.get(pathField.getText()));
    }
}
