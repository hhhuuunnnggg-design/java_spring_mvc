package vn.hoidanit.laptopshop.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import jakarta.validation.Valid;
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
    public ModelAndView getProductPage(@RequestParam(value = "page", defaultValue = "1") int page) {
        ModelAndView modelAndView = new ModelAndView("admin/product/table-product");
        // xây dụng phân trang
        Pageable pageable = PageRequest.of(page - 1, 2);
        Page<Product> products = this.productService.gethandleAllProducts(pageable);
        List<Product> listProducts = products.getContent();
        modelAndView.addObject("tableListProduct", listProducts);
        // trang hiện tại
        modelAndView.addObject("currentPage", page);
        // totalPages, tổng số trang
        modelAndView.addObject("totalPages", products.getTotalPages());
        return modelAndView;
    }

    @GetMapping("/admin/product/create")
    public ModelAndView createProductForm() {
        ModelAndView modelAndView = new ModelAndView("admin/product/createProduct"); // viewname là đường link dẫn đến
                                                                                     // jsp
        modelAndView.addObject("newOneProduct", new Product());
        return modelAndView;
    }

    @PostMapping("/admin/product/create")
    public ModelAndView createProduct(@Valid @ModelAttribute("newOneProduct") Product newProduct,
            BindingResult bindingResult,
            @RequestParam("hoidanitFile") MultipartFile file) {
        // xuất lỗi
        List<FieldError> err = bindingResult.getFieldErrors();
        for (FieldError listerr : err) {
            System.out.println(listerr.getField() + " - " + listerr.getDefaultMessage());
        }
        // Kiểm tra lỗi
        if (bindingResult.hasErrors()) {
            return new ModelAndView("admin/product/createProduct"); // Trả về view chứa form với lỗi
        }
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
        System.out.println("Deleting product with ID: " + id); // Thêm log để kiểm tra

        if (productService.getProductById(id) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }
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
        return modelAndView;
    }

    @PostMapping("/admin/product/update/{id}")
    public ModelAndView postUpdateProduct(@ModelAttribute("UpdateProductId") Product newProduct,
            @RequestParam("hoidanitFile") MultipartFile file) {
        Product currentProduct = this.productService.getProductById(newProduct.getId());
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/product");

        if (currentProduct != null) {
            // Nếu file không trống, upload và cập nhật đường dẫn ảnh mới
            if (!file.isEmpty()) {
                String avatarFileName = this.uploadService.handleSaveUploadFile(file, "product");
                currentProduct.setImage(avatarFileName); // Cập nhật ảnh mới
            }

            // Cập nhật các thông tin khác của sản phẩm
            currentProduct.setName(newProduct.getName());
            currentProduct.setPrice(newProduct.getPrice());
            currentProduct.setDetaildesc(newProduct.getDetaildesc());
            currentProduct.setShortdesc(newProduct.getShortdesc());
            currentProduct.setQuantity(newProduct.getQuantity());
            currentProduct.setFactory(newProduct.getFactory());
            currentProduct.setTarget(newProduct.getTarget());
            this.productService.handleSaveProduct(currentProduct);
        }

        return modelAndView;
    }

    // end sua

}
