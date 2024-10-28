package vn.hoidanit.laptopshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import vn.hoidanit.laptopshop.entity.User;
import vn.hoidanit.laptopshop.service.UserService;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    // -------------------------------------
    // @RequestMapping("/")
    // public String getHomePage(Model model) {
    // String test = this.userService.handleHello();
    // model.addAttribute("eric", test);
    // return "hello";
    // }

    // @RequestMapping("/admin/user")
    // public String getuserPage(Model model) {
    // model.addAttribute("newUser", new User());
    // return "admin/user/create";
    // }

    // @RequestMapping(value = "/admin/user/create", method = RequestMethod.POST)
    // public String creatUserPage(Model model, @ModelAttribute("newUser") User
    // nguyendinhhung) {
    // System.out.println("run here: " + nguyendinhhung);
    // return "hello";
    // }
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
        ModelAndView modelAndView = new ModelAndView("hello");
        modelAndView.addObject("message", "User created successfully!");
        return modelAndView;
    }
}
