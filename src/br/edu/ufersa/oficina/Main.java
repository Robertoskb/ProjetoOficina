package br.edu.ufersa.oficina;

import br.edu.ufersa.oficina.DAO.*;
import br.edu.ufersa.oficina.DAO.OrderDAO;
import br.edu.ufersa.oficina.entity.*;

public class Main {
    public static void main(String[] args) {
        CarDAO cd = new CarDAO();
        PartsDAO pd = new PartsDAO();

        for (Car c: cd.getAllCar())
            c.show();

        for (Parts p: pd.getAllParts())
            p.show();

        ClientDAO cld = new ClientDAO();
        ServiceDAO sd = new ServiceDAO();

        for (Client c: cld.getAllClient())
            c.show();

        for (Service s: sd.getAllService())
            s.show();

        UserDAO ud = new UserDAO();
        BudgetDAO bd = new BudgetDAO();
        OrderDAO od = new OrderDAO();

        for (User u: ud.getAllUsers())
            u.show();

        for (Budget b: bd.getAllBudget())
            b.show();

        for (Order o: od.getAllOrder()){
            o.show();
        }
    }
}
