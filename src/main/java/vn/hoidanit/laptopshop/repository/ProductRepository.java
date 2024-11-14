package vn.hoidanit.laptopshop.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import vn.hoidanit.laptopshop.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    List<Product> findAll();

    Optional<Product> deleteById(long id);

    Optional<Product> findById(long id);

    Product save(Product newproduct);

    Page<Product> findAll(Pageable page);

    // fillter dữ liệu lên pagram
    Page<Product> findAll(Specification<Product> spec, Pageable page);
}
