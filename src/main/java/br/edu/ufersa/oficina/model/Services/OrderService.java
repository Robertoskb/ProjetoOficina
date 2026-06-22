package br.edu.ufersa.oficina.model.Services;

import br.edu.ufersa.oficina.model.DAO.OrderDAO;
import br.edu.ufersa.oficina.model.Entity.Order;

import java.util.ArrayList;

public class OrderService extends TransactionService<Order, OrderDAO> {
    public OrderService(){
        super(new OrderDAO());
    }

    public ArrayList<Order> getTransactionCompleteThisMonth(){
        return transactionDAO.getTransactionCompleteThisMonth();
    }

    public ArrayList<Order> getTransactionPaidPending(){
        return transactionDAO.getTransactionPaidPending();
    }

    public ArrayList<Order> getTransactionInProgress(){
        return transactionDAO.getTransactionInProgress();
    }

    @Override
    public void finish(int id) {
        Order order = getTransactionById(id);

        order.finish();

        update(order);
    }
}
