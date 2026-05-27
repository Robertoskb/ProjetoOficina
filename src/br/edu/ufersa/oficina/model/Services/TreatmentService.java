package br.edu.ufersa.oficina.model.Services;

import br.edu.ufersa.oficina.model.DAO.TreatmentDAO;
import br.edu.ufersa.oficina.Exceptions.MecException;
import br.edu.ufersa.oficina.Exceptions.MecNotFoundException;
import br.edu.ufersa.oficina.model.entity.Treatment;
import br.edu.ufersa.oficina.model.entity.Car;
import br.edu.ufersa.oficina.model.entity.Client;

import java.time.LocalDate;
import java.util.ArrayList;

public class TreatmentService<T extends Treatment> {
    private TreatmentDAO<T> treatmentDAO;

    public TreatmentService(TreatmentDAO<T> treatmentDAO) {
        setTreatmentDAO(treatmentDAO);
    }

    public ArrayList<T> getAllTreatment(){
        return treatmentDAO.getAllEntity();
    }

    public T getTreatmentById(int id){
        T treatment = treatmentDAO.getTreatmentById(id);

        if (treatment == null)
            throw new MecNotFoundException("Atendimento não encontrado");

        return treatment;
    }

    public ArrayList<T> getTreatmentByClient(Client client){
        return treatmentDAO.getTreatmentByClient(client);
    }

    public ArrayList<T> getTreatmentByCar(Car car){
        return treatmentDAO.getTreatmentByCar(car);
    }

    public ArrayList<T> getTreatmentByPeriod(LocalDate start, LocalDate end){
        if (start.isAfter(end))
            throw new MecException("Período inválido");

        return treatmentDAO.getTreatmentByPeriod(start, end);
    }

    public void addTreatment(T treatment) {
        if (treatment.getPrice() <= 0)
            throw new MecException("Preço inválido");

        if (treatment.getServices().isEmpty() && treatment.getParts().isEmpty())
            throw new MecException("Atendimento vazio de serviços e peças");


        treatmentDAO.addTreatment(treatment);
    }

    public void updateTreatment(T treatment){
        if (treatmentDAO.getTreatmentById(treatment.getId()) == null)
            throw new MecNotFoundException("Atendimento não encontrado");

        if (treatment.getPrice() <= 0)
            throw new MecException("Preço inválido");

        if (treatment.getServices().isEmpty() && treatment.getParts().isEmpty())
            throw new MecException("Atendimento vazio de serviços e peças");

        treatmentDAO.updateTreatment(treatment);
    }

    public void delete(int id){
        if (treatmentDAO.getTreatmentById(id) == null)
            throw new MecNotFoundException("Atendimento não encontrado");

        treatmentDAO.delete(id);
    }

    public TreatmentDAO<T> getTreatmentDAO() {
        return treatmentDAO;
    }

    public void setTreatmentDAO(TreatmentDAO<T> treatmentDAO) {
        this.treatmentDAO = treatmentDAO;
    }
}
