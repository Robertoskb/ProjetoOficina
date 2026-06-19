package br.edu.ufersa.oficina.controller.form;

import br.edu.ufersa.oficina.model.Entity.Treatment;
import br.edu.ufersa.oficina.model.Services.TreatmentService;
import br.edu.ufersa.oficina.ui.ScreenManager;

import java.io.IOException;

public class TreatmentForm<T extends Treatment, S extends TreatmentService<T>> extends Form<T , S>{
    public TreatmentForm(ScreenManager screenManager, T entity, S service) {
        super(screenManager, entity, service);
    }

    public TreatmentForm(ScreenManager screenManager, S service) {
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
