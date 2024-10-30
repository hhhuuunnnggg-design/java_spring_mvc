package vn.hoidanit.laptopshop.controller.client;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ItemController {

    @GetMapping("/product/{id}")
    public ModelAndView getHomePage(@PathVariable Long id) {

        ModelAndView viewProduct = new ModelAndView("client/product/detail");
        return viewProduct;
    }

}
