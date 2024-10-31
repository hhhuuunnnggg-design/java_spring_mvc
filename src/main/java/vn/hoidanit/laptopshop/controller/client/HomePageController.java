package vn.hoidanit.laptopshop.controller.client;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import vn.hoidanit.laptopshop.entity.Product;
import vn.hoidanit.laptopshop.entity.dto.RegisterDTO;
import vn.hoidanit.laptopshop.service.ProductService;

@Controller
public class HomePageController {

    @Autowired
    private ProductService productService;

    @GetMapping("/")
    public ModelAndView getHomePage() {
        ModelAndView modelAndView = new ModelAndView("client/homepage/showHomePage");
        List<Product> products = productService.gethandleAllProducts();
        modelAndView.addObject("listProduct", products);
        return modelAndView;
    }

    @GetMapping("/register")
    public ModelAndView getregister() {
        ModelAndView modelAndView = new ModelAndView("client/auth/register");
        RegisterDTO register = new RegisterDTO();
        modelAndView.addObject("newregister", register);
        return modelAndView;
    }

    @GetMapping("/login")
    public ModelAndView getlogin() {
        ModelAndView modelAndView = new ModelAndView("client/auth/login");
        return modelAndView;
    }

}
