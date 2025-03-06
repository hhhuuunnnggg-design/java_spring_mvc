package vn.hoidanit.laptopshop.entity;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "cart_detail")
public class CartDetail implements Serializable { // Thêm Serializable
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long quantity;

    private double price;

    // product_id: long
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    // cart_id: long
    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

}
