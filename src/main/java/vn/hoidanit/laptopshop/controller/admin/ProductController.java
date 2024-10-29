package vn.hoidanit.laptopshop.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import vn.hoidanit.laptopshop.service.UserService;

@Controller
public class ProductController {
    @Autowired
    private UserService userService;

    @GetMapping("/admin/product")
    public ModelAndView getHomePage() {
        ModelAndView modelAndView = new ModelAndView("admin/product/showProduct"); // vewname là đường link dẫ đến jsp
        return modelAndView;
    }
}
