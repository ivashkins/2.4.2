package ivashproject.service;

import ivashproject.dao.RolesDao;
import ivashproject.dao.UserDao;
import ivashproject.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;

@Component
public class UserServiceImpl implements UserService {
    private final UserDao userDao;
    private final RolesDao rolesDao;

    public UserServiceImpl(UserDao userDao, RolesDao rolesDao) {
        this.userDao = userDao;
        this.rolesDao = rolesDao;
    }

    public void getUsers(ModelMap map, Authentication authentication) {
        if (authentication != null) {
            User user = getShowUser(authentication.getName());
            map.addAttribute("user", user);

        }
    }

    public User getShowUser(String name) {
        return userDao.show(name);
    }

    @Transactional
    public void create(User user) {
        if (user.isAdmin()) {
            user.addRoles(rolesDao.getAdminRole());
        }
        user.addRoles(rolesDao.getUserRole());
        PasswordEncoder encoder = new BCryptPasswordEncoder(12);
        user.setPassword(encoder.encode(user.getPassword()));
        userDao.addUser(user);
    }

    public void showUser(long id, ModelMap map) {
        map.addAttribute("user", userDao.show(id));
    }

    public void adminRole(ModelMap map) {
        map.addAttribute("users", userDao.userList());
    }

    public void newUser(ModelMap map) {
        map.addAttribute("user", new User());
    }

    public void edit(long id, ModelMap map) {
        map.addAttribute("user", userDao.show(id));
    }

    public void delete(long id) {
        userDao.deleteUser(userDao.show(id));
    }

    public void update(long id, User user) {
        if(user.isAdmin()){
            user.addRoles(rolesDao.getAdminRole());
        }
        user.addRoles(rolesDao.getUserRole());
        userDao.updateUser(id, user);
    }


}
