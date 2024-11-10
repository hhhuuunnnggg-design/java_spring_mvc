package vn.hoidanit.laptopshop.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.ServletContext;
import jakarta.validation.Valid;
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

    @Autowired
    private PasswordEncoder passwordEncoder;

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

    // ---------start uơdate
    @GetMapping("/admin/user/update/{id}")
    public ModelAndView getUpdateUser(@PathVariable Long id) {
        User userId = this.userService.getUserById(id);
        ModelAndView modelAndView = new ModelAndView("admin/user/update"); // ăn theo file của url
        modelAndView.addObject("UpdateUserId", userId);
        modelAndView.addObject("avatarPath", userId.getAvatar()); // Thêm đường dẫn avatar
        modelAndView.addObject("userId", id);
        return modelAndView;
    }

    @PostMapping("/admin/user/update/{id}")
    public ModelAndView postUpdateUser(@Valid @ModelAttribute("newUser") User nguyendinhhung,
            BindingResult bindingResult,
            @RequestParam("hoidanitFile") MultipartFile file) {
        User CurrentUser = this.userService.getUserById(nguyendinhhung.getId());

        // String avatarFileName = this.uploadService.handleSaveUploadFile(file,
        // "avatar");
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/user");
        // ăn theo file của url
        if (CurrentUser != null) {
            if (file.isEmpty()) {
                String avatarFileName = this.uploadService.handleSaveUploadFile(file, "avatar");
                CurrentUser.setAvatar(avatarFileName);
            }

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

    // start create
    @GetMapping(value = "/admin/user/create")
    public ModelAndView createUserForm() {
        ModelAndView modelAndView = new ModelAndView("admin/user/create");
        modelAndView.addObject("newOneUser", new User());
        return modelAndView;
    }

    // bindingResult giúp thông báo lỗi
    // @Valid để validate dữ liệu
    @PostMapping("/admin/user/create")
    // hình như là không cần ModelAttribute ở đây, nhưng cần sử dụng khi validate dữ
    // liệu
    public ModelAndView createUser(
            @ModelAttribute("newOneUser") @Valid User newUser,
            BindingResult bindingResult,
            @RequestParam("hoidanitFile") MultipartFile file) {
        // Kiểm tra lỗi validate
        List<FieldError> errors = bindingResult.getFieldErrors();
        for (FieldError error : errors) {
            System.out.println(error.getField() + " - " + error.getDefaultMessage());
        }
        if (bindingResult.hasErrors()) {
            return new ModelAndView("/admin/user/create");
        }

        // Gọi UploadService để lưu ảnh vào thư mục "avatar" và nhận đường dẫn file
        String avatarFileName = this.uploadService.handleSaveUploadFile(file, "avatar");
        // mã hóa password
        String hashPashWord = this.passwordEncoder.encode(newUser.getPassword());

        // Gán tên file hoặc đường dẫn file vào thuộc tính avatar của User
        newUser.setAvatar(avatarFileName);
        newUser.setPassword(hashPashWord);
        // Lưu đối tượng User vào cơ sở dữ liệu
        this.userService.handleSaveUser(newUser);

        return new ModelAndView("redirect:/admin/user");
    }
    // end start

}
