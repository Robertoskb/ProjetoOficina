package br.edu.ufersa.oficina.model.Services;

import br.edu.ufersa.oficina.Exceptions.MecException;
import br.edu.ufersa.oficina.model.DAO.BudgetDAO;
import br.edu.ufersa.oficina.model.Entity.Budget;
import br.edu.ufersa.oficina.model.Entity.Order;

public class BudgetService extends TreatmentService<Budget> {
    public BudgetService(){
        super(new BudgetDAO());
    }

    public void createOrder(Budget budget){
        int id = budget.getId();

        if (treatmentDAO.getTreatmentById(id) == null)
            throw new MecException("Atendimento não encontrado");

        new OrderService().insert(budget.createOrder());
    }

    @Override
    public void finish(int id) {
        Budget budget = getTreatmentById(id);

        Order order = budget.createOrder();

        update(budget);

        new OrderService().insert(order);
    }


}
