package br.edu.ufersa.oficina.model.entity;

import java.time.LocalDate;
import java.util.ArrayList;

public class Budget extends Treatment{
    public Budget(int id, ArrayList<Parts> parts, ArrayList<Service> services, Car car, double price){
        super(id, parts, services, car, price);
    }

    public Budget(ArrayList<Parts> parts, ArrayList<Service> services, Car car, double price){
        super(parts, services, car, price);
    }

    public Budget(int id, ArrayList<Parts> parts, ArrayList<Service> services, Car car, double price, LocalDate date_start, LocalDate date_finish){
        super(id, parts, services, car, price, date_start, date_finish);
    }

    public Budget(ArrayList<Parts> parts, ArrayList<Service> services, Car car, double price, LocalDate date_start, LocalDate date_finish){
        super(parts, services, car, price, date_start, date_finish);
    }

    public Order createOrder(){
        Order order = new Order(getParts(), getServices(), getCar(), getPrice());
        setDate_finish(order.getDate_start());

        return order;
    }
}
