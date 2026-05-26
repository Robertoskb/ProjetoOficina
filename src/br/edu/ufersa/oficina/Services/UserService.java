package br.edu.ufersa.oficina.Services;

import br.edu.ufersa.oficina.DAO.UserDAO;
import br.edu.ufersa.oficina.Exceptions.MecException;
import br.edu.ufersa.oficina.Exceptions.MecNotFoundException;
import br.edu.ufersa.oficina.entity.User;

import java.util.ArrayList;

public class UserService {
    private final UserDAO dao = new UserDAO();

    public void addUser(User user){
        String name, email, password;
        name = user.getName();
        email = user.getEmail();
        password = user.getPassword();

        if (!name.trim().isEmpty() && !email.trim().isEmpty() && !password.trim().isEmpty()){
            if (!email.contains("@"))
                throw new MecException("Email inválido");

            if (password.length() < 8)
                throw new MecException("Senha curta");

            dao.addUser(user);
        }
        else {
            throw new MecException("Campos vazios");
        }
    }

    public User getUserById(int id){
        User user = dao.filterEntityById(id);

        if (user == null)
            throw new MecNotFoundException("Usuário não Encontrado");

        return user;
    }

    public ArrayList<User> getAllUsers(){
        ArrayList<User> users = dao.getAllUsers();

        if (users == null)
            throw new MecNotFoundException("Nenhum usuário encontrado");

        return users;
    }

    public User login(String email, String password){
        User user = dao.filterEntity("email", email);

        if (user == null)
            throw new MecNotFoundException("Email não encontrado");

        if (!password.equals(user.getPassword()))
            throw new MecException("Senha incorreta");
        
        return user;
    }

    public UserDAO getDao() {
        return dao;
    }
}
