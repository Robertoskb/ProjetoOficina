package br.edu.ufersa.oficina.controller;

public interface TransactionObserver extends Observer {
    void delete(int id);
    void edit(int id);
    void finish(int id);
}
