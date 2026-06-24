package br.edu.ufersa.oficina.controller.form;

import br.edu.ufersa.oficina.model.Entity.Budget;
import br.edu.ufersa.oficina.model.Services.BudgetService;
import br.edu.ufersa.oficina.model.Services.CarService;
import br.edu.ufersa.oficina.model.Services.PartService;
import br.edu.ufersa.oficina.model.Services.ServiceService;
import br.edu.ufersa.oficina.ui.ScreenManager;

public class BudgetForm extends TransactionForm<Budget, BudgetService>{
    public BudgetForm(Budget entity, BudgetService service, CarService carService, PartService partService, ServiceService serviceService) {
        super(entity, service, "Budget.fxml", carService, partService, serviceService);
    }

}
