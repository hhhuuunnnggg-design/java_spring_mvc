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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import vn.hoidanit.laptopshop.entity.User;
import vn.hoidanit.laptopshop.service.UserService;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    // Trang chính
    @RequestMapping("/")
    public ModelAndView getHelloPage() {
        List<User> arrUsers = this.userService.getAllUsersByEmail("lceo7093@gmail.com");

        ModelAndView modelAndView = new ModelAndView("hello"); // view name là đường dẫn đến JSP
        String test = this.userService.handleHello();
        modelAndView.addObject("eric", test);
        return modelAndView;
    }

    // Trang danh sách người dùng
    @RequestMapping("/admin/user")
    public ModelAndView getUserPage() {
        ModelAndView modelAndView = new ModelAndView("admin/user/table-user");
        List<User> users = this.userService.getAllUsers();
        modelAndView.addObject("tableListUser", users);
        return modelAndView;
    }

    // Chi tiết người dùng
    @GetMapping("/admin/user/{id}")
    public ModelAndView getUserDetailPage(@PathVariable Long id) {
        User user = this.userService.getUserById(id);
        ModelAndView modelAndView = new ModelAndView("admin/user/showView");
        if (user != null) {
            modelAndView.addObject("detailUserId", user);
            modelAndView.addObject("userId", id);
        } else {
            // Xử lý khi không tìm thấy người dùng
            modelAndView.addObject("errorMessage", "Không tìm thấy người dùng với ID " + id);
        }
        return modelAndView;
    }

    // Cập nhật người dùng
    @GetMapping("/admin/update/{id}")
    public ModelAndView getUpdateUser(@PathVariable Long id) {
        User user = this.userService.getUserById(id);
        ModelAndView modelAndView = new ModelAndView("admin/user/update");
        if (user != null) {
            modelAndView.addObject("UpdateUserId", user);
            modelAndView.addObject("userId", id);
        } else {
            modelAndView.addObject("errorMessage", "Không tìm thấy người dùng với ID " + id);
        }
        return modelAndView;
    }

    @PostMapping("/admin/update/{id}")
    public ModelAndView postUpdateUser(@PathVariable Long id, @ModelAttribute("newUser") User updatedUser) {
        User currentUser = this.userService.getUserById(id);
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/user");
        if (currentUser != null) {
            currentUser.setEmail(updatedUser.getEmail());
            currentUser.setFullname(updatedUser.getFullname());
            currentUser.setAdress(updatedUser.getAdress());
            currentUser.setPhone(updatedUser.getPhone());
            currentUser.setPassword(updatedUser.getPassword());
            this.userService.handleSaveUser(currentUser);
        } else {
            // Có thể thêm thông báo thất bại ở đây
        }
        return modelAndView;
    }

    // Xóa người dùng
    @DeleteMapping("/admin/detele/{id}")
    public ResponseEntity<String> deleteUserId(@PathVariable Long id) {
        userService.handleDeleteUserId(id);
        return ResponseEntity.ok("User với ID " + id + " đã bị xóa");
    }

    // Trang tạo người dùng
    @RequestMapping("/admin/user/create") // view submit
    public ModelAndView getCreateUserPage() {
        ModelAndView modelAndView = new ModelAndView("admin/user/create");
        modelAndView.addObject("newUser", new User());
        return modelAndView;
    }

    @RequestMapping(value = "/admin/user/create", method = RequestMethod.POST)
    public ModelAndView createUserPage(@ModelAttribute("newUser") User newUser) {
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/user");
        if (newUser != null) {
            this.userService.handleSaveUser(newUser);
        } else {
            // Có thể thêm thông báo thất bại ở đây
        }
        return modelAndView;
    }
}
