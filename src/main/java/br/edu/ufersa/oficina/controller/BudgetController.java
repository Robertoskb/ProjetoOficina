package br.edu.ufersa.oficina.controller;

import br.edu.ufersa.oficina.components.CardAdd;
import br.edu.ufersa.oficina.components.CardGeneric;
import br.edu.ufersa.oficina.components.CardTreatment;
import br.edu.ufersa.oficina.controller.form.BudgetForm;
import br.edu.ufersa.oficina.model.Entity.Budget;
import br.edu.ufersa.oficina.model.Entity.Car;
import br.edu.ufersa.oficina.model.Entity.Client;
import br.edu.ufersa.oficina.model.Services.BudgetService;
import br.edu.ufersa.oficina.model.Services.CarService;
import br.edu.ufersa.oficina.model.Services.ClientService;
import br.edu.ufersa.oficina.ui.ScreenManager;
import br.edu.ufersa.oficina.utils.PaginationList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class BudgetController extends TreatmentController<Budget, BudgetService> {


    public BudgetController(ScreenManager screenManager) {
        super(screenManager, new BudgetService());
    }


    @Override
    public void add() {
        try {
            FXMLLoader loader = screenManager.getScreenLoader().loader("form/budgetForm.fxml");
            loader.setController(new BudgetForm(screenManager, new Budget(), service));
            Parent view = loader.load();
            screenManager.setCenter(view);
            screenManager.show();
        } catch (Exception e) {
            alert(e.getMessage());
        }
    }

    @Override
    public void edit(int id) {
        try {
            Budget budget = service.getTreatmentById(id);
            FXMLLoader loader = screenManager.getScreenLoader().loader("form/budgetForm.fxml");
            loader.setController(new BudgetForm(screenManager, budget, service));
            Parent view = loader.load();
            screenManager.setCenter(view);
            screenManager.show();
        } catch (Exception e) {
            alert(e.getMessage());
        }
    }
}