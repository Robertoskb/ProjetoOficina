package br.edu.ufersa.oficina.components;

import br.edu.ufersa.oficina.model.Services.GenericService;

import java.io.IOException;

public class EntityCard<E, S extends GenericService<E>> extends Card<S>{
    public EntityCard() throws IOException {
        super("entityCard.fxml");
    }
}
