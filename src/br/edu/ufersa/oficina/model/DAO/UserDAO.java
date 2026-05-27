package br.edu.ufersa.oficina.model.DAO;

import br.edu.ufersa.oficina.model.Factories.UserFactory;
import br.edu.ufersa.oficina.model.connection.ConnectionDB;
import br.edu.ufersa.oficina.model.entity.User;
import java.sql.*;
import java.util.ArrayList;

public class UserDAO extends GenericDAO<User> {

    public UserDAO(){
        super("User", new UserFactory());
    }

    public ArrayList<User> getAllUsers(){
        return getAllEntity();
    }

    private void register(String name, String email, String password){
        Connection conn = ConnectionDB.getConnection();

        if (conn == null) return;

        String sql = "INSERT INTO " + table + " (name, email, password) values (?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, password);

            ps.execute();
        }

        catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public void addUser(User user){
        register(user.getName(), user.getEmail(), user.getPassword());
    }

    public void updateUser(User user){
        Connection conn = ConnectionDB.getConnection();

        if (conn == null) return;

        String sql = "UPDATE TABLE " + table + " SET name = ?, email = ?, password = ? WHERE id = ?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());

            ps.setInt(5, user.getId());

            ps.execute();
        }

        catch (SQLException e){
            System.out.println(e.getMessage());

        }
    }

}