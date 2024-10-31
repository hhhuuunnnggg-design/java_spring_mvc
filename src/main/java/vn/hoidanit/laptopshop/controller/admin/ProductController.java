package vn.hoidanit.laptopshop.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import vn.hoidanit.laptopshop.entity.Product;
import vn.hoidanit.laptopshop.service.ProductService;
import vn.hoidanit.laptopshop.service.UploadService;

@Controller
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private UploadService uploadService;

    @GetMapping("/admin/product")
    public ModelAndView getProductPage() {
        ModelAndView modelAndView = new ModelAndView("admin/product/table-product"); // vewname là đường link dẫ đến jsp
        List<Product> products = this.productService.gethandleAllProducts();
        modelAndView.addObject("tableListProduct", products);

        return modelAndView;
    }

    // thêm (chưa xong)
    @GetMapping("/admin/product/create")
    public ModelAndView createProductForm() {
        ModelAndView modelAndView = new ModelAndView("admin/product/createProduct"); // vewname là đường link dẫ đến jsp
        modelAndView.addObject("newOneProduct", new Product());
        return modelAndView;
    }

    @PostMapping("/admin/product/create")
    public ModelAndView createProduct(@ModelAttribute("newOneProduct") Product newProduct,
            @RequestParam("hoidanitFile") MultipartFile file) {

        // Gọi UploadService để lưu ảnh vào thư mục "avatar" và nhận đường dẫn file
        String avatarFileName = this.uploadService.handleSaveUploadFile(file, "product");
        newProduct.setImage(avatarFileName);
        newProduct.setSold(0L);
        this.productService.handleSaveProduct(newProduct);
        return new ModelAndView("redirect:/admin/product");
    }
    // end thêm

    // start delete (chưa xong)
    @DeleteMapping("/admin/product/delete/{id}")
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
    public ModelAndView getUpdateProduct(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        ModelAndView modelAndView = new ModelAndView("admin/product/updateProduct");
        modelAndView.addObject("UpdateProductId", product); // Truyền đối tượng product vào ModelAndView
        System.out.println("đây chính là ảnh " + product.getImage());
        return modelAndView;
    }

    @PostMapping("/admin/product/update/{id}")
    public ModelAndView postUpdateProduct(@ModelAttribute("UpdateProductId") Product newProduct,
            @RequestParam("hoidanitFile") MultipartFile file) {
        Product CurentProduct = this.productService.getProductById(newProduct.getId());
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/product");
        String avatarFileName = this.uploadService.handleSaveUploadFile(file, "product");
        if (CurentProduct != null) {
            CurentProduct.setName(newProduct.getName());
            CurentProduct.setPrice(newProduct.getPrice());
            CurentProduct.setDetaildesc(newProduct.getDetaildesc());
            CurentProduct.setShortdesc(newProduct.getShortdesc());
            CurentProduct.setQuantity(newProduct.getQuantity());
            CurentProduct.setFactory(newProduct.getFactory());
            CurentProduct.setTarget(newProduct.getTarget());
            CurentProduct.setImage(avatarFileName);
            this.productService.handleSaveProduct(CurentProduct);

        }
        return modelAndView;
    }

}
