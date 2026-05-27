package br.edu.ufersa.oficina.model.Services;

import br.edu.ufersa.oficina.model.DAO.BudgetDAO;
import br.edu.ufersa.oficina.model.entity.Budget;

public class BudgetService extends TreatmentService<Budget> {
    public BudgetService(){
        super(new BudgetDAO());
    }
}
