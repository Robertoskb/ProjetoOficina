package br.edu.ufersa.oficina.model.entity;

public class Parts {
    private int id;
    private String name;
    private double price;
    private String manufacturer;
    private String model;

    public Parts(int id, String name, double price, String manufacturer, String model) {
        setId(id);
        setName(name);
        setPrice(price);
        setManufacturer(manufacturer);
        setModel(model);
    }

    public Parts(String name, double price, String manufacturer, String model) {
        setName(name);
        setPrice(price);
        setManufacturer(manufacturer);
        setModel(model);
    }

    public void show() {
        System.out.println("(" + this.name + ", R$ " + this.price + ", " + this.manufacturer + ")");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        if(price > 0)
            this.price = price;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
}
