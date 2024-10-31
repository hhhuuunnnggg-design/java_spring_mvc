package vn.hoidanit.laptopshop.controller.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import vn.hoidanit.laptopshop.entity.Product;
import vn.hoidanit.laptopshop.service.ProductService;

@Controller
public class ItemController {
    @Autowired
    private ProductService productService;

    @GetMapping("/product/{id}")
    public ModelAndView getHomePage(@PathVariable Long id) {
        Product products = this.productService.getProductById(id);
        ModelAndView viewProduct = new ModelAndView("client/product/detail");
        System.out.println(products);
        viewProduct.addObject("Product", products);
        return viewProduct;
    }

}
