package br.edu.ufersa.oficina.controller.Paginator;

import br.edu.ufersa.oficina.controller.form.BudgetForm;
import br.edu.ufersa.oficina.model.Entity.Budget;
import br.edu.ufersa.oficina.model.Services.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class BudgetController extends TransactionController<Budget, BudgetService> {


    public BudgetController(BudgetService service, ClientService clientService, CarService carService, PartService partService, ServiceService serviceService) {
        super(service, clientService, carService, partService, serviceService);
    }


    @Override
    public void add() {
        try {
            FXMLLoader loader = screenManager.loader("form/budgetForm.fxml");
            loader.setController(new BudgetForm(new Budget(), service, carService, partService, serviceService));
            Parent view = loader.load();
            screenManager.setCenter(view);
            screenManager.show();
        } catch (Exception e) {
            error(e.getMessage());
        }
    }

    @Override
    public void edit(int id) {
        try {
            Budget budget = service.getTransactionById(id);
            FXMLLoader loader = screenManager.loader("form/budgetForm.fxml");
            loader.setController(new BudgetForm(budget, service, carService, partService, serviceService));
            Parent view = loader.load();
            screenManager.setCenter(view);
            screenManager.show();
        } catch (Exception e) {
            error(e.getMessage());
        }
    }
}