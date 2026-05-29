package br.edu.ufersa.oficina.model.Services;

import br.edu.ufersa.oficina.model.DAO.ClientDAO;
import br.edu.ufersa.oficina.Exceptions.MecException;
import br.edu.ufersa.oficina.Exceptions.MecNotFoundException;
import br.edu.ufersa.oficina.model.entity.Client;

import java.util.ArrayList;

public class ClientService {
    private final ClientDAO dao = new ClientDAO();

    public void addClient(Client client){
        String name, address, cpf;
        name = client.getName();
        address = client.getAddress();
        cpf = client.getCPF();

        if (!name.trim().isEmpty() && !address.trim().isEmpty() && !cpf.trim().isEmpty()){
            dao.addClient(client);
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
        ArrayList<Client> clients = dao.getAllClient();

        if (clients == null)
            throw new MecNotFoundException("Nenhum cliente encontrado");

        return clients;
    }


    public ClientDAO getDao() {
        return dao;
    }
}
