package br.edu.ufersa.oficina.model.Mappers;

import br.edu.ufersa.oficina.model.Entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserMapper implements GenericMapper<User> {
    public User createEntity(ResultSet rs) throws SQLException {
        int id = rs.getInt("user_id");
        String name = rs.getString("user_name");
        String email = rs.getString("email");
        String password = rs.getString("password");

        return new User(id, name, email, password);
    }

    public ArrayList<User> createArrayEntity(ResultSet rs) throws SQLException {
        ArrayList<User> users = new ArrayList<User>();

        while (rs.next())
            users.add(createEntity(rs));

        return users;
    }
}
