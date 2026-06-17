package br.edu.ufersa.oficina.controller;

import br.edu.ufersa.oficina.model.Entity.Budget;
import br.edu.ufersa.oficina.model.Services.BudgetService;
import br.edu.ufersa.oficina.ui.ScreenManager;

public class BudgetController extends TreatmentController<Budget, BudgetService>{
    public BudgetController(ScreenManager screenManager){
        super(screenManager, new BudgetService());
    }
}
