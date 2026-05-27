import br.edu.ufersa.oficina.model.DAO.*;
import br.edu.ufersa.oficina.model.DAO.OrderDAO;
import br.edu.ufersa.oficina.model.entity.*;

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

        for (Budget b: bd.getAllEntity())
            b.show();

        for (Order o: od.getAllEntity()){
            o.show();
        }
    }
}
