package vn.hoidanit.laptopshop.controller.admin;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.ServletContext;
import vn.hoidanit.laptopshop.entity.User;
import vn.hoidanit.laptopshop.service.UploadService;
import vn.hoidanit.laptopshop.service.UserService;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private ServletContext servletContext;

    @Autowired
    private UploadService uploadService;

    @RequestMapping("/")
    public ModelAndView getHelloPage() {
        List<User> arrUsers = this.userService.getAllUsersByEmail("lceo7093@gmail.com");
        ModelAndView modelAndView = new ModelAndView("hello");
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
        User user = this.userService.getUserById(id);
        ModelAndView modelAndView = new ModelAndView("admin/user/showView");
        if (user != null) {
            modelAndView.addObject("detailUserId", user);
            modelAndView.addObject("userId", id);
        } else {
            modelAndView.addObject("errorMessage", "Không tìm thấy người dùng với ID " + id);
        }
        return modelAndView;
    }

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
        }
        return modelAndView;
    }

    // start delete
    @DeleteMapping("/admin/detele/{id}")
    public ResponseEntity<String> deleteUserId(@PathVariable Long id) {
        userService.handleDeleteUserId(id);
        return ResponseEntity.ok("User với ID " + id + " đã bị xóa");
    }

    @RequestMapping(value = "/admin/user/create", method = RequestMethod.GET)
    public ModelAndView createUserForm() {
        ModelAndView modelAndView = new ModelAndView("admin/user/create");
        modelAndView.addObject("newUser", new User());
        return modelAndView;
    }

    @RequestMapping(value = "/admin/user/create", method = RequestMethod.POST)
    public ModelAndView createUser(@ModelAttribute("newUser") User newUser,
            @RequestParam("hoidanitFile") MultipartFile file) {
        this.uploadService.handleSaveUploadFile(file, "avatar");
        this.userService.handleSaveUser(newUser);
        return new ModelAndView("redirect:/admin/user");
    }
}
