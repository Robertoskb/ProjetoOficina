package br.edu.ufersa.oficina.controller.form;

import br.edu.ufersa.oficina.model.Entity.Part;
import br.edu.ufersa.oficina.model.Services.PartsService;
import br.edu.ufersa.oficina.ui.ScreenManager;

import java.io.IOException;

public class PartForm extends Form<Part, PartsService> {
    public PartForm(ScreenManager screenManager, Part entity, PartsService service) {
        super(screenManager, entity, service);
    }

    public PartForm(ScreenManager screenManager, PartsService service) {
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
