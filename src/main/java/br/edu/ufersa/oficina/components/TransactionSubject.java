package br.edu.ufersa.oficina.components;

import br.edu.ufersa.oficina.controller.Paginator.TransactionObserver;

public interface TransactionSubject extends Subject {
    void registerObserver(TransactionObserver observer);

    void removeObserver(TransactionObserver observer);

    public void notifyFinish(int id);
}
