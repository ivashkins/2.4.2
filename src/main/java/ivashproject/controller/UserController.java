package ivashproject.controller;

import ivashproject.model.User;
import ivashproject.service.UserService;
import ivashproject.service.UserServiceImpl;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {
    final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user")
    public String getUsers(ModelMap map, Authentication authentication) {
        userService.getUsers(map, authentication);
        return "singleUser";
    }


    @GetMapping("/admin/{id}")
    public String showUser(@PathVariable long id, ModelMap map) {
        userService.showUser(id, map);
        return "singleUser";
    }

    @GetMapping("/admin")
    public String adminRole(ModelMap map) {
        userService.adminRole(map);
        return "admin";
    }


    @GetMapping("admin/new")
    public String newUser(ModelMap map) {
        userService.newUser(map);
        return "newUser";
    }


    @PostMapping("/admin/create")
    public String create(@ModelAttribute("user") User user) {
        userService.create(user);
        return "redirect:/admin";
    }

    @GetMapping("/admin/{id}/edit")
    public String edit(@PathVariable("id") long id, ModelMap map) {
        userService.edit(id, map);
        return "edit";
    }

    @GetMapping("admin/{id}/delete")
    public String delete(@PathVariable("id") long id) {
        userService.delete(id);
        return "redirect:/admin";
    }

    @PatchMapping("admin/{id}")
    public String update(@ModelAttribute("user") User user, @PathVariable("id") long id) {
        userService.update(id,user);
        return "redirect:/admin";
    }

}
