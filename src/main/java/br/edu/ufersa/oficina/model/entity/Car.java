package br.edu.ufersa.oficina.model.entity;

public class Car {
    private int id;
    private String brand;
    private String model;
    private String color;
    private String plate;
    private int year;
    private int mileage;
    private Client client;

    public Car(int id, String brand, String model, String color, String plate, int year, int mileage, Client client) {
        setId(id);
        setBrand(brand);
        setModel(model);
        setColor(color);
        setPlate(plate);
        setYear(year);
        setMileage(mileage);
        setClient(client);
    }

    public Car(String brand, String model, String color, String plate, int year, int mileage, Client client) {
        setBrand(brand);
        setModel(model);
        setColor(color);
        setPlate(plate);
        setYear(year);
        setMileage(mileage);
        setClient(client);
    }

    public void show() {
        System.out.println("(" + this.plate + ", " + this.brand + " " + this.model + ", Dono: " + this.client.getName() + ")");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
