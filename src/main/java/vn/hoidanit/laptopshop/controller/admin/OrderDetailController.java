// package vn.hoidanit.laptopshop.controller.admin;

// import java.util.List;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Controller;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.servlet.ModelAndView;

// import vn.hoidanit.laptopshop.entity.OrderDetail;
// import vn.hoidanit.laptopshop.service.OrderDetailService;

// @Controller
// public class OrderDetailController {
// @Autowired
// private OrderDetailService orderDetailService;

// @GetMapping("/admin/order/view/{id}")
// public ModelAndView getViewOrderDetail(@PathVariable Long id) {
// ModelAndView modelAndView = new ModelAndView("admin/order/view-orderDetail");
// List<OrderDetail> orderDetail =
// this.orderDetailService.gethandelAllDetail(id);
// modelAndView.addObject("viewOrderDetail", orderDetail);
// return null;
// }

// }
