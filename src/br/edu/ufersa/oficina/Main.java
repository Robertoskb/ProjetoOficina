package br.edu.ufersa.oficina;

import br.edu.ufersa.oficina.DAO.BudgetDAO;
import br.edu.ufersa.oficina.DAO.OrderDAO;
import br.edu.ufersa.oficina.DAO.UserDAO;
import br.edu.ufersa.oficina.connection.ConnectionDB;
import br.edu.ufersa.oficina.entity.*;

import java.sql.Connection;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        BudgetDAO bd = new BudgetDAO();
        OrderDAO od = new OrderDAO();

        for (Budget b: bd.getAllBudget())
            b.show();

        for (Order o: od.getAllOrder()){
            o.show();
        }
    }
}
