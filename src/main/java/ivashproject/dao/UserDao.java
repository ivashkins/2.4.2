package ivashproject.dao;

import ivashproject.model.User;
import org.springframework.stereotype.Component;

import java.util.List;


public interface UserDao {
    public void addUser(User user);
    public void deleteUser(User user);
    public void updateUser(long id,User user);
    public List<User> userList();
    public User show(long id);
    public User show(String name);
}
