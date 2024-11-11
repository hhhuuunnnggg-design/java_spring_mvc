package vn.hoidanit.laptopshop.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import vn.hoidanit.laptopshop.entity.Order;
import vn.hoidanit.laptopshop.service.OrderService;

@Controller
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/admin/order")
    public ModelAndView getOrderPage() {
        ModelAndView modelAndView = new ModelAndView("admin/order/table-order"); // vewname là đường link dẫ đến jsp
        List<Order> orders = this.orderService.gethandleAllOrder();
        modelAndView.addObject("tableListOrder", orders);
        return modelAndView;
    }

}
