package vn.hoidanit.laptopshop.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import vn.hoidanit.laptopshop.service.OrderDetailService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class OrderDetailController {
    @Autowired
    private OrderDetailService orderDetailService;

    @GetMapping("/admin/order/view/{id}")
    public ModelAndView getViewOrderDetail(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("admin/order/view-orderDetail");
        modelAndView.addObject(null, modelAndView);
        return null;
    }

}
