package vn.hoidanit.laptopshop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;
import vn.hoidanit.laptopshop.entity.Cart;
import vn.hoidanit.laptopshop.entity.CartDetail;
import vn.hoidanit.laptopshop.entity.Order;
import vn.hoidanit.laptopshop.entity.OrderDetail;
import vn.hoidanit.laptopshop.entity.Product;
import vn.hoidanit.laptopshop.entity.User;
import vn.hoidanit.laptopshop.repository.CartDetailRepository;
import vn.hoidanit.laptopshop.repository.CartRepository;
import vn.hoidanit.laptopshop.repository.OrderDetailRepository;
import vn.hoidanit.laptopshop.repository.OrderRepository;
import vn.hoidanit.laptopshop.repository.ProductRepository;
import vn.hoidanit.laptopshop.service.specification.ProductSpec;

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

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    // case 0:
    // public Page<Product> gethandleAllProductWithPect(Pageable page, String name)
    // {
    // return this.productRepository.findAll(ProductSpec.namelike(name), page);
    // }

    // case1:
    // public Page<Product> gethandleAllProductWithPect(Pageable page, Double
    // minPrice)
    // {
    // return this.productRepository.findAll(ProductSpec.minPrice(minPrice), page);
    // }

    // case2:
//    public Page<Product> gethandleAllProductWithPect(Pageable page, Double Maxprice) {
//        return this.productRepository.findAll(ProductSpec.maxPrice(Maxprice), page);
//    }

    //case 3
//    public Page<Product> gethandleAllProductWithPect(Pageable page, String fatory) {
//        return this.productRepository.findAll(ProductSpec.factoryLike(fatory), page);
//    }

    //case 4
    public Page<Product> gethandleAllProductWithPect(Pageable page, List<String> fatory) {
        return this.productRepository.findAll(ProductSpec.mathListFactory(fatory),page);
    }


    public Page<Product> gethandleAllProductss(Pageable page) {
        return this.productRepository.findAll(page);
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

    public void handelAddProductToCart(String email, Long productId, HttpSession session) {
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
                newCart.setSum(0);

                cart = cartRepository.save(newCart);
            } else {
                cart = cartOptional.get();
            }

            // Thêm chi tiết sản phẩm vào giỏ hàng
            Optional<Product> productOptional = productRepository.findById(productId);
            if (productOptional.isPresent()) {
                Product realProduct = productOptional.get();

                // Kiểm tra xem sản phẩm đã có trong giỏ hàng chưa
                CartDetail oldCartDetail = cartDetailRepository.findByCartAndProduct(cart, realProduct);
                if (oldCartDetail == null) {
                    // Nếu sản phẩm chưa có trong giỏ hàng, thêm sản phẩm mới
                    CartDetail cd = new CartDetail();
                    cd.setCart(cart);
                    cd.setProduct(realProduct);
                    cd.setPrice(realProduct.getPrice());
                    cd.setQuantity(1);

                    // Cập nhật tổng số lượng sản phẩm trong giỏ hàng
                    int newSum = cart.getSum() + 1;
                    cart.setSum(newSum);
                    cartRepository.save(cart);

                    // Đặt lại `sum` vào session
                    session.setAttribute("sum", newSum);

                    cartDetailRepository.save(cd); // Lưu đối tượng CartDetail thực tế
                } else {
                    // Nếu sản phẩm đã tồn tại trong giỏ hàng, tăng số lượng lên 1
                    oldCartDetail.setQuantity(oldCartDetail.getQuantity() + 1);
                    cartDetailRepository.save(oldCartDetail); // Cập nhật lại CartDetail

                    // Cập nhật tổng số lượng sản phẩm trong giỏ hàng
                    int newSum = cart.getSum();
                    cart.setSum(newSum);
                    cartRepository.save(cart);

                    // Đặt lại `sum` vào session
                    session.setAttribute("sum", newSum);
                }
            }
        }
    }

    public Cart fetchByUser(User user) {
        return this.cartRepository.findByUser(user).orElse(null);
    }

    public void handleRemoveCartDetail(long cartDetailId, HttpSession session) {
        Optional<CartDetail> cartDetailOptional = this.cartDetailRepository.findById(cartDetailId);
        if (cartDetailOptional.isPresent()) {
            CartDetail cartDetail = cartDetailOptional.get();
            Cart currentCart = cartDetail.getCart();
            this.cartDetailRepository.deleteById(cartDetailId);

            if (currentCart.getSum() > 1) {
                int s = currentCart.getSum() - 1;
                currentCart.setSum(s);
                session.setAttribute("sum", currentCart.getSum());
                this.cartRepository.save(currentCart);
            } else {
                this.cartRepository.deleteById(currentCart.getId());
                session.setAttribute("sum", 0);
                this.cartRepository.save(currentCart);
            }
        }
    }

    public void handleUpdateCartBeforeCheckout(List<CartDetail> cartDetails) {
        for (CartDetail cartDetail : cartDetails) {
            Optional<CartDetail> cdOptional = this.cartDetailRepository.findById(cartDetail.getId());
            if (cdOptional.isPresent()) {
                CartDetail currentCartDetail = cdOptional.get();
                currentCartDetail.setQuantity(cartDetail.getQuantity());
                this.cartDetailRepository.save(currentCartDetail);
            }
        }
    }

    public void handlePlaceOrder(User user, HttpSession session, String receiverName, String receiverAddress,
            String receiverPhone) {
        // step 1: get cart by user
        Cart cart = this.cartRepository.findByUser(user).orElse(null);
        if (cart != null) {
            List<CartDetail> cartDetails = cart.getCartDetails();
            if (cartDetails != null) {

                // step1: create order
                Order order = new Order();
                order.setUser(user);
                order.setReceiverName(receiverName);
                order.setReceiverAddress(receiverAddress);
                order.setReceiverPhone(receiverPhone);
                order.setStatus("PENDING");

                double sum = 0;
                for (CartDetail cd : cartDetails) {
                    sum += cd.getPrice();
                }
                order.setTotalPrice(sum);

                // order kiểu này giúp lấy đc id của nó lun
                order = this.orderRepository.save(order);

                for (CartDetail cd : cartDetails) {
                    OrderDetail orderDetail = new OrderDetail();
                    orderDetail.setOrder(order);
                    orderDetail.setProduct(cd.getProduct());
                    orderDetail.setPrice(cd.getPrice());
                    orderDetail.setQuantity(cd.getQuantity());

                    this.orderDetailRepository.save(orderDetail);
                }
                // step 2: delete cart_detail and cart
                for (CartDetail cd : cartDetails) {
                    this.cartDetailRepository.deleteById(cd.getId());
                }

                this.cartRepository.deleteById(cart.getId());

                // step 3 : update session
                session.setAttribute("sum", 0);

            }

        }

    }

    public void handlePlaceOrders(
            User user, HttpSession session,
            String receiverName, String receiverAddress, String receiverPhone) {

        // step 1: get cart by user
        Cart cart = this.cartRepository.findByUser(user).orElse(null);
        if (cart != null) {
            List<CartDetail> cartDetails = cart.getCartDetails();

            if (cartDetails != null) {

                // create order
                Order order = new Order();
                order.setUser(user);
                order.setReceiverName(receiverName);
                order.setReceiverAddress(receiverAddress);
                order.setReceiverPhone(receiverPhone);
                order.setStatus("PENDING");

                double sum = 0;
                for (CartDetail cd : cartDetails) {
                    sum += cd.getPrice();
                }
                order.setTotalPrice(sum);
                order = this.orderRepository.save(order);

                // create orderDetail

                for (CartDetail cd : cartDetails) {
                    OrderDetail orderDetail = new OrderDetail();
                    orderDetail.setOrder(order);
                    orderDetail.setProduct(cd.getProduct());
                    orderDetail.setPrice(cd.getPrice());
                    orderDetail.setQuantity(cd.getQuantity());

                    this.orderDetailRepository.save(orderDetail);
                }

                // step 2: delete cart_detail and cart
                for (CartDetail cd : cartDetails) {
                    this.cartDetailRepository.deleteById(cd.getId());
                }

                this.cartRepository.deleteById(cart.getId());

                // step 3 : update session
                session.setAttribute("sum", 0);
            }
        }

    }
}