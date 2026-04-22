package br.edu.ufersa.oficina.entity;

import java.time.LocalDate;
import java.util.ArrayList;


public class Treatment {
    private int id;
    private ArrayList<Parts> parts;
    private ArrayList<Service> services;
    private Car car;
    private double price;
    private LocalDate date_start;
    private LocalDate date_finish;

    public Treatment(int id, ArrayList<Parts> parts, ArrayList<Service> services, Car car, double price){
        setId(id);
        setParts(parts);
        setServices(services);
        setCar(car);
        setPrice(price);
        setDate_start(LocalDate.now());
    }

    public Treatment(int id, ArrayList<Parts> parts, ArrayList<Service> services, Car car, double price, LocalDate date_start, LocalDate date_finish){
        setId(id);
        setParts(parts);
        setServices(services);
        setCar(car);
        setPrice(price);
        setDate_start(date_start);
        setDate_finish(date_finish);

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Parts> getParts() {
        return parts;
    }

    public void setParts(ArrayList<Parts> parts) {
        this.parts = parts;
    }

    public ArrayList<Service> getServices() {
        return services;
    }

    public void setServices(ArrayList<Service> services) {
        this.services = services;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        if (price > 0)
            this.price = price;
    }

    public LocalDate getDate_start() {
        return date_start;
    }

    public void setDate_start(LocalDate date_start) {
        this.date_start = date_start;
    }

    public LocalDate getDate_finish() {
        return date_finish;
    }

    public void setDate_finish(LocalDate date_finish) {
        this.date_finish = date_finish;
    }
}
