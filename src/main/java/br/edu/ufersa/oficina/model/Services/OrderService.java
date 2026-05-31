package br.edu.ufersa.oficina.model.Services;

import br.edu.ufersa.oficina.model.DAO.OrderDAO;
import br.edu.ufersa.oficina.model.entity.Order;

public class OrderService extends TreatmentService<Order>{
    public OrderService(){
        super(new OrderDAO());
    }
}
