package br.edu.ufersa.oficina.model.Entity;

import java.time.LocalDate;
import java.util.ArrayList;


public abstract class Transaction extends Entity {
    private ArrayList<Part> parts;
    private ArrayList<Service> services;
    private Car car;
    private double price;
    private LocalDate date_start;
    private LocalDate date_finish;

    public Transaction(){}

    public Transaction(int id, ArrayList<Part> parts, ArrayList<Service> services, Car car, double price){
        setId(id);
        setParts(parts);
        setServices(services);
        setCar(car);
        setPrice(price);
        setDate_start(LocalDate.now());
    }

    public Transaction(ArrayList<Part> parts, ArrayList<Service> services, Car car, double price){
        setParts(parts);
        setServices(services);
        setCar(car);
        setPrice(price);
        setDate_start(LocalDate.now());
    }

    public Transaction(int id, ArrayList<Part> parts, ArrayList<Service> services, Car car, double price, LocalDate date_start, LocalDate date_finish){
        setId(id);
        setParts(parts);
        setServices(services);
        setCar(car);
        setPrice(price);
        setDate_start(date_start);
        setDate_finish(date_finish);

    }

    public Transaction(ArrayList<Part> parts, ArrayList<Service> services, Car car, double price, LocalDate date_start, LocalDate date_finish){
        setParts(parts);
        setServices(services);
        setCar(car);
        setPrice(price);
        setDate_start(date_start);
        setDate_finish(date_finish);

    }

    public abstract void finish();
    public abstract boolean isFinish();

    public void show(){
        System.out.println("(" + id + ", " + car.getId() + ", " + price + ", " + date_start.toString() + ")");
    }

    public ArrayList<Part> getParts() {
        return parts;
    }

    public void setParts(ArrayList<Part> parts) {
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

    public int getDiscount(){
        double totalParts = parts.stream().mapToDouble(Part::getPrice).sum();
        double totalServices = services.stream().mapToDouble(Service::getPrice).sum();
        double total = totalParts + totalServices;

        return Math.toIntExact(Math.round(100 * (1.0 - price / total)));
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
