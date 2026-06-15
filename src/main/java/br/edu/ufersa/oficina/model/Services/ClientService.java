package br.edu.ufersa.oficina.model.Services;

import br.edu.ufersa.oficina.model.DAO.ClientDAO;
import br.edu.ufersa.oficina.Exceptions.MecException;
import br.edu.ufersa.oficina.Exceptions.MecNotFoundException;
import br.edu.ufersa.oficina.model.Entity.Client;

import java.util.ArrayList;

public class ClientService {
    private final ClientDAO dao = new ClientDAO();

    public void addClient(Client client){
        String name, address;
        long cpf;
        name = client.getName();
        address = client.getAddress();
        cpf = client.getCPF();

        if (!name.trim().isEmpty() && !address.trim().isEmpty() && (cpf > 0)){
            dao.insert(client);
        }
        else {
            throw new MecException("Campos vazios");
        }
    }

    public Client getClientById(int id){
        Client client = dao.filterEntityById(id);

        if (client == null)
            throw new MecNotFoundException("Cliente não Encontrado");

        return client;
    }

    public ArrayList<Client> getAllClients(){
        ArrayList<Client> clients = dao.getAllClients();
        return clients;
    }


    public ClientDAO getDao() {
        return dao;
    }
}
