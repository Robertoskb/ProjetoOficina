package br.edu.ufersa.oficina.DAO;

import br.edu.ufersa.oficina.connection.ConnectionDB;
import br.edu.ufersa.oficina.entity.User;
import java.sql.*;
import java.util.ArrayList;

class UserFactory{
    public static User createUser(ResultSet rs) throws SQLException{
        int id = rs.getInt("id");
        String name = rs.getString("name");
        String email = rs.getString("email");
        String password = rs.getString("password");

        return new User(id, name, email, password);
    }

    public static ArrayList<User> createArrayUsers(ResultSet rs) throws SQLException{
        ArrayList<User> users = new ArrayList<User>();

        while (rs.next())
            users.add(createUser(rs));

        return users;
    }
}

public class UserDAO extends GenericDAO {

    public UserDAO(){
        super("User");
    }

    public User getUserById(int id){
        try (ResultSet rs = filterById(id)) {
            if (rs != null && rs.next()) {
                return UserFactory.createUser(rs);
            }
        }

        catch (SQLException e){
            System.out.println(e.getMessage());

        }
        return null;
    }

    public ArrayList<User> getAllUsers(){
        ResultSet rs = getAll();

        if (rs == null) return null;
        try {
            return UserFactory.createArrayUsers(rs);
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;

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

    public void addUser(String name, String email, String password){
        register(name, email, password);
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