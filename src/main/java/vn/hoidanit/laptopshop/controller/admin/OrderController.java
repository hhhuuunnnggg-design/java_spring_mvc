package vn.hoidanit.laptopshop.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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

    @PutMapping("/admin/order/update/{id}")
    public ModelAndView getViewOrderDetailư(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("admin/order/table-order");
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
}
