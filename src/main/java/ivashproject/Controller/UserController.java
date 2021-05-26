package ivashproject.Controller;

import ivashproject.Dao.UserDao;
import ivashproject.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

    final UserDao userDao;

    public UserController(UserDao userDao) {
        this.userDao = userDao;
    }

    @GetMapping("/user")
    public String getUsers(ModelMap map, Authentication authentication) {
        if(authentication!=null) {
            User user = getShowUser(authentication.getName());
            map.addAttribute("user", user);
        }
        return "singleUser";
    }
    public User getShowUser(@PathVariable String name){
        return userDao.show(name);
    }

    @GetMapping("/admin/{id}")
    public String showUser(@PathVariable long id, ModelMap map) {
        map.addAttribute("user", userDao.show(id));
        return "singleUser";
    }

    @GetMapping("/admin")
    public String adminRole(ModelMap map) {
        map.addAttribute("users", userDao.userList());
        return "admin";
    }


    @GetMapping("admin/new")
    public String newUser(ModelMap map) {
        map.addAttribute("user", new User());
        return "newUser";
    }


    @PostMapping("/admin/create")
    public String create(@ModelAttribute("user") User user) {
        PasswordEncoder encoder=new BCryptPasswordEncoder(12);
        user.setPassword(encoder.encode(user.getPassword()));
        userDao.addUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/admin/{id}/edit")
    public String edit(@PathVariable("id") long id, ModelMap map) {
        map.addAttribute("user", userDao.show(id));
        return "edit";
    }

    @GetMapping("admin/{id}/delete")
    public String delete(@PathVariable("id") long id) {
        userDao.deleteUser(userDao.show(id));
        return "redirect:/admin";
    }

    @PatchMapping("admin/{id}")
    public String update(@ModelAttribute("user") User user, @PathVariable("id") long id) {
        userDao.updateUser(id, user);
        return "redirect:/admin";
    }

}
