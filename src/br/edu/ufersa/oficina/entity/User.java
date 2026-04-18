package br.edu.ufersa.oficina.entity;

public class User {

    private int id;
    private String name;
    private String email;
    private String password;
    private int authorization;


    public User(int id, String name, String email, String password, int authorization){
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.authorization = authorization;
    }

    public void show(){
        System.out.println("(" + this.name + ", " + this.email + ")");
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAuthorization() {
        return authorization;
    }

    public void setAuthorization(int authorization) {
        this.authorization = authorization;
    }
}