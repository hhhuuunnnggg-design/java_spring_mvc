package vn.hoidanit.laptopshop.repository;

import java.util.List;

import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.hoidanit.laptopshop.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser(User user);

    List<Order> findAll();

    // detail order
    // List<OrderDetail> findByOrderId(Long orderId);

}