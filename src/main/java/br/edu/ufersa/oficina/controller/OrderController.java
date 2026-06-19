package br.edu.ufersa.oficina.controller;

import br.edu.ufersa.oficina.components.CardTreatment;
import br.edu.ufersa.oficina.controller.form.OrderForm;
import br.edu.ufersa.oficina.controller.form.UserForm;
import br.edu.ufersa.oficina.model.Entity.Order;
import br.edu.ufersa.oficina.model.Entity.User;
import br.edu.ufersa.oficina.model.Services.OrderService;
import br.edu.ufersa.oficina.ui.ScreenManager;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

public class OrderController extends TreatmentController<Order, OrderService>{
    public OrderController(ScreenManager screenManager){
        super(screenManager, new OrderService());
    }


    @Override
    public void add() {
        try {
            FXMLLoader loader = screenManager.getScreenLoader().loader("form/orderForm.fxml");

            loader.setController(new OrderForm(screenManager, new Order(), service));

            Parent view = loader.load();

            screenManager.setCenter(view);

            screenManager.show();
        }
        catch (Exception e) {
            alert(e.getMessage());
        }
    }

    @Override
    public void edit(int id) {
        try {
            Order order = service.getTreatmentById(id);

            FXMLLoader loader = screenManager.getScreenLoader().loader("form/orderForm.fxml");

            loader.setController(new OrderForm(screenManager, order, service));

            Parent view = loader.load();

            screenManager.setCenter(view);

            screenManager.show();
        }
        catch (Exception e) {
            alert(e.getMessage());
        }
    }
}
