package br.edu.ufersa.oficina.ui;

import br.edu.ufersa.oficina.Exceptions.MecNotFoundException;
import br.edu.ufersa.oficina.controller.*;
import br.edu.ufersa.oficina.controller.Paginator.*;
import br.edu.ufersa.oficina.controller.form.ReportForm;
import br.edu.ufersa.oficina.model.Services.*;

public class ControllerFactory {
    private final UserService userService = new UserService();
    private final ClientService clientService = new ClientService();
    private final CarService carService = new CarService();
    private final PartService partService = new PartService();
    private final ServiceService serviceService = new ServiceService();
    private final BudgetService budgetService = new BudgetService();
    private final OrderService orderService = new OrderService();

    public Object create(Class<?> clazz) {
        if (clazz == MainController.class)
            return new MainController();

        if (clazz == LoginController.class)
            return new LoginController(userService);

        if (clazz == MenuController.class)
            return new MenuController(orderService);

        if (clazz == ClientController.class)
            return new ClientController(clientService);

        if (clazz == CarController.class)
            return new CarController(carService);

        if (clazz == PartController.class)
            return new PartController(partService);

        if (clazz == ServiceController.class)
            return new ServiceController(serviceService);

        if (clazz == BudgetController.class)
            return new BudgetController(budgetService);

        if (clazz == OrderController.class)
            return new OrderController(orderService);

        if (clazz == UserController.class)
            return new UserController(userService);

        if (clazz == ReportForm.class)
            return new ReportForm(orderService, budgetService);


        throw new MecNotFoundException("Controller não encontrado");
    }
}
