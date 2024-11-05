package vn.hoidanit.laptopshop.service.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

// Xác định đây là một annotation để xác thực dữ liệu
@Constraint(validatedBy = StrongPasswordValidator.class)

// Phạm vi sử dụng của annotation (có thể áp dụng cho các phương thức và trường)
@Target({ ElementType.METHOD, ElementType.FIELD })

// Quy định rằng annotation này sẽ tồn tại ở thời điểm runtime, cho phép truy
// cập bằng Reflection
@Retention(RetentionPolicy.RUNTIME)

// Đảm bảo rằng annotation này sẽ có mặt trong tài liệu API
@Documented
public @interface StrongPassword {
    // Thông báo mặc định khi mật khẩu không đạt yêu cầu
    String message() default "Must be 8 characters long and combination of uppercase letters, lowercase letters, numbers, special characters.";

    // `groups` để xác định các nhóm xác thực khác nhau (tuỳ chọn)
    Class<?>[] groups() default {};

    // `payload` để đính kèm dữ liệu tùy chỉnh cho các mục đích cụ thể (tuỳ chọn)
    Class<? extends Payload>[] payload() default {};
}
