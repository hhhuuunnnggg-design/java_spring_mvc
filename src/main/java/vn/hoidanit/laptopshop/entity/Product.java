package vn.hoidanit.laptopshop.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Entity
@Table(name = "products")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     Long id;

    @NotNull
    @Size(min = 5, message = "Tên sản phẩm phải trên 5 ký tự")
     String name;

    @NotNull(message = "Giá sản phẩm không được để trống")
    @DecimalMin(value = "1", message = "Giá sản phẩm phải lớn hơn hoặc bằng 1")
     Double price;

     String image;

    @Column(columnDefinition = "MEDIUMTEXT")
    @NotNull
    @Size(min = 5, message = "mô tả phải trên 5 ký tự")
     String detaildesc;

    @NotNull
    @Size(min = 5, message = "mô tả ngắn phải trên 5 ký tự")
     String shortdesc;

    @NotNull(message = "Số lượng sản phẩm không được để trống")
    @DecimalMin(value = "1", message = "Số lượng sản phẩm phải lớn hơn hoặc bằng 1")
    Long quantity;
    Long sold;
    String factory;
    String target;


//    @Override
//    public String toString() {
//        return "Product [id=" + id + ", name=" + name + ", price=" + price + ", image=" + image + ", detaildesc="
//                + detaildesc + ", shortdesc=" + shortdesc + ", quantity=" + quantity + ", sold=" + sold + ", factory="
//                + factory + ", target=" + target + "]";
//    }

}
