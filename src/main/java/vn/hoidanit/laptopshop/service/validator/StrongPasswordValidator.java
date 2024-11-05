package vn.hoidanit.laptopshop.service.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class StrongPasswordValidator implements ConstraintValidator<StrongPassword, String> {

    @Override // `value` là mật khẩu đầu vào mà chúng ta cần kiểm tra
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // Kiểm tra mật khẩu có đáp ứng các yêu cầu sau:
        // - Ít nhất 1 chữ số (\\d)
        // - Ít nhất 1 chữ thường ([a-z])
        // - Ít nhất 1 chữ hoa ([A-Z])
        // - Ít nhất 1 ký tự đặc biệt trong số các ký tự: @#$%^&+=!*()
        // - Độ dài tối thiểu 8 ký tự
        return value.matches("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!*()]).{8,}$");
    }
}
