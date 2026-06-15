package br.edu.ufersa.oficina.model.DAO;

import br.edu.ufersa.oficina.Exceptions.MecException;
import br.edu.ufersa.oficina.model.Mappers.UserMapper;
import br.edu.ufersa.oficina.model.Connection.ConnectionDB;
import br.edu.ufersa.oficina.model.Entity.User;
import java.sql.*;
import java.util.ArrayList;

public class UserDAO extends GenericDAO<User> {

    public UserDAO(){
        super("User", new UserMapper());
    }

    public ArrayList<User> getAllUsers(){
        return getAllEntities();
    }

    private void register(String name, String email, String password){
        Connection conn = ConnectionDB.getConnection();

        String sql = "INSERT INTO " + table + " (name, email, password) values (?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, password);

            ps.execute();
        }

        catch (SQLException e){
            throw new MecException(e.getMessage());
        }
    }

    public void addUser(User user){
        register(user.getName(), user.getEmail(), user.getPassword());
    }

    public void updateUser(User user){
        Connection conn = ConnectionDB.getConnection();

        String sql = "UPDATE " + table + " SET name = ?, email = ?, password = ? WHERE id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());

            ps.setInt(4, user.getId());

            ps.execute();
        }

        catch (SQLException e){
            throw new MecException(e.getMessage());
        }
    }

}