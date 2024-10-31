package vn.hoidanit.laptopshop.controller.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import vn.hoidanit.laptopshop.service.ProductService;

@Controller
public class HomePageController {
    @Autowired
    private ProductService productService;

    // return "client/homepage/showHomePage"; getHomePage
    @GetMapping("/")
    public ModelAndView getHomePage() {
        ModelAndView modelAndView = new ModelAndView("client/homepage/showHomePage");
        return modelAndView;
    }

}
