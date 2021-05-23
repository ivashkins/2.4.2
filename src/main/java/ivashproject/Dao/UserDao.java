package ivashproject.Dao;

import ivashproject.Model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Transactional
    public void addUser(User user) {
        sessionFactory.getCurrentSession().save(user);
    }

    @Transactional
    public void deleteUser(User user){
        sessionFactory.getCurrentSession().delete(user);
    }

    @Transactional
    public void updateUser(long id,User updateUser){
        TypedQuery<User> query=sessionFactory.getCurrentSession().createQuery("update User set name= :name, lastName=:lastname,age=:age where id=:id");
        query.setParameter("name",updateUser.getName());
        query.setParameter("lastname",updateUser.getLastName());
        query.setParameter("age",updateUser.getAge());
        query.setParameter("id",updateUser.getId());
        query.executeUpdate();
    }

    @Transactional(readOnly = true)
    public List<User> userList() {
        TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("from User", User.class);
        return query.getResultList();
    }
@Transactional(readOnly = true)
    public User show(long id) {
        TypedQuery<User>query=sessionFactory.getCurrentSession().createQuery("from User where id= :id");
        query.setParameter("id",id);
        return query.getSingleResult();
    }

}
