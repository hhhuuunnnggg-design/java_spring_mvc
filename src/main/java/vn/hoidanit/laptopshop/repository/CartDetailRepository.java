package vn.hoidanit.laptopshop.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.hoidanit.laptopshop.entity.Cart;
import vn.hoidanit.laptopshop.entity.CartDetail;
import vn.hoidanit.laptopshop.entity.User;

@Repository
public interface CartDetailRepository extends JpaRepository<CartDetail, Long> {

}
