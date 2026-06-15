package br.edu.ufersa.oficina.model.Entity;

import java.time.LocalDate;
import java.util.ArrayList;

public class Order extends Treatment{
    private boolean completed;

    public Order(int id, ArrayList<Part> parts, ArrayList<Service> services, Car car, double price){
        super(id, parts, services, car, price);
        completed = false;
    }

    public Order(ArrayList<Part> parts, ArrayList<Service> services, Car car, double price){
        super(parts, services, car, price);
        completed = false;
    }

    public Order(int id, ArrayList<Part> parts, ArrayList<Service> services, Car car, double price, LocalDate date_start, LocalDate date_finish, boolean completed){
        super(id, parts, services, car, price, date_start, date_finish);
        setCompleted(completed);
    }

    public Order(ArrayList<Part> parts, ArrayList<Service> services, Car car, double price, LocalDate date_start, LocalDate date_finish, boolean completed){
        super(parts, services, car, price, date_start, date_finish);
        setCompleted(completed);
    }

    public void finish(){
        setCompleted(true);
        setDate_finish(LocalDate.now());
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
