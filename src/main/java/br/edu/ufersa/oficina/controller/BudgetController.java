package br.edu.ufersa.oficina.controller;

import br.edu.ufersa.oficina.controller.form.BudgetForm;
import br.edu.ufersa.oficina.controller.form.UserForm;
import br.edu.ufersa.oficina.model.Entity.Budget;
import br.edu.ufersa.oficina.model.Entity.User;
import br.edu.ufersa.oficina.model.Services.BudgetService;
import br.edu.ufersa.oficina.ui.ScreenManager;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class BudgetController extends TreatmentController<Budget, BudgetService>{
    public BudgetController(ScreenManager screenManager){
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
        }
        catch (Exception e) {
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
        }
        catch (Exception e) {
            alert(e.getMessage());
        }
    }
}
