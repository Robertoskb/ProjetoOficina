package br.edu.ufersa.oficina.model.entity;

public class Client {
    private int id;
    private String name;
    private String address;
    private long CPF;

    public Client(int id, String name, String address, long CPF) {
        setId(id); setName(name); setAddress(address); setCPF(CPF);
    }

    public Client(String name, String address, long CPF) {
        setName(name); setAddress(address); setCPF(CPF);
    }

    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setAddress(String address) { this.address = address; }
    public void setCPF (long CPF) { this.CPF = CPF; }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getAddress() { return address; }
    public long getCPF () { return CPF; }

    public void show() {
        System.out.println("(" + id +
                ", " + name +
                ", " + address +
                ", " + CPF + ")");
    }
}
