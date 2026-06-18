package br.edu.ufersa.oficina.utils;

public interface TreatmentSubject {
    public void registerObserver(TreatmentObserver observer);

    public void removeObserver(TreatmentObserver observer);

    public void notifyDelete(int id);
    public void notifyEdit(int id);
    public void notifyFinish(int id);
}
