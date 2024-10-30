package vn.hoidanit.laptopshop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.hoidanit.laptopshop.entity.Product;
import vn.hoidanit.laptopshop.repository.ProductRepository;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<Product> gethandleAllProducts() {
        return this.productRepository.findAll();
    }
}
