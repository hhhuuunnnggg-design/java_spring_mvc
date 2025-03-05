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

@Entity
@Setter
@Getter
@Table(name = "menu")
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long parent_id;
    private Long url;
    private String name;
    private Long order_index;
    private Long active_flag;

    @OneToMany(mappedBy = "menu")
    List<Auth> auth;
}
