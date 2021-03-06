package web.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import web.model.User;
import web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminUserController {

    private UserService userService;

    @Autowired
    public AdminUserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/user")
    public ModelAndView listCars(ModelAndView modelAndView) {
        List<User> users = userService.getUsers();
        modelAndView.setViewName("admin-list-users");
        modelAndView.addObject("users", users);
        return modelAndView;
    }

    @GetMapping("/showForm")
    public ModelAndView showFormForAdd(ModelAndView modelAndView) {
        User user = new User();
        modelAndView.setViewName("admin-user-form");
        modelAndView.addObject("user", user);
        return modelAndView;
    }

    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute("user") User user) {
        userService.saveUser(user);
        return "redirect:/admin/user";
    }

    @GetMapping("/updateForm")
    public ModelAndView showFormForUpdate(@RequestParam("userId") long id, ModelAndView modelAndView) {
        User user = userService.getUser(id);
        modelAndView.setViewName("admin-user-form");
        modelAndView.addObject("user", user);
        return modelAndView;
    }

    @GetMapping("/delete")
    public String deleteCar(@RequestParam("userId") long id) {
        userService.deleteUser(id);
        return "redirect:/admin/user";
    }
}
