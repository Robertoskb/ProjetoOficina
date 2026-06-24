package br.edu.ufersa.oficina.model.Entity;

public class Service extends Entity{
    private String name;
    private double price;

    public Service(int id, String name, double price) {
        setId(id); setName(name); setPrice(price);
    }

    public Service(String name, double price) {
        setName(name); setPrice(price);
    }

    public void setName(String name) { this.name = name; }

    public void setPrice(double price) {
        if (price > 0) { this.price = price; }
    }

    public String getName() { return name; }
    public double getPrice() { return price; }

    public void show() {
        System.out.println("(" + id +
                ", " + name +
                ", " + price + ")");
    }

    @Override
    public String toString(){
        return name;
    }
}
