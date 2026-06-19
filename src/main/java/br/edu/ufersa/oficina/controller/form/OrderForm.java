package br.edu.ufersa.oficina.controller.form;

import br.edu.ufersa.oficina.model.Entity.Order;
import br.edu.ufersa.oficina.model.Services.OrderService;
import br.edu.ufersa.oficina.ui.ScreenManager;

import java.io.IOException;

public class OrderForm extends TreatmentForm<Order, OrderService>{
    public OrderForm(ScreenManager screenManager, Order entity, OrderService service) {
        super(screenManager, entity, service, "Order.fxml");
    }

    public OrderForm(ScreenManager screenManager, OrderService service) {
        super(screenManager, service, "Order.fxml");
    }

    @Override
    public void fill(){
        super.fill();

        paidCheckBox.setSelected(entity.isCompleted());
    }

    @Override
    public void setEntityValues(){
        super.setEntityValues();

        entity.setCompleted(paidCheckBox.isSelected());
    }

}
