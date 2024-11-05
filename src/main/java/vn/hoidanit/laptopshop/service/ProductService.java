package vn.hoidanit.laptopshop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.hoidanit.laptopshop.entity.Cart;
import vn.hoidanit.laptopshop.entity.CartDetail;
import vn.hoidanit.laptopshop.entity.Product;
import vn.hoidanit.laptopshop.entity.User;
import vn.hoidanit.laptopshop.repository.CartDetailRepository;
import vn.hoidanit.laptopshop.repository.CartRepository;
import vn.hoidanit.laptopshop.repository.ProductRepository;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartDetailRepository cartDetailRepository;

    @Autowired
    private UserService userService;

    public List<Product> gethandleAllProducts() {
        return this.productRepository.findAll();
    }

    public void handleDeleteProductId(Long id) {
        productRepository.deleteById(id);
    }

    public Product getProductById(Long id) {
        return this.productRepository.findById(id).orElse(null);
    }

    public Product handleSaveProduct(Product product) {
        return this.productRepository.save(product);
    }

    public void handelAddProductToCart(String email, Long productId) {
        User user = this.userService.getUserByEmail(email);
        System.out.println(user);
        if (user != null) {
            // Kiểm tra xem người dùng đã có giỏ hàng chưa
            Optional<Cart> cartOptional = cartRepository.findByUser(user);

            Cart cart;
            if (cartOptional.isEmpty()) {
                // Tạo mới giỏ hàng
                Cart newCart = new Cart();
                newCart.setUser(user);
                newCart.setSum(1);

                cart = cartRepository.save(newCart);
            } else {
                cart = cartOptional.get();
            }

            // Thêm chi tiết sản phẩm vào giỏ hàng
            Optional<Product> productOptional = productRepository.findById(productId);
            if (productOptional.isPresent()) {

                Product realProduct = productOptional.get();
                CartDetail cd = new CartDetail();
                cd.setCart(cart);
                cd.setProduct(realProduct);
                cd.setPrice(realProduct.getPrice());
                cd.setQuantity(1);

                cartDetailRepository.save(cd); // Lưu đối tượng CartDetail thực tế
            }
        }
    }
}