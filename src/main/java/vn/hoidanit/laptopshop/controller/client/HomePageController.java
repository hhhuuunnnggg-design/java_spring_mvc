package vn.hoidanit.laptopshop.controller.client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import jakarta.validation.Valid;
import vn.hoidanit.laptopshop.entity.Order;
import vn.hoidanit.laptopshop.entity.Product;
import vn.hoidanit.laptopshop.entity.User;
import vn.hoidanit.laptopshop.entity.dto.RegisterDTO;
import vn.hoidanit.laptopshop.entity.test.Auth;
import vn.hoidanit.laptopshop.entity.test.Menu;
import vn.hoidanit.laptopshop.entity.test.Role_User;
import vn.hoidanit.laptopshop.entity.test.Rolee;
import vn.hoidanit.laptopshop.service.OrderService;
import vn.hoidanit.laptopshop.service.ProductService;
import vn.hoidanit.laptopshop.service.UserService;
import vn.hoidanit.laptopshop.util.Constant;

@Controller
public class HomePageController {

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private OrderService orderService;

    @GetMapping("/logintest")
    public ModelAndView getlogins(HttpSession session, User users) {
        ModelAndView modelAndView = new ModelAndView("client/auth/login");
        User user = userService.findByEmail(users.getEmail()).get(0); // Lấy bản ghi đầu tiên
        Role_User userRole = (Role_User) user.getRoleUsers().iterator().next();
        List<Menu> menuList = new ArrayList<>();
        Rolee role = userRole.getRolee();
        List<Menu> menuChildList = new ArrayList<>();
        for (Object obj : role.getAuth()) {
            Auth auth = (Auth) obj;
            Menu menu = auth.getMenu();
            if (menu.getParent_id() == 0 && menu.getOrder_index() != -1 && menu.getActive_flag() == 1
                    && auth.getPermission() == 1) {
                menu.setIdMenu(menu.getUrl().replace("/", "") + "Id");
                menuList.add(menu);
            } else if (menu.getParent_id() != 0 && menu.getOrder_index() != -1 && menu.getActive_flag() == 1
                    && auth.getPermission() == 1) {
                menu.setIdMenu(menu.getUrl().replace("/", "") + "Id");
                menuChildList.add(menu);
            }
        }
        for (Menu menu : menuList) {
            List<Menu> childList = new ArrayList<>();
            for (Menu childMenu : menuChildList) {
                if (childMenu.getParent_id() == menu.getId()) {
                    childList.add(childMenu);
                }
            }
            menu.setChild(childList);
        }
        sortMenu(menuList);
        for (Menu menu : menuList) {
            sortMenu(menu.getChild());
        }
        session.setAttribute(Constant.MENU_SESSION, menuList);
        session.setAttribute(Constant.USER_INFO, user);

        return modelAndView;
    }

    public void sortMenu(List<Menu> menus) {
        Collections.sort(menus, new Comparator<Menu>() {
            @Override
            public int compare(Menu o1, Menu o2) {
                return (int) (o1.getOrder_index() - o2.getOrder_index());
            }
        });
    }

    @GetMapping("/")
    public ModelAndView getHomePage(@RequestParam(value = "page", defaultValue = "1") int page) {
        ModelAndView modelAndView = new ModelAndView("client/homepage/showHomePage");
        // 10 sản phẩm
        Pageable pageable = PageRequest.of(page - 1, 4);
        Page<Product> products = productService.gethandleAllProductssUser(pageable);
        List<Product> prd = products.getContent();
        modelAndView.addObject("listProduct", prd);

        // trang hiện tại
        modelAndView.addObject("currentPage", page);
        // số lượng ở trang hiện tại
        modelAndView.addObject("totalPages", products.getTotalPages());
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

//    @GetMapping("/login")
//    public ModelAndView getlogin() {
//        ModelAndView modelAndView = new ModelAndView("client/auth/login");
//        return modelAndView;
//    }

    @GetMapping("/access-deny")
    public ModelAndView getDenyPage() {
        ModelAndView modelAndView = new ModelAndView("client/auth/deny");
        return modelAndView;
    }

    @GetMapping("/order-history")
    public String getOrderHistoryPage(Model model, HttpServletRequest request) {
        User currentUser = new User();// null
        HttpSession session = request.getSession(false);
        long id = (long) session.getAttribute("id");
        currentUser.setId(id);

        List<Order> orders = this.orderService.fetchOrderByUser(currentUser);
        model.addAttribute("orders", orders);

        return "client/cart/order-history";
    }

}
