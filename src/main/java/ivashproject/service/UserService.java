package ivashproject.service;

import ivashproject.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;


public interface UserService {
    public void getUsers(ModelMap map, Authentication authentication);

    public User getShowUser(String name);

    public void create(User user);

    public void showUser(long id, ModelMap map);

    public void adminRole(ModelMap map);

    public void newUser(ModelMap map);

    public void edit(long id, ModelMap map);

    public void delete(long id);

    public void update(long id, User user);
}
