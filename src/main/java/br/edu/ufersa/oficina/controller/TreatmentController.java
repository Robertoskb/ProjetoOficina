package br.edu.ufersa.oficina.controller;

import br.edu.ufersa.oficina.components.CardGeneric;
import br.edu.ufersa.oficina.components.CardTreatment;
import br.edu.ufersa.oficina.controller.form.TreatmentForm;
import br.edu.ufersa.oficina.controller.form.UserForm;
import br.edu.ufersa.oficina.model.Entity.Car;
import br.edu.ufersa.oficina.model.Entity.Client;
import br.edu.ufersa.oficina.model.Entity.Treatment;
import br.edu.ufersa.oficina.model.Services.TreatmentService;
import br.edu.ufersa.oficina.ui.ScreenManager;
import br.edu.ufersa.oficina.utils.TreatmentObserver;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;
import java.util.ArrayList;

public abstract class TreatmentController<T extends Treatment, S extends TreatmentService<T>> extends PaginatorController<S> implements TreatmentObserver {
    protected ArrayList<CardTreatment> cards = new ArrayList<>();

    public TreatmentController(ScreenManager screenManager, S service){
        super(screenManager, service);
    }

    @Override
    public void generateCards() throws IOException {
        for (T treatment: service.getAllTreatments()){
            CardTreatment card = new CardTreatment();

            if (treatment.isFinish())
                card.removeButton(card.getBtnCheck());

            card.setCardId(treatment.getId());

            Car car = treatment.getCar();
            Client client = car.getClient();

            String first = car.getModel() != null ? car.getModel(): "<Carro Removido>";
            String last = client.getName()!= null ? client.getName() : "<Cliente Removido>";
            String plate = car.getPlate() != null ? car.getPlate(): "";


            card.setTitle(first + " de " + last + " " + plate);
            card.setDescription("R$ " + String.format("R$ %.2f", treatment.getPrice()));
            card.registerObserver(this);

            super.cards.add(card);
            cards.add(card);
        }
    }

    @Override
    public void add() {

    }

    @Override
    public void edit(int id) {
    }

    @Override
    public void finish(int id) {
        try {
            service.finish(id);
            for (CardTreatment card: cards)
                if (card.getCardId() == id)
                    card.removeButton(card.getBtnCheck());
            updatePage(pagination.getCurrentPageIndex());
        }

        catch (Exception e){
            alert(e.getMessage());
        }
    }

}
