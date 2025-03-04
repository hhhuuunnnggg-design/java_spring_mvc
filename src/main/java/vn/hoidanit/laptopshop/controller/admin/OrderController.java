package vn.hoidanit.laptopshop.controller.admin;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.ui.Model;

import vn.hoidanit.laptopshop.entity.Order;
import vn.hoidanit.laptopshop.entity.OrderDetail;
import vn.hoidanit.laptopshop.entity.Product;
import vn.hoidanit.laptopshop.service.OrderDetailService;
import vn.hoidanit.laptopshop.service.OrderService;

@Controller
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderDetailService orderDetailService;

    // lấy gia tri
    @GetMapping("/admin/order")
    public ModelAndView getOrderPage(@RequestParam(value = "page", defaultValue = "1") int page) {
        ModelAndView modelAndView = new ModelAndView("admin/order/table-order");
        // chức năng phân trang
        Pageable pageable = PageRequest.of(page - 1, 2);
        Page<Order> order = this.orderService.gethandleAllOrder(pageable);
        List<Order> orders = order.getContent();
        // trang hiện tại
        modelAndView.addObject("currentPage", page);
        // tổng số trang
        modelAndView.addObject("totalPages", order.getTotalPages());

        modelAndView.addObject("tableListOrder", orders);
        return modelAndView;
    }

    //xem chi tiet
    @GetMapping("/admin/order/view/{id}")
    public ModelAndView getViewOrderDetail(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("admin/order/view-orderDetail");
        List<OrderDetail> orderDetails = this.orderDetailService.gethandelAllDetail(id);

        if (orderDetails != null && !orderDetails.isEmpty()) {
            for (OrderDetail orderDetail : orderDetails) {
                Product product = orderDetail.getProduct();
                // Xử lý thêm với product nếu cần
                System.out.println("Product ID: " + product.getId());
                System.out.println("Product Name: " + product.getName());
                System.out.println("đây là ảnh: " + product.getImage());
            }
        }

        modelAndView.addObject("viewOrderDetail", orderDetails);
        return modelAndView;
    }

    //lan 1
    @GetMapping("/admin/order/update/{id}")
    public ModelAndView getUpdateOrderPage(@PathVariable long id) {
        ModelAndView getModelAndView = new ModelAndView("admin/order/update");
        Optional<Order> currentOrder = this.orderService.fetchOrderById(id);

        if (currentOrder.isPresent()) {
            getModelAndView.addObject("newOrder", currentOrder.get()); // ✅ Truyền đối tượng Order, không phải Optional
        } else {
            getModelAndView.addObject("newOrder", new Order()); // ✅ Tránh lỗi nếu order không tồn tại
        }
        return getModelAndView;
    }


    // update lan 2 (done)
    @PostMapping("/admin/order/update")
    public String handleUpdateOrder(@ModelAttribute("newOrder") Order order) {
        this.orderService.updateOrder(order);
        return "redirect:/admin/order";
    }

    @DeleteMapping("/admin/order/delete/{id}")
    public ResponseEntity<String> deleteProductId(@PathVariable Long id) {
        System.out.println("Deleting product with ID: " + id); // Thêm log để kiểm tra

        if (orderService == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }
        orderService.deleteOrderById(id);
        return ResponseEntity.ok("Product với ID " + id + " đã bị xóa");
    }

}
