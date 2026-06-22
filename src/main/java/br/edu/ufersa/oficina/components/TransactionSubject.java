package br.edu.ufersa.oficina.components;

import br.edu.ufersa.oficina.controller.form.TransactionObserver;

public interface TransactionSubject extends Subject {
    public void registerObserver(TransactionObserver observer);

    public void removeObserver(TransactionObserver observer);

    public void notifyDelete(int id);
    public void notifyEdit(int id);
    public void notifyFinish(int id);
}
