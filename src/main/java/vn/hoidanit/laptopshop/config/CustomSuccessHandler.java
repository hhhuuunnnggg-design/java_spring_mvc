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
import vn.hoidanit.laptopshop.entity.Cart;
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
        HttpSession session = request.getSession(false);
        if (session == null) {
            return;
        }
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);

        // Lấy email từ đối tượng Authentication
        String email = authentication.getName();
        // Truy xuất người dùng từ cơ sở dữ liệu bằng email
        User user = this.userService.getUserByEmail(email);

        if (user != null) {
            // Kiểm tra nếu người dùng có giỏ hàng hay không
            Cart cart = user.getCart();
            if (cart != null) {
                // Nếu giỏ hàng tồn tại, lưu số lượng giỏ hàng vào session
                session.setAttribute("cartSum", cart.getSum());
                session.setAttribute("sum", cart.getSum());
            } else {
                // Nếu giỏ hàng không tồn tại, đặt giá trị mặc định cho "cartSum" và "sum"
                session.setAttribute("cartSum", 0);
                session.setAttribute("sum", 0);
            }

            // Lưu thông tin khác của người dùng vào session
            session.setAttribute("fullName", user.getFullname());
            session.setAttribute("avatar", user.getAvatar());
            session.setAttribute("id", user.getId());
            session.setAttribute("email", user.getEmail());

            int sum = user.getCart().getSum();
            session.setAttribute("sum", sum);
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
