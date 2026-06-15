package br.edu.ufersa.oficina;

import br.edu.ufersa.oficina.model.Services.*;
import br.edu.ufersa.oficina.model.Entity.*;

public class Main {
    public static void main(String[] args) {
        CarService cs = new CarService();
        PartsService ps = new PartsService();

       for (Car c: cs.getAllCars())
            c.show();

        for (Part p: ps.getAllParts())
            p.show();

        ClientService csv = new ClientService();
        ServiceService ss = new ServiceService();

        for (Client c: csv.getAllClients())
            c.show();

        for (Service s: ss.getAllServices())
            s.show();

        UserService us = new UserService();
        BudgetService bs = new BudgetService();
        OrderService os = new OrderService();

        for (Budget b: bs.getAllTreatments()){
            b = bs.getTreatmentById(b.getId());
            for (Part p: b.getParts())
                p.show();
            for (Service s: b.getServices())
                s.show();
        }
        for (Order o: os.getAllTreatments()){
            o = os.getTreatmentById(o.getId());
            for (Part p: o.getParts())
                p.show();
            for (Service s: o.getServices())
                s.show();
        }
    }
}
