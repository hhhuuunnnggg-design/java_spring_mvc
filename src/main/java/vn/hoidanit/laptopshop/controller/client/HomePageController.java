package vn.hoidanit.laptopshop.controller.client;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import jakarta.validation.Valid;
import vn.hoidanit.laptopshop.entity.Product;
import vn.hoidanit.laptopshop.entity.User;
import vn.hoidanit.laptopshop.entity.dto.RegisterDTO;
import vn.hoidanit.laptopshop.service.ProductService;
import vn.hoidanit.laptopshop.service.UserService;

@Controller
public class HomePageController {

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/")
    public ModelAndView getHomePage() {
        ModelAndView modelAndView = new ModelAndView("client/homepage/showHomePage");
        // 10 sản phẩm
        Pageable pageable = PageRequest.of(0, 10);
        Page<Product> products = productService.gethandleAllProducts(pageable);
        List<Product> prd = products.getContent();
        modelAndView.addObject("listProduct", prd);
        return modelAndView;
    }

    @GetMapping("/register")
    public ModelAndView getregister() {
        ModelAndView modelAndView = new ModelAndView("client/auth/register");
        RegisterDTO register = new RegisterDTO();
        modelAndView.addObject("newregister", register);
        return modelAndView;
    }

    @PostMapping("/register")
    public ModelAndView postregister(@ModelAttribute("newregister") @Valid RegisterDTO registerDTO,
            BindingResult bindingResult) {
        // start xuất lỗi
        List<FieldError> errors = bindingResult.getFieldErrors();
        for (FieldError error : errors) {
            System.out.println(error.getField() + " - " + error.getDefaultMessage());
        }
        // end xuất lỗi
        if (bindingResult.hasErrors()) {
            return new ModelAndView("client/auth/register");
        }

        User user = this.userService.registerDTOtoUser(registerDTO);
        String hashPassword = this.passwordEncoder.encode(registerDTO.getPassword());
        user.setPassword(hashPassword);

        this.userService.handleSaveUser(user);
        return new ModelAndView("redirect:/login");

    }

    @GetMapping("/login")
    public ModelAndView getlogin() {
        ModelAndView modelAndView = new ModelAndView("client/auth/login");
        return modelAndView;
    }

    @GetMapping("/access-deny")
    public ModelAndView getDenyPage() {
        ModelAndView modelAndView = new ModelAndView("client/auth/deny");
        return modelAndView;
    }

}
