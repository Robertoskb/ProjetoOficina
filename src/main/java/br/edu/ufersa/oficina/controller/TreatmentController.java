package br.edu.ufersa.oficina.controller;

import br.edu.ufersa.oficina.components.CardTreatment;
import br.edu.ufersa.oficina.model.Entity.Car;
import br.edu.ufersa.oficina.model.Entity.Client;
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
            card.setCardId(treatment.getId());

            Car car = treatment.getCar();
            Client client = car.getClient();

            String first = car.getModel() != null ? car.getModel(): "<Carro Removido>";
            String last = client.getName()!= null ? client.getName() : "<Cliente Removido>";


            card.setTitle(first + " de " + last);
            card.setDescription("R$ " + treatment.getPrice());
            card.registerObserver(this);

            cards.add(card);
        }
    }
}
