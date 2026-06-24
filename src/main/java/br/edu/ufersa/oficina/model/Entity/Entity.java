package br.edu.ufersa.oficina.model.Entity;

public abstract class Entity {
    protected int id;

    public boolean isValid(){
        return id > 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
