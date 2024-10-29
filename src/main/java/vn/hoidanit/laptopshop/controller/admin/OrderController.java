package vn.hoidanit.laptopshop.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import vn.hoidanit.laptopshop.service.UserService;

@Controller
public class OrderController {
    @Autowired
    private UserService userService;

    @GetMapping("/admin/order")
    public ModelAndView getHomePage() {
        ModelAndView modelAndView = new ModelAndView("admin/order/showOrder"); // vewname là đường link dẫ đến jsp
        return modelAndView;
    }
}
