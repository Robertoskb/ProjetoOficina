package br.edu.ufersa.oficina.controller.form;

import br.edu.ufersa.oficina.model.Entity.Client;
import br.edu.ufersa.oficina.model.Services.ClientService;
import br.edu.ufersa.oficina.ui.ScreenManager;

import java.io.IOException;

public class ClientForm extends Form<Client, ClientService>{
    public ClientForm(ScreenManager screenManager, Client entity, ClientService service) {
        super(screenManager, entity, service, "");
    }

    public ClientForm(ScreenManager screenManager, ClientService service) {
        super(screenManager, service, "");
    }

    @Override
    public void fill() {

    }

    @Override
    public void setEntityValues() {

    }

}
