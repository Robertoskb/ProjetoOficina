import br.edu.ufersa.oficina.model.DAO.*;
import br.edu.ufersa.oficina.model.DAO.OrderDAO;
import br.edu.ufersa.oficina.model.Services.*;
import br.edu.ufersa.oficina.model.entity.*;

public class Main {
    public static void main(String[] args) {
        CarService cs = new CarService();
        PartsService ps = new PartsService();

       for (Car c: cs.getAllCars())
            c.show();

        for (Parts p: ps.getAllParts())
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

        for (User u: us.getAllUsers())
            u.show();

        for (Budget b: bs.getAllTreatment())
            b.show();

        for (Order o: os.getAllTreatment()){
            o.show();
        }
    }
}
