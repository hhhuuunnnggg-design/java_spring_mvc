package vn.hoidanit.laptopshop.controller;

import java.util.List;

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

    // ---------------------------------------

    @RequestMapping("/")
    public ModelAndView getHomePage() {
        List<User> arrUsers = this.userService.getAllUsersByEmail("lceo7093@gmail.com");
        System.out.println(arrUsers);
        ModelAndView modelAndView = new ModelAndView("hello"); // vewname là đường link dẫ đến jsp
        String test = this.userService.handleHello();
        modelAndView.addObject("eric", test);
        return modelAndView;
    }

    @RequestMapping("/admin/user")
    public ModelAndView getUserPage() {
        ModelAndView modelAndView = new ModelAndView("admin/user/table-user");
        List<User> users = this.userService.getAllUsers();
        System.out.println(users);
        modelAndView.addObject("tableListUser", users);
        return modelAndView;
    }

    @RequestMapping("/admin/user/create") // view submit
    public ModelAndView getuserPage() {
        ModelAndView modelAndView = new ModelAndView("admin/user/create");
        modelAndView.addObject("newUser", new User());
        return modelAndView;
    }

    // // value này ăn với action bên jsp
    @RequestMapping(value = "/admin/user/create", method = RequestMethod.POST) // nó kiểu có tác dụng xác nhận chức năng
                                                                               // submit thêm tài khoảng
    public ModelAndView createUserPage(@ModelAttribute("newUser") User nguyendinhhung) {// @ModelAttribute("newUser")
                                                                                        // bên jsp cũng phải có
        // Sau khi tạo user, có thể redirect hoặc trả về view khác
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/user"); // view của jsp
        this.userService.handleSaveUser(nguyendinhhung);
        return modelAndView;
    }
}
