## 🛒 Laptopshop 2025 — Spring Boot MVC (JSP)

Ứng dụng bán laptop với đầy đủ luồng người dùng: duyệt sản phẩm, giỏ hàng, đặt hàng, thanh toán VNPay, lịch sử đơn, phân quyền, và đăng nhập OAuth2 (Google/GitHub).

---

## ✨ Tính năng nổi bật

- 🔐 Xác thực & phân quyền: Spring Security, Remember-me, session JDBC, redirect theo vai trò (User/Admin)
- 🔑 Đăng nhập OAuth2: Google (UI link `/oauth2/authorization/google`), GitHub (UI sẵn, có thể cấu hình thêm)
- 🛍️ Sản phẩm: phân trang, sắp xếp theo giá, chi tiết sản phẩm (`/product`, `/product/{id}`)
- 🛒 Giỏ hàng: thêm/xóa/cập nhật, giữ trạng thái theo session
- 🧾 Đặt hàng & thanh toán: tạo đơn, chọn phương thức COD/VNPay, sinh `paymentRef` theo `UUID`
- 💳 VNPay sandbox: build URL có `hmacSHA512`, redirect sang gateway, xử lý callback tại `/thanks` để cập nhật `paymentStatus`
- 📜 Lịch sử đơn hàng: `/order-history` (theo user)
- 👨‍💼 Khu vực Admin: điều hướng sau đăng nhập tới `/admin` đối với `ROLE_ADMIN`

---

## 🛠️ Công nghệ sử dụng

- ☕ Java 17, Spring Boot
- 🧰 Spring MVC (JSP), Spring Security, OAuth2 Client
- 🗃️ Spring Data JPA (MySQL), Hibernate Validator
- 🗂️ Spring Session JDBC (lưu session DB), Remember-me Services

---

## 🔌 Cấu hình & biến môi trường

File `src/main/resources/application.properties`:

- CSDL MySQL (mặc định):
  - `spring.datasource.url=jdbc:mysql://localhost:3306/laptopshop`
  - `spring.datasource.username=root`
  - `spring.jpa.hibernate.ddl-auto=update`
- Session JDBC:
  - `spring.session.store-type=jdbc`
  - `spring.session.jdbc.initialize-schema=always`
- VNPay sandbox:
  - `hoidanit.vnpay.tmn-code=...`
  - `hoidanit.vnpay.hash-secret=...`
  - `hoidanit.vnpay.vnp-url=https://sandbox.vnpayment.vn/paymentv2/vpcpay.html`
  - `hoidanit.vnpay.vnp-return-url=http://localhost:8080/thanks`
- OAuth2 Google:
  - `spring.security.oauth2.client.registration.google.client-id=...`
  - `spring.security.oauth2.client.registration.google.client-secret=...`
  - `spring.security.oauth2.client.registration.google.scope=email,profile`

---

## 🚀 Hướng dẫn chạy (Windows)

```bash
mvnw.cmd clean install
mvnw.cmd spring-boot:run
```

Hoặc chạy trực tiếp class `vn.hoidanit.laptopshop.LaptopshopApplication` từ IDE.

Mặc định ứng dụng chạy cổng `8080`.

---

## 🔐 Bảo mật & OAuth2

- Cấu hình tại `vn.hoidanit.laptopshop.config.SecurityConfiguration`:
  - Cho phép truy cập công khai các tài nguyên tĩnh và trang công cộng
  - Bắt buộc xác thực cho các route còn lại
  - `formLogin` và `oauth2Login` dùng chung `customSuccessHandler()` để điều hướng theo vai trò
  - `SpringSessionRememberMeServices` bật chế độ always-remember
- `CustomUserDetailsService`: load user theo email, map quyền `ROLE_{role}` từ DB
- `CustomOAuth2UserService`: xử lý thông tin từ provider (Google), có thể map/đồng bộ user nội bộ
- UI login thêm liên kết SSO: `/oauth2/authorization/google` và `/oauth2/authorization/github`

---

## 💳 Luồng thanh toán VNPay (sandbox)

1. Người dùng đặt hàng tại `/checkout` và submit `/place-order`
2. Nếu chọn VNPay: sinh `uuid` làm `paymentRef`, build URL VNPay bằng `VNPayService.generateVNPayURL(...)` và redirect
3. VNPay gọi ngược về `/thanks` (theo `vnp-return-url`)
4. Ở `/thanks`, đọc `vnp_ResponseCode` và `vnp_TxnRef` để cập nhật `paymentStatus` qua `productService.updatePaymentStatus`

---

## 📦 Module & endpoint tiêu biểu

- Client controllers: `HomePageController`, `ItemController`, API giỏ hàng (`CartAPI`)
- Admin: điều hướng mặc định sau đăng nhập nếu có `ROLE_ADMIN`
- Endpoint chính:
  - `/` Trang chủ, `/product`, `/product/{id}`
  - `/cart`, `/checkout`, `/place-order`, `/thanks`, `/order-history`
  - `/login`, `/register`, `/access-deny`
  - `/oauth2/authorization/google`, `/oauth2/authorization/github`

---

## 🧾 Mô hình dữ liệu (rút gọn)

- `User(id, email, password, fullname, role, provider, ...)`
- `Role(id, name, description)` → 1:N `User`
- `Product(id, name, price, image, detaildesc, shortdesc, quantity, sold, ...)`
- `Cart(id, user)` ↔ `CartDetail(id, cart, product, quantity)`
- `Order(id, user, totalPrice, receiver..., status, paymentRef, paymentStatus, paymentMethod)` ↔ `OrderDetail`

---

## 🧭 Những gì tôi đã làm trong `LaptopshopApplication.java`

- Khởi tạo entry point Spring Boot bằng `@SpringBootApplication` + `main()`
- Duy trì phương án loại trừ `SecurityAutoConfiguration` (comment sẵn) để linh hoạt trong debug/demo bảo mật
- Đặt package gốc `vn.hoidanit.laptopshop` giúp component scan toàn bộ layer MVC/Service/Repository

Trích đoạn:

```java
@SpringBootApplication
// @SpringBootApplication(exclude =
// org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class)
public class LaptopshopApplication {
    public static void main(String[] args) {
        SpringApplication.run(LaptopshopApplication.class, args);
    }
}
```

---
