package vn.hoidanit.laptopshop.service.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy = RegisterValidator.class)
// TYPE: phạm vi hoạt động là cả class
@Target({ ElementType.TYPE }) // Chỉ định annotation này được áp dụng ở cấp độ lớp (class)
@Retention(RetentionPolicy.RUNTIME) // Annotation sẽ tồn tại trong runtime và có thể truy cập bằng Reflection
@Documented // Đánh dấu để annotation này sẽ có mặt trong tài liệu API
public @interface RegisterChecked {

    // Thông báo lỗi mặc định khi xác thực không thành công
    String message() default "User register validation failed";

    // Thuộc tính `groups` để xác định nhóm xác thực (không bắt buộc)
    Class<?>[] groups() default {};

    // Thuộc tính `payload` để đính kèm thông tin bổ sung cho annotation (không bắt
    // buộc)
    Class<? extends Payload>[] payload() default {};
}
