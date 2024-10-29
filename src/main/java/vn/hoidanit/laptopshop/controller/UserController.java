package vn.hoidanit.laptopshop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import vn.hoidanit.laptopshop.entity.User;
import vn.hoidanit.laptopshop.service.UserService;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    // ---------------------------------------

    @RequestMapping("/")
    public ModelAndView getHomePage() {
        List<User> arrUsers = this.userService.getAllUsersByEmail("lceo7093@gmail.com");

        ModelAndView modelAndView = new ModelAndView("hello"); // vewname là đường link dẫ đến jsp
        String test = this.userService.handleHello();
        modelAndView.addObject("eric", test);
        return modelAndView;
    }

    @RequestMapping("/admin/user")
    public ModelAndView getUserPage() {
        ModelAndView modelAndView = new ModelAndView("admin/user/table-user");
        List<User> users = this.userService.getAllUsers();

        modelAndView.addObject("tableListUser", users);
        return modelAndView;
    }

    @GetMapping("/admin/user/{id}")
    public ModelAndView getUserDetailPage(@PathVariable Long id) {

        User userId = this.userService.getUserById(id);
        ModelAndView modelAndView = new ModelAndView("admin/user/showView");
        modelAndView.addObject("detailUserId", userId);
        modelAndView.addObject("userId", id);
        return modelAndView;
    }

    // ---------start uơdate
    @GetMapping("/admin/update/{id}")
    public ModelAndView getUpdateUser(@PathVariable Long id) {
        User userId = this.userService.getUserById(id);
        System.out.println(userId);
        ModelAndView modelAndView = new ModelAndView("admin/user/update"); // ăn theo file của url
        modelAndView.addObject("UpdateUserId", userId);
        modelAndView.addObject("userId", id);
        return modelAndView;
    }

    @PostMapping("/admin/update/{id}")
    public ModelAndView postUpdateUser(@ModelAttribute("newUser") User nguyendinhhung) {
        User CurrentUser = this.userService.getUserById(nguyendinhhung.getId());
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/user"); // ăn theo file của url
        if (CurrentUser != null) {
            CurrentUser.setEmail(nguyendinhhung.getEmail());
            CurrentUser.setFullname(nguyendinhhung.getFullname());
            CurrentUser.setAdress(nguyendinhhung.getAdress());
            CurrentUser.setPhone(nguyendinhhung.getPhone());
            CurrentUser.setPassword(nguyendinhhung.getPassword());
            this.userService.handleSaveUser(CurrentUser);
        }
        return modelAndView;
    }
    // end update

    // start delete
    @DeleteMapping("/admin/detele/{id}")
    public ResponseEntity<String> deleteUserId(@PathVariable Long id) {
        userService.handleDeleteUserId(id);
        return ResponseEntity.ok("User với ID " + id + " đã bị xóa");
    }

    // end detlete

    @RequestMapping("/admin/user/create") // view submit
    public ModelAndView getcreateUserPage() {
        ModelAndView modelAndView = new ModelAndView("admin/user/create");
        modelAndView.addObject("newUser", new User());
        return modelAndView;
    }

    // value này ăn với action bên jsp
    @RequestMapping(value = "/admin/user/create", method = RequestMethod.POST) // nó kiểu có tác dụng xác nhận chức năng
                                                                               // submit thêm tài khoảng
    public ModelAndView createUserPage(@ModelAttribute("newUser") User nguyendinhhung) {// @ModelAttribute("newUser")
                                                                                        // bên jsp cũng phải có
        // Sau khi tạo user, có thể redirect hoặc trả về view khác
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/user"); // view của jsp
        this.userService.handleSaveUser(nguyendinhhung);
        return modelAndView;
    }
}
