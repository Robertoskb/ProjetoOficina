package br.edu.ufersa.oficina.model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import br.edu.ufersa.oficina.Exceptions.MecException;
import br.edu.ufersa.oficina.model.Connection.ConnectionDB;
import br.edu.ufersa.oficina.model.Entity.User;
import br.edu.ufersa.oficina.model.Mappers.UserMapper;

public class UserDAO extends GenericDAO<User> {

    public UserDAO(){
        super("User", new UserMapper());
    }

    public ArrayList<User> getAllUsers(){
        return getAllEntities();
    }

    private void register(String name, String email, String password, boolean admin){
        Connection conn = ConnectionDB.getConnection();

        String sql = "INSERT INTO " + table + " (user_name, email, password, admin) values (?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, password);
            ps.setBoolean(4, admin);

            ps.execute();
        }

        catch (SQLException e){
            throw new MecException(e.getMessage());
        }
    }

    public void insert(User user){
        register(user.getName(), user.getEmail(), user.getPassword(), user.isAdmin());
    }

    public void update(User user){
        Connection conn = ConnectionDB.getConnection();

        String sql = "UPDATE " + table + " SET user_name = ?, email = ?, password = ?, admin = ? WHERE user_id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setBoolean(4, user.isAdmin());

            ps.setInt(5, user.getId());

            ps.execute();
        }

        catch (SQLException e){
            throw new MecException(e.getMessage());
        }
    }

}