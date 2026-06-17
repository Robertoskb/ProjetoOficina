package br.edu.ufersa.oficina.controller;

import br.edu.ufersa.oficina.model.Entity.Order;
import br.edu.ufersa.oficina.model.Services.OrderService;
import br.edu.ufersa.oficina.ui.ScreenManager;

public class OrderController extends TreatmentController<Order, OrderService>{
    public OrderController(ScreenManager screenManager){
        super(screenManager, new OrderService());
    }
}
