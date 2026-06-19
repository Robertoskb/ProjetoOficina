package br.edu.ufersa.oficina.controller.form;

import br.edu.ufersa.oficina.model.Entity.Budget;
import br.edu.ufersa.oficina.model.Services.BudgetService;
import br.edu.ufersa.oficina.ui.ScreenManager;

public class BudgetForm extends TreatmentForm<Budget, BudgetService>{
    public BudgetForm(ScreenManager screenManager, Budget entity, BudgetService service) {
        super(screenManager, entity, service, "Budget.fxml");
    }

    public BudgetForm(ScreenManager screenManager, BudgetService service) {
        super(screenManager, service, "Budget.fxml");
    }
}
