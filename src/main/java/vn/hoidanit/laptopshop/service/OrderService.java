package vn.hoidanit.laptopshop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import vn.hoidanit.laptopshop.entity.Order;
import vn.hoidanit.laptopshop.repository.OrderRepository;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    public Page<Order> gethandleAllOrder(Pageable pageable) {
        return this.orderRepository.findAll(pageable);

    }

}
