package vn.hoidanit.laptopshop.repository;

import java.util.List;

import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;

import vn.hoidanit.laptopshop.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser(User user);
}