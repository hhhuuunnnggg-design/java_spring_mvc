package vn.hoidanit.laptopshop.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import vn.hoidanit.laptopshop.entity.Product;
import vn.hoidanit.laptopshop.service.ProductService;

@Controller
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/admin/product")
    public ModelAndView getProductPage() {
        ModelAndView modelAndView = new ModelAndView("admin/product/table-product"); // vewname là đường link dẫ đến jsp
        List<Product> products = this.productService.gethandleAllProducts();
        System.out.println(products);
        return modelAndView;
    }

    @GetMapping("/admin/product/create")
    public ModelAndView getCreateProductPage() {
        ModelAndView modelAndView = new ModelAndView("admin/product/createProduct"); // vewname là đường link dẫ đến jsp
        return modelAndView;
    }

}
