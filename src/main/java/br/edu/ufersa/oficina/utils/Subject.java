package br.edu.ufersa.oficina.utils;

public interface Subject {
    public void registerObserver(Observer observer);

    public void removeObserver(Observer observer);

    public void notifyAdd();
    public void notifyDelete(int id);
    public void notifyEdit(int id);

}
