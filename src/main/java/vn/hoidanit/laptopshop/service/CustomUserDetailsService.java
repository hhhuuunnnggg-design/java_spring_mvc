package vn.hoidanit.laptopshop.service;

import java.util.Collections;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    // Khai báo một đối tượng `userService` để lấy thông tin người dùng từ cơ sở dữ
    // liệu
    private final UserService userService;

    // Khởi tạo `CustomUserDetailsService` với `UserService`
    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    // Phương thức `loadUserByUsername` dùng để tải thông tin người dùng dựa trên
    // tên người dùng (username/email)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Lấy đối tượng `User` từ cơ sở dữ liệu thông qua email (username)
        vn.hoidanit.laptopshop.entity.User user = this.userService.getUserByEmail(username);

        // Nếu không tìm thấy người dùng, ném ra ngoại lệ `UsernameNotFoundException`
        if (user == null) {
            throw new UsernameNotFoundException("user not found");
        }

        // Trả về đối tượng `UserDetails` để Spring Security dùng xác thực
        return new User(
                user.getEmail(), // email sẽ là username được sử dụng để đăng nhập
                user.getPassword(), // mật khẩu đã mã hóa của người dùng
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole().getName()))); // cấp quyền
                                                                                                            // theo role
                                                                                                            // của người
                                                                                                            // dùng
    }

}
