package vn.hoidanit.laptopshop.entity.test;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "menu")
public class Menu implements Serializable { // Thêm Serializable
    private static final long serialVersionUID = 1L; // Thêm UID cho Serializable
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
