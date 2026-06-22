package br.edu.ufersa.oficina.controller.form;

import br.edu.ufersa.oficina.model.Entity.Budget;
import br.edu.ufersa.oficina.model.Services.BudgetService;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class BudgetController extends TransactionController<Budget, BudgetService> {


    public BudgetController(BudgetService service) {
        super(service);
    }


    @Override
    public void add() {
        try {
            FXMLLoader loader = screenManager.getScreenLoader().loader("form/budgetForm.fxml");
            loader.setController(new BudgetForm(new Budget(), service));
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
            Budget budget = service.getTransactionById(id);
            FXMLLoader loader = screenManager.getScreenLoader().loader("form/budgetForm.fxml");
            loader.setController(new BudgetForm(budget, service));
            Parent view = loader.load();
            screenManager.setCenter(view);
            screenManager.show();
        } catch (Exception e) {
            alert(e.getMessage());
        }
    }
}