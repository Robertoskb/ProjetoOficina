package br.edu.ufersa.oficina.controller.form;

import br.edu.ufersa.oficina.model.Entity.Service;
import br.edu.ufersa.oficina.model.Services.ServiceService;
import br.edu.ufersa.oficina.ui.ScreenManager;

import java.io.IOException;

public class ServiceForm extends Form<Service, ServiceService> {
    public ServiceForm(ScreenManager screenManager, Service entity, ServiceService service) {
        super(screenManager, entity, service);
    }

    public ServiceForm(ScreenManager screenManager, ServiceService service) {
        super(screenManager, service);
    }

    @Override
    public void fill() {

    }

    @Override
    public void setEntityValues() {

    }

    @Override
    public void save() throws IOException {

    }
}
