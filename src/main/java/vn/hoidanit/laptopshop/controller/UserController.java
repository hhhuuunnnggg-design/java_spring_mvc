package vn.hoidanit.laptopshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import vn.hoidanit.laptopshop.entity.User;
import vn.hoidanit.laptopshop.repository.UserRepository;
import vn.hoidanit.laptopshop.service.UserService;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    // ---------------------------------------

    @RequestMapping("/")
    public ModelAndView getHomePage() {
        ModelAndView modelAndView = new ModelAndView("hello"); // vewname là đường link dẫ đến jsp
        String test = this.userService.handleHello();
        modelAndView.addObject("eric", test);
        return modelAndView;
    }

    @RequestMapping("/admin/user")
    public ModelAndView getuserPage() {
        ModelAndView modelAndView = new ModelAndView("admin/user/create");
        modelAndView.addObject("newUser", new User());
        return modelAndView;
    }

    // value này ăn với action bên jsp
    @RequestMapping(value = "/admin/user/create", method = RequestMethod.POST)
    // @ModelAttribute("newUser") bên jsp cũng phải có
    public ModelAndView createUserPage(@ModelAttribute("newUser") User nguyendinhhung) {
        System.out.println("run here: " + nguyendinhhung);
        // Sau khi tạo user, có thể redirect hoặc trả về view khác
        ModelAndView modelAndView = new ModelAndView("hello"); // view của jsp
        modelAndView.addObject("message", "User created successfully!");
        this.userService.handleSaveUser(nguyendinhhung);
        return modelAndView;
    }
}
