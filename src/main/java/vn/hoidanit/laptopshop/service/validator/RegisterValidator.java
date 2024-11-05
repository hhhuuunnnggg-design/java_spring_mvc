package vn.hoidanit.laptopshop.service.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import vn.hoidanit.laptopshop.entity.dto.RegisterDTO;
import vn.hoidanit.laptopshop.service.UserService;

@Service // tạo tài khonagr
public class RegisterValidator implements ConstraintValidator<RegisterChecked, RegisterDTO> {
    // Inject `UserService` để kiểm tra các yêu cầu xác thực liên quan đến người
    // dùng, chẳng hạn như kiểm tra email
    @Autowired
    private UserService userService;

    // Phương thức `isValid` thực hiện logic xác thực khi `@RegisterChecked` được áp
    // dụng
    @Override
    public boolean isValid(RegisterDTO user, ConstraintValidatorContext context) {
        boolean valid = true; // Đặt biến `valid` là `true` mặc định để kiểm tra tính hợp lệ

        // Kiểm tra xem các trường mật khẩu có khớp nhau hay không
        if (!user.getPassword().equals(user.getConfirmPassword())) {
            // Nếu mật khẩu không khớp, xây dựng thông báo lỗi cho trường `confirmPassword`
            context.buildConstraintViolationWithTemplate("Passwords Nhập không chính xác")
                    .addPropertyNode("confirmPassword") // Xác định trường gây lỗi
                    .addConstraintViolation() // Thêm lỗi vào ngữ cảnh xác thực
                    .disableDefaultConstraintViolation(); // Vô hiệu hóa thông báo mặc định
            valid = false; // Đánh dấu là không hợp lệ
        }

        // Kiểm tra nếu email đã tồn tại trong hệ thống
        if (this.userService.checkEmailExist(user.getEmail())) {
            // Nếu email đã tồn tại, xây dựng thông báo lỗi cho trường `email`
            context.buildConstraintViolationWithTemplate("Email của bạn đã tồn tại")
                    .addPropertyNode("email") // Xác định trường gây lỗi
                    .addConstraintViolation() // Thêm lỗi vào ngữ cảnh xác thực
                    .disableDefaultConstraintViolation(); // Vô hiệu hóa thông báo mặc định
            valid = false; // Đánh dấu là không hợp lệ
        }

        return valid; // Trả về kết quả xác thực
    }
}
