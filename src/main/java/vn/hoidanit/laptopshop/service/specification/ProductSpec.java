package vn.hoidanit.laptopshop.service.specification;

import org.springframework.data.jpa.domain.Specification;

import vn.hoidanit.laptopshop.entity.Product;
import vn.hoidanit.laptopshop.entity.Product_;

public class ProductSpec {
    public static Specification<Product> namelike(String name) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get(Product_.NAME), "%" + name + "%");
    }
}
