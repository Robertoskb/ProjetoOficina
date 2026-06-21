package br.edu.ufersa.oficina.utils;

public interface TransactionSubject {
    public void registerObserver(TransactionObserver observer);

    public void removeObserver(TransactionObserver observer);

    public void notifyDelete(int id);
    public void notifyEdit(int id);
    public void notifyFinish(int id);
}
