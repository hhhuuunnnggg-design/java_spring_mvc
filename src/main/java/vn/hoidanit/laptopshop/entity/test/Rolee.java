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
@Getter
@Setter
@Table(name = "rolee")
public class Rolee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String role_name;
    private String description;

    @OneToMany(mappedBy = "rolee")
    List<Auth> auth;

    @OneToMany(mappedBy = "rolee")
    List<Role_User> role_Users;
}
