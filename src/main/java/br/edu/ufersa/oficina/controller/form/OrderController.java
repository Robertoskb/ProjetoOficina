package br.edu.ufersa.oficina.controller.form;

import br.edu.ufersa.oficina.model.Entity.Order;
import br.edu.ufersa.oficina.model.Services.OrderService;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class OrderController extends TransactionController<Order, OrderService> {
    public OrderController(OrderService service) {
        super(service);
    }

    @Override
    public void add() {
        try {
            FXMLLoader loader = screenManager.getScreenLoader().loader("form/orderForm.fxml");
            loader.setController(new OrderForm(new Order(), service));
            Parent view = loader.load();
            screenManager.setCenter(view);
            screenManager.show();
        } catch (Exception e) {
            alert(e.getMessage());
        }
    }

    @Override
    public void edit(int id) {
        try {
            Order order = service.getTransactionById(id);
            FXMLLoader loader = screenManager.getScreenLoader().loader("form/orderForm.fxml");
            loader.setController(new OrderForm(order, service));
            Parent view = loader.load();
            screenManager.setCenter(view);
            screenManager.show();
        } catch (Exception e) {
            alert(e.getMessage());
        }
    }
}