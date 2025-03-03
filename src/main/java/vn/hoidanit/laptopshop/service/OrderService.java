package vn.hoidanit.laptopshop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import vn.hoidanit.laptopshop.entity.Order;
import vn.hoidanit.laptopshop.repository.OrderRepository;

import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    public Page<Order> gethandleAllOrder(Pageable pageable) {
        return this.orderRepository.findAll(pageable);

    }
    public void deleteOrderById(Long id) {
        orderRepository.deleteById(id);
    }
    public Optional<Order> fetchOrderById(long id) {
        return this.orderRepository.findById(id);
    }
    public void updateOrder(Order order) {
        Optional<Order> orderOptional = this.fetchOrderById(order.getId());
        if (orderOptional.isPresent()) {
            Order currentOrder = orderOptional.get();
            currentOrder.setStatus(order.getStatus());
            this.orderRepository.save(currentOrder);
        }
    }
}
