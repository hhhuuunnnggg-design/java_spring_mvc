package vn.hoidanit.laptopshop.controller.client;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import vn.hoidanit.laptopshop.entity.Cart;
import vn.hoidanit.laptopshop.entity.CartDetail;
import vn.hoidanit.laptopshop.entity.Product;
import vn.hoidanit.laptopshop.entity.Product_;
import vn.hoidanit.laptopshop.entity.User;
import vn.hoidanit.laptopshop.entity.dto.ProductCriteriaDTO;
import vn.hoidanit.laptopshop.service.ProductService;
import vn.hoidanit.laptopshop.service.VNPayService;

@Controller
public class ItemController {
    @Autowired
    private ProductService productService;
    @Autowired
    private VNPayService VNPayService;

    @GetMapping("/product/{id}")
    public ModelAndView getHomePage(@PathVariable Long id) {
        Product products = this.productService.getProductById(id);
        ModelAndView viewProduct = new ModelAndView("client/product/detail");
        System.out.println(products);
        viewProduct.addObject("Product", products);
        return viewProduct;
    }

    @GetMapping("/product")
    public ModelAndView getProductList(@RequestParam(value = "page", defaultValue = "1") int page,
            ProductCriteriaDTO productCriteriaDTO,
            HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("client/homepage/detailProduct");

        Pageable pageable = PageRequest.of(page - 1, 5);

        // theo giá cả
        if (productCriteriaDTO.getSort() != null && productCriteriaDTO.getSort().isPresent()) {
            String sort = productCriteriaDTO.getSort().get();
            if (sort.equals("gia-tang-dan")) {
                pageable = PageRequest.of(page - 1, 5, Sort.by(Product_.PRICE).ascending());
            } else if (sort.equals("gia-giam-dan")) {
                pageable = PageRequest.of(page - 1, 5, Sort.by(Product_.PRICE).descending());
            }
        }
        // -------------
        Page<Product> products = productService.gethandleAllProductWithPect(pageable, productCriteriaDTO);
        // -------------
        String qs = request.getQueryString();
        if (qs != null && !qs.isBlank()) {
            // remove page
            qs = qs.replace("page=" + page, "");
        }

        List<Product> prd = products.getContent();
        modelAndView.addObject("listProduct", prd);
        modelAndView.addObject("currentPage", page);
        modelAndView.addObject("totalPages", products.getTotalPages());
        modelAndView.addObject("queryString", qs);

        return modelAndView;
    }

    @GetMapping("/cart")
    public ModelAndView getCartPage(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("client/cart/showCart");
        HttpSession session = request.getSession(false);
        User user = new User();
        Long userId = (Long) session.getAttribute("id");
        user.setId(userId);

        Cart cart = this.productService.fetchByUser(user);

        // List<CartDetail> cartDetails = cart.getCartDetails();
        List<CartDetail> cartDetails = cart == null ? new ArrayList<CartDetail>() : cart.getCartDetails();

        double totalPrice = 0;
        for (CartDetail cd : cartDetails) {
            totalPrice += cd.getPrice() * cd.getQuantity();
        }
        modelAndView.addObject("cartDetails", cartDetails);
        modelAndView.addObject("totalPrice", totalPrice);

        modelAndView.addObject("cart", cart);
        return modelAndView;
    }

    @PostMapping("/add-product-to-cart/{id}")
    public ModelAndView addProductToCart(@PathVariable Long id, HttpServletRequest request) {

        HttpSession session = request.getSession(false); // Lấy session hiện tại, trả về null nếu session chưa tồn tại

        Long productId = id; // Gán giá trị của {id} từ URL vào biến productId

        String email = (String) session.getAttribute("email"); // Lấy email người dùng từ session (cần thiết cho thao
                                                               // tác giỏ hàng)

        // Gọi phương thức trong ProductService để thêm sản phẩm vào giỏ hàng
        // Truyền vào email, productId và session để xử lý
        this.productService.handelAddProductToCart(email, productId, session);

        // Khởi tạo ModelAndView để điều hướng người dùng về trang chủ sau khi thêm sản
        // phẩm
        ModelAndView modelAndView = new ModelAndView("redirect:/");

        return modelAndView; // Trả về ModelAndView để chuyển hướng người dùng
    }

    // xoa gio hang
    @PostMapping("/delete-cart-product/{id}")
    public ModelAndView deleteCartDetail(@PathVariable Long id,
            HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("redirect:/cart");
        HttpSession session = request.getSession(false);
        Long cartDetailId = id;
        this.productService.handleRemoveCartDetail(cartDetailId, session);

        return modelAndView;
    }

    @GetMapping("/checkout")
    public String getCheckOutPage(Model model, HttpServletRequest request) {
        User currentUser = new User();// null
        HttpSession session = request.getSession(false);
        long id = (long) session.getAttribute("id");
        currentUser.setId(id);

        Cart cart = this.productService.fetchByUser(currentUser);

        List<CartDetail> cartDetails = cart == null ? new ArrayList<CartDetail>() : cart.getCartDetails();

        double totalPrice = 0;
        for (CartDetail cd : cartDetails) {
            totalPrice += cd.getPrice() * cd.getQuantity();
        }

        model.addAttribute("cartDetails", cartDetails);
        model.addAttribute("totalPrice", totalPrice);

        return "client/cart/checkout";
    }

    @PostMapping("/confirm-checkout")
    public String getCheckOutPage(@ModelAttribute("cart") Cart cart) {
        List<CartDetail> cartDetails = cart == null ? new ArrayList<CartDetail>() : cart.getCartDetails();
        this.productService.handleUpdateCartBeforeCheckout(cartDetails);
        return "redirect:/checkout";
    }

    @PostMapping("/place-order")
    public String handlePlaceOrder(
            HttpServletRequest request,
            @RequestParam("receiverName") String receiverName,
            @RequestParam("receiverAddress") String receiverAddress,
            @RequestParam("receiverPhone") String receiverPhone,
            @RequestParam("paymentMethod") String paymentMethod,
            @RequestParam("totalPrice") String totalPrice) throws UnsupportedEncodingException {
        User currentUser = new User();// null
        HttpSession session = request.getSession(false);
        long id = (long) session.getAttribute("id");
        currentUser.setId(id);

        final String uuid = UUID.randomUUID().toString().replace("-", "");

        this.productService.handlePlaceOrder(currentUser, session,
                receiverName, receiverAddress, receiverPhone,
                paymentMethod, uuid);

        if (!paymentMethod.equals("COD")) {
            // todo: redirect to VNPAY
            String ip = this.VNPayService.getIpAddress(request);
            String vnpUrl = this.VNPayService.generateVNPayURL(Double.parseDouble(totalPrice), uuid, ip);

            return "redirect:" + vnpUrl;
        }

        return "redirect:/thanks";

    }

    @GetMapping("/thanks")
    public String getThankYouPage(
            Model model,
            @RequestParam("vnp_ResponseCode") Optional<String> vnpayResponseCode,
            @RequestParam("vnp_TxnRef") Optional<String> paymentRef) {

        if (vnpayResponseCode.isPresent() && paymentRef.isPresent()) {
            // thanh toán qua VNPAY, cập nhật trạng thái order
            String paymentStatus = vnpayResponseCode.get().equals("00")
                    ? "PAYMENT_SUCCEED"
                    : "PAYMENT_FAILED";
            this.productService.updatePaymentStatus(paymentRef.get(), paymentStatus);
        }

        return "client/cart/thanks";
    }

}
