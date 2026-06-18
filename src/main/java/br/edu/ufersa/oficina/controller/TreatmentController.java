package br.edu.ufersa.oficina.controller;

import br.edu.ufersa.oficina.components.CardTreatment;
import br.edu.ufersa.oficina.model.Entity.Car;
import br.edu.ufersa.oficina.model.Entity.Treatment;
import br.edu.ufersa.oficina.model.Services.TreatmentService;
import br.edu.ufersa.oficina.ui.ScreenManager;

import java.io.IOException;

public class TreatmentController<T extends Treatment, S extends TreatmentService<T>> extends PaginatorController<S>{
    public TreatmentController(ScreenManager screenManager, S service){
        super(screenManager, service);
    }

    @Override
    public void generateCards() throws IOException {
        for (T treatment: service.getAllTreatments()){
            if (treatment.getDate_finish() != null)
                continue;
            CardTreatment<S> card = new CardTreatment<>();
            card.setService(service);
            card.setEntityId(treatment.getId());

            Car car = treatment.getCar();

            String first = "Carro";
            String last = "Desconhecido";

            if (car != null) {
                first = car.getModel();
                if (car.getClient() != null)
                    last = car.getClient().getName();
            }

            card.setTitle(first + " de " + last + " id: " + treatment.getId());
            card.setDescription("R$ " + treatment.getPrice());
            card.registerObserver(this);

            cards.add(card);
        }
    }
}
