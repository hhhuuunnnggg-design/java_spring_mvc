package vn.hoidanit.laptopshop.entity.test;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.Transient;

@Entity
@Setter
@Getter
@Table(name = "menu")
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long parent_id;
    private String url;
    private String name;
    private Long order_index;
    private Long active_flag;

    // không mapping vao csdl
    @Transient
    private String IdMenu;

    @Transient
    private List<Menu> child;

    @OneToMany(mappedBy = "menu")
    List<Auth> auth;
}
