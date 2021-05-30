package ivashproject.dao;

import ivashproject.model.User;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.sql.ResultSet;
import java.util.List;

@Component
public class UserDaoImpl implements UserDao {

    @PersistenceContext(unitName = "entityManagerFactory")
    private EntityManager manager;

    @Override
    @Transactional
    public void addUser(User user) {
        manager.persist(user);
    }

    @Override
    @Transactional
    public void deleteUser(User user) {
        manager.remove(manager.contains(user) ? user : manager.merge(user));
    }

    @Override
    @Transactional
    public void updateUser(long id, User updateUser) {
        updateUser.setId(id);
        manager.merge(updateUser);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> userList() {
        return manager.createQuery("select u from User u", User.class).getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public User show(long id) {
        return manager.find(User.class, id);

    }

    @Override
    @Transactional(readOnly = true)
    public User show(String name) {
        Query query = manager.createQuery("select u from User u  where email= :email");
        query.setParameter("email", name);
        return (User) query.getSingleResult();
    }

}
