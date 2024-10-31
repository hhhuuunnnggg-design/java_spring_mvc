package vn.hoidanit.laptopshop.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
        modelAndView.addObject("tableListProduct", products);

        return modelAndView;
    }

    // thêm (chưa xong)
    @GetMapping("/admin/product/create")
    public ModelAndView getCreateProductPage() {
        ModelAndView modelAndView = new ModelAndView("admin/product/createProduct"); // vewname là đường link dẫ đến jsp
        return modelAndView;
    }

    // start delete (chưa xong)
    @DeleteMapping("/admin/product/detele/{id}")
    public ResponseEntity<String> deleteProductId(@PathVariable Long id) {
        productService.handleDeleteProductId(id);
        return ResponseEntity.ok("Product với ID " + id + " đã bị xóa");

    }

    // view
    @GetMapping("/admin/product/{id}")
    public ModelAndView getProductDetailPage(@PathVariable Long id) {
        Product product = this.productService.getProductById(id);
        ModelAndView modelAndView = new ModelAndView("admin/product/showProduct");
        System.out.println(product);
        if (product != null) {
            modelAndView.addObject("detailproductId", product);
            modelAndView.addObject("productId", id);
        } else {
            modelAndView.addObject("errorMessage", "Không tìm thấy người dùng với ID " +
                    id);
        }
        return modelAndView;
    }

    // sửa
    @GetMapping("/admin/product/update/{id}")
    public ModelAndView postUpdateProduct(@PathVariable Long id, @ModelAttribute("newProduct") Product nguyendinhhung) {
        System.out.println("bạn đang update product với id là: " + id);
        ModelAndView test = new ModelAndView("admin/product/updateProduct");
        test.addObject("test2", test);

        return test;
    }

}
