package br.edu.ufersa.oficina.model.Services;

import br.edu.ufersa.oficina.model.DAO.TransactionDAO;
import br.edu.ufersa.oficina.Exceptions.MecException;
import br.edu.ufersa.oficina.Exceptions.MecNotFoundException;
import br.edu.ufersa.oficina.model.Entity.*;

import java.time.LocalDate;
import java.util.ArrayList;

public abstract class TransactionService<T extends Transaction> implements GenericService<T> {
    protected TransactionDAO<T> transactionDAO;

    public TransactionService(TransactionDAO<T> transactionDAO) {
        setTransactionDAO(transactionDAO);
    }

    public ArrayList<T> getAllTransactions(){
        return transactionDAO.getAllEntities();
    }

    public T getTransactionById(int id){
        T transaction = transactionDAO.getTransactionById(id);

        if (transaction == null)
            throw new MecNotFoundException("Atendimento não encontrado");

        return transaction;
    }

    public ArrayList<T> getTransactionByClient(Client client){
        return transactionDAO.getTransactionsByClient(client);
    }

    public ArrayList<T> getTransactionByCar(Car car){
        return transactionDAO.getTransactionsByCar(car);
    }

    public ArrayList<T> getTransactionByPeriod(LocalDate start, LocalDate end){
        if (start.isAfter(end))
            throw new MecException("Período inválido");

        return transactionDAO.getTransactionsByPeriod(start, end);
    }

    public ArrayList<Part> getPartsByTransaction(int id){
        return transactionDAO.getPartsByTransaction(id);
    }

    public ArrayList<Service> getServiceByTransaction(int id){
        return transactionDAO.getServicesByTransaction(id);
    }

    public void insert(T transaction) {
        if (transaction.getPrice() <= 0)
            throw new MecException("Preço inválido");

        if (transaction.getServices().isEmpty() && transaction.getParts().isEmpty())
            throw new MecException("Atendimento vazio de serviços e peças");

        if (transaction.getDate_start() == null)
            throw new MecException("Data de não pode ser nula");

        if (transaction.getDate_finish() != null && transaction.getDate_start().isAfter(transaction.getDate_finish()))
            throw new MecException("Data de início não pode ser posterior a data de finalização");


        transactionDAO.insert(transaction);
    }

    public void update(T transaction){
        if (transactionDAO.getTransactionById(transaction.getId()) == null)
            throw new MecNotFoundException("Atendimento não encontrado");

        if (transaction.getPrice() <= 0)
            throw new MecException("Preço inválido");

        if (transaction.getServices().isEmpty() && transaction.getParts().isEmpty())
            throw new MecException("Atendimento vazio de serviços e peças");

        if (transaction.getDate_start() == null)
            throw new MecException("Data de não pode ser nula");

        if (transaction.getDate_finish() != null && transaction.getDate_start().isAfter(transaction.getDate_finish()))
            throw new MecException("Data de início não pode ser posterior a data de finalização");

        transactionDAO.update(transaction);
    }

    public void delete(int id){
        getTransactionById(id);

        transactionDAO.delete(id);
    }

    public abstract void finish(int id);

    public TransactionDAO<T> getTransactionDAO() {
        return transactionDAO;
    }

    public void setTransactionDAO(TransactionDAO<T> transactionDAO) {
        this.transactionDAO = transactionDAO;
    }
}
