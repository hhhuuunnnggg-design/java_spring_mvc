package vn.hoidanit.laptopshop.service.specification;

import org.springframework.data.jpa.domain.Specification;

import vn.hoidanit.laptopshop.entity.Product;
import vn.hoidanit.laptopshop.entity.Product_;

import java.util.List;

public class ProductSpec {
    // case 0
    public static Specification<Product> namelike(String name) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get(Product_.NAME), "%" + name + "%");
    }

    // case 1
    public static Specification<Product> minPrice(Double price) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.ge(root.get(Product_.PRICE), price);
    }

    // case2:
    public static Specification<Product> maxPrice(Double price) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.le(root.get(Product_.PRICE), price);
    }

    //case 3
    public static Specification<Product> factoryLike(String  factory) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get(Product_.FACTORY), "%" + factory + "%"));

    }

    //case 4
    public static Specification<Product> mathListFactory(List<String> factory) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.in(root.get(Product_.FACTORY)).value(factory));
    }

    //case 5
    public static Specification<Product> matchPrice(double min, double max) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.and(
                criteriaBuilder.gt(root.get(Product_.PRICE),min),
                criteriaBuilder.lt(root.get(Product_.PRICE),max)
        ));
    }

    //case 6
    public static Specification<Product> matchMultiplePrice(double min, double max) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.between(root.get(Product_.PRICE), min, max));
    }


}
