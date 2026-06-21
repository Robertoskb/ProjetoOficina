package br.edu.ufersa.oficina.model.Services;

import br.edu.ufersa.oficina.model.DAO.TreatmentDAO;
import br.edu.ufersa.oficina.Exceptions.MecException;
import br.edu.ufersa.oficina.Exceptions.MecNotFoundException;
import br.edu.ufersa.oficina.model.Entity.*;

import java.time.LocalDate;
import java.util.ArrayList;

public abstract class TreatmentService<T extends Treatment> implements GenericService<T> {
    protected TreatmentDAO<T> treatmentDAO;

    public TreatmentService(TreatmentDAO<T> treatmentDAO) {
        setTreatmentDAO(treatmentDAO);
    }

    public ArrayList<T> getAllTreatments(){
        return treatmentDAO.getAllEntities();
    }

    public T getTreatmentById(int id){
        T treatment = treatmentDAO.getTreatmentById(id);

        if (treatment == null)
            throw new MecNotFoundException("Atendimento não encontrado");

        return treatment;
    }

    public ArrayList<T> getTreatmentByClient(Client client){
        return treatmentDAO.getTreatmentsByClient(client);
    }

    public ArrayList<T> getTreatmentByCar(Car car){
        return treatmentDAO.getTreatmentsByCar(car);
    }

    public ArrayList<T> getTreatmentByPeriod(LocalDate start, LocalDate end){
        if (start.isAfter(end))
            throw new MecException("Período inválido");

        return treatmentDAO.getTreatmentsByPeriod(start, end);
    }

    public ArrayList<Part> getPartsByTreatment(int id){
        return treatmentDAO.getPartsByTreatment(id);
    }

    public ArrayList<Service> getServiceByTreatment(int id){
        return treatmentDAO.getServicesByTreatment(id);
    }

    public void insert(T treatment) {
        if (treatment.getPrice() <= 0)
            throw new MecException("Preço inválido");

        if (treatment.getServices().isEmpty() && treatment.getParts().isEmpty())
            throw new MecException("Atendimento vazio de serviços e peças");

        if (treatment.getDate_start() == null)
            throw new MecException("Data de não pode ser nula");

        if (treatment.getDate_finish() != null && treatment.getDate_start().isAfter(treatment.getDate_finish()))
            throw new MecException("Data de início não pode ser posterior a data de finalização");


        treatmentDAO.insert(treatment);
    }

    public void update(T treatment){
        if (treatmentDAO.getTreatmentById(treatment.getId()) == null)
            throw new MecNotFoundException("Atendimento não encontrado");

        if (treatment.getPrice() <= 0)
            throw new MecException("Preço inválido");

        if (treatment.getServices().isEmpty() && treatment.getParts().isEmpty())
            throw new MecException("Atendimento vazio de serviços e peças");

        if (treatment.getDate_start() == null)
            throw new MecException("Data de não pode ser nula");

        if (treatment.getDate_finish() != null && treatment.getDate_start().isAfter(treatment.getDate_finish()))
            throw new MecException("Data de início não pode ser posterior a data de finalização");

        treatmentDAO.update(treatment);
    }

    public void delete(int id){
        getTreatmentById(id);

        treatmentDAO.delete(id);
    }

    public abstract void finish(int id);

    public TreatmentDAO<T> getTreatmentDAO() {
        return treatmentDAO;
    }

    public void setTreatmentDAO(TreatmentDAO<T> treatmentDAO) {
        this.treatmentDAO = treatmentDAO;
    }
}
