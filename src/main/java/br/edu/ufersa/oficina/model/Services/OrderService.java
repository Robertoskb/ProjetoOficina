package br.edu.ufersa.oficina.model.Services;

import br.edu.ufersa.oficina.model.DAO.OrderDAO;
import br.edu.ufersa.oficina.model.Entity.Order;

public class OrderService extends TransactionService<Order> {
    public OrderService(){
        super(new OrderDAO());
    }

    @Override
    public void finish(int id) {
        Order order = getTransactionById(id);

        order.finish();

        update(order);
    }
}
