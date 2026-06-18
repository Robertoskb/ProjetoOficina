package br.edu.ufersa.oficina.utils;

public interface TreatmentObserver extends Observer {
    void delete(int id);
    void edit(int id);
    void finish(int id);
}
