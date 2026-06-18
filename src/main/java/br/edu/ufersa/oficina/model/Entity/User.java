package br.edu.ufersa.oficina.model.Entity;

public class User extends Entity{

    private String name;
    private String email;
    private String password;
    private boolean admin;


    public User(int id, String name, String email, String password, boolean admin){
        setId(id);
        setName(name);
        setEmail(email);
        setPassword(password);
        setAdmin(admin);
    }

    public User(String name, String email, String password, boolean admin){
        setName(name);
        setEmail(email);
        setPassword(password);
        setAdmin(admin);
    }

    public void show(){
        System.out.println("(" + this.name + ", " + this.email + ")");
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

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
}