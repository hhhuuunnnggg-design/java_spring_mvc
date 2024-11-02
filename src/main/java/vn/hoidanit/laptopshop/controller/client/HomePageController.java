package vn.hoidanit.laptopshop.controller.client;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
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

    @GetMapping("/")
    public ModelAndView getHomePage() {
        ModelAndView modelAndView = new ModelAndView("client/homepage/showHomePage");
        List<Product> products = productService.gethandleAllProducts();
        modelAndView.addObject("listProduct", products);
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
        if (bindingResult.hasErrors()) {
            return new ModelAndView("/register");
        }
        if (registerDTO.getPassWord().equals(registerDTO.getConfirmPassWord())) {
            System.out.println("nhập đúng regisster");
            User user = new User();
            user.setFullname(registerDTO.getFirstName() + " " + registerDTO.getLastName());
            user.setEmail(registerDTO.getEmail());
            user.setPassword(registerDTO.getPassWord());
            user.setRole(2);
            this.userService.handleSaveUser(user);

            return new ModelAndView("/login");
        } else {
            System.out.println("nhập sai confispasswword");
        }

        return new ModelAndView("redirect:/register");
    }

    @GetMapping("/login")
    public ModelAndView getlogin() {
        ModelAndView modelAndView = new ModelAndView("client/auth/login");
        return modelAndView;
    }

}
