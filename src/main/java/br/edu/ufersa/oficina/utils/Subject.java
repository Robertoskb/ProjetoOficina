package br.edu.ufersa.oficina.utils;

import java.util.ArrayList;

public interface Subject {
    public void registerObserver(Observer observer);

    public void removeObserver(Observer observer);

    public void notifyObservers(int id);

}
