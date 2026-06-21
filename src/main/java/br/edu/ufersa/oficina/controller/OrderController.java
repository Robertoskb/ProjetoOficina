package br.edu.ufersa.oficina.controller;

import br.edu.ufersa.oficina.components.CardAdd;
import br.edu.ufersa.oficina.components.CardGeneric;
import br.edu.ufersa.oficina.components.CardTransaction;
import br.edu.ufersa.oficina.controller.form.OrderForm;
import br.edu.ufersa.oficina.model.Entity.Order;
import br.edu.ufersa.oficina.model.Entity.Car;
import br.edu.ufersa.oficina.model.Entity.Client;
import br.edu.ufersa.oficina.model.Services.OrderService;
import br.edu.ufersa.oficina.model.Services.CarService;
import br.edu.ufersa.oficina.model.Services.ClientService;
import br.edu.ufersa.oficina.ui.ScreenManager;
import br.edu.ufersa.oficina.utils.PaginationList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class OrderController extends TransactionController<Order, OrderService> {
    public OrderController(ScreenManager screenManager) {
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
        } catch (Exception e) {
            alert(e.getMessage());
        }
    }

    @Override
    public void edit(int id) {
        try {
            Order order = service.getTransactionById(id);
            FXMLLoader loader = screenManager.getScreenLoader().loader("form/orderForm.fxml");
            loader.setController(new OrderForm(screenManager, order, service));
            Parent view = loader.load();
            screenManager.setCenter(view);
            screenManager.show();
        } catch (Exception e) {
            alert(e.getMessage());
        }
    }
}