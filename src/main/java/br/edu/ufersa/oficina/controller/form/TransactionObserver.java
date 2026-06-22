package br.edu.ufersa.oficina.controller.form;

public interface TransactionObserver extends Observer {
    void delete(int id);
    void edit(int id);
    void finish(int id);
}
