package vn.hoidanit.laptopshop.controller.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import vn.hoidanit.laptopshop.entity.User;
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

    @GetMapping("/product")
    public ModelAndView getProductList(@RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "name", required = false) Optional<String> name,
            @RequestParam(value = "min-price", required = false) Optional<String> minPriceOptional,
            @RequestParam(value = "price", required = false) Optional<String> priceOptional,
            @RequestParam(value = "factory", required = false) Optional<String> factoryOptional,
            @RequestParam(value = "max-price", required = false) Optional<String> maxPriceOptional) {
        ModelAndView modelAndView = new ModelAndView("client/homepage/detailProduct");
        Pageable pageable = PageRequest.of(page - 1, 60);

        // case 0:
        String nameParame = name.isPresent() ? name.get() : "";
        Page<Product> products = productService.gethandleAllProductWithPect(pageable,
                nameParame);

        // case 1: (min)
        // Double minPriceParam = minPriceOptional.isPresent() ?
        // Double.parseDouble(minPriceOptional.get()) : 0;
        // Page<Product> products = productService.gethandleAllProductWithPect(pageable,
        // minPriceParam);

        // case 2 (max)
        // Double maxPriceParam = maxPriceOptional.isPresent() ?
        // Double.parseDouble(maxPriceOptional.get()) : 1000000000;
        // Page<Product> products = productService.gethandleAllProductWithPect(pageable,
        // maxPriceParam);

        // case 3 factory=Apple
        // String factoryOptionalParame = factoryOptional.isPresent() ?
        // factoryOptional.get() : "";
        // Page<Product> products =
        // productService.gethandleAllProductWithPect(pageable,factoryOptionalParame);

        // case 4 factory=Apple,dell (cái này là demo)
        // List<String> factoryOptionalParam = new ArrayList<>();
        // Page<Product> products;
        // if (factoryOptional.isPresent() && !factoryOptional.get().isEmpty()) {
        // factoryOptionalParam = Arrays.asList(factoryOptional.get().split(","));
        // products = productService.gethandleAllProductWithPect(pageable,
        // factoryOptionalParam);
        // } else {
        // products = this.productService.gethandleAllProductss(pageable);
        // }

        // case 5 (tu 10tr->15tr)
        // String price=priceOptional.isPresent() ? priceOptional.get() : "";
        // Page<Product> products = productService.gethandleAllProductWithPect(pageable,
        // price);

        // case 6 (tu 10tr->15tr va tu 20tr->30tr)
        // List<String> PricesOptionalParam = new ArrayList<>();
        // Page<Product> products;
        // if (priceOptional.isPresent() && !priceOptional.get().isEmpty()) {
        // PricesOptionalParam = Arrays.asList(priceOptional.get().split(","));
        // products = productService.gethandleAllProductWithPect(pageable,
        // PricesOptionalParam);
        // } else {
        // products = this.productService.gethandleAllProductss(pageable);
        // }

        // List<String> price = Arrays.asList(priceOptional.orElse("0").split(","));
        // Page<Product> products =
        // this.productService.gethandleAllProductWithPect(pageable,
        // price);

        List<Product> prd = products.getContent();
        modelAndView.addObject("listProduct", prd);
        modelAndView.addObject("currentPage", page);
        modelAndView.addObject("totalPages", products.getTotalPages());

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
            @RequestParam("receiverPhone") String receiverPhone) {
        User currentUser = new User();// null
        HttpSession session = request.getSession(false);
        long id = (long) session.getAttribute("id");
        currentUser.setId(id);

        this.productService.handlePlaceOrder(currentUser, session, receiverName, receiverAddress, receiverPhone);

        return "redirect:/";
    }

}
