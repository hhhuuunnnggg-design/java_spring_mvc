package vn.hoidanit.laptopshop.config;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.hoidanit.laptopshop.entity.User;
import vn.hoidanit.laptopshop.service.UserService;

public class CustomSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    private UserService userService;

    // Xác định URL đích để chuyển hướng sau khi đăng nhập thành công dựa trên quyền
    // của người dùng
    protected String determineTargetUrl(final Authentication authentication) {
        // Bản đồ lưu URL tương ứng với vai trò (ROLE)
        Map<String, String> roleTargetUrlMap = new HashMap<>();
        roleTargetUrlMap.put("ROLE_USER", "/"); // Người dùng thông thường sẽ được chuyển hướng về trang chủ "/"
        roleTargetUrlMap.put("ROLE_ADMIN", "/admin"); // Quản trị viên sẽ được chuyển hướng về trang "/admin"

        // Lấy danh sách quyền của người dùng từ đối tượng Authentication
        final Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        // Duyệt qua từng quyền của người dùng để tìm URL đích phù hợp
        for (final GrantedAuthority grantedAuthority : authorities) {
            String authorityName = grantedAuthority.getAuthority();
            if (roleTargetUrlMap.containsKey(authorityName)) {
                return roleTargetUrlMap.get(authorityName); // Trả về URL tương ứng với quyền
            }
        }
        throw new IllegalStateException(); // Ném ngoại lệ nếu không tìm thấy quyền phù hợp
    }

    // Xóa các thuộc tính xác thực và thêm thông tin người dùng vào session
    protected void clearAuthenticationAttributes(HttpServletRequest request, Authentication authentication) {
        // Lấy session hiện tại (không tạo mới nếu chưa có session)
        HttpSession session = request.getSession(false);
        if (session == null) {
            return; // Không có session, thoát khỏi hàm
        }
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION); // Xóa lỗi xác thực nếu có

        // Lấy email từ đối tượng Authentication
        String email = authentication.getName();
        // Truy xuất người dùng từ database bằng email
        User user = this.userService.getUserByEmail(email);
        if (user != null) {
            // Lưu thông tin người dùng vào session để hiển thị sau khi đăng nhập
            session.setAttribute("fullName", user.getFullname());
            session.setAttribute("avatar", user.getAvatar());
            session.setAttribute("id", user.getId());
            session.setAttribute("email", user.getEmail());
        }
    }

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy(); // Chiến lược chuyển hướng mặc định

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        // Xác định URL đích sau khi đăng nhập thành công dựa trên quyền
        String targetUrl = determineTargetUrl(authentication);

        // Nếu response đã cam kết (đã chuyển hướng hoặc trả về), không thực hiện gì
        // thêm
        if (response.isCommitted()) {
            return;
        }

        // Chuyển hướng đến URL đích sau khi đăng nhập thành công
        redirectStrategy.sendRedirect(request, response, targetUrl);

        // Dọn dẹp các thuộc tính xác thực và lưu thông tin người dùng vào session
        clearAuthenticationAttributes(request, authentication);
    }
}
