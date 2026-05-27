package br.edu.ufersa.oficina.model.Services;

import br.edu.ufersa.oficina.model.DAO.BudgetDAO;
import br.edu.ufersa.oficina.Exceptions.MecException;
import br.edu.ufersa.oficina.Exceptions.MecNotFoundException;
import br.edu.ufersa.oficina.model.entity.Budget;
import br.edu.ufersa.oficina.model.entity.Car;
import br.edu.ufersa.oficina.model.entity.Client;

import java.time.LocalDate;
import java.util.ArrayList;

public class BudgetService {
    private final BudgetDAO budgetDAO = new BudgetDAO();

    public Budget getBudgetById(int id){
        Budget budget = budgetDAO.getTreatmentById(id);

        if (budget == null)
            throw new MecNotFoundException("Orçamento não encontrado");

        return budget;
    }

    public ArrayList<Budget> getBudgetByClient(Client client){
        return budgetDAO.getTreatmentByClient(client);
    }

    public ArrayList<Budget> getBudgetByCar(Car car){
        return budgetDAO.getTreatmentByCar(car);
    }

    public ArrayList<Budget> getBudgetByPeriod(LocalDate start, LocalDate end){
        if (start.isAfter(end))
            throw new MecException("Período inválido");

        return budgetDAO.getTreatmentByPeriod(start, end);
    }

    public void addBudget(Budget budget) {
        if (budget.getPrice() <= 0)
            throw new MecException("Preço inválido");

        if (budget.getServices().isEmpty() && budget.getParts().isEmpty())
            throw new MecException("Orçamento vazio de serviços e peças");


        budgetDAO.addBudget(budget);
    }

    public void updateBudget(Budget budget){
        if (budgetDAO.getTreatmentById(budget.getId()) == null)
            throw new MecNotFoundException("Orçamento não encontrado");

        if (budget.getPrice() <= 0)
            throw new MecException("Preço inválido");

        if (budget.getServices().isEmpty() && budget.getParts().isEmpty())
            throw new MecException("Orçamento vazio de serviços e peças");

        budgetDAO.updateBudget(budget);
    }

    public BudgetDAO getBudgetDAO() {
        return budgetDAO;
    }
}
