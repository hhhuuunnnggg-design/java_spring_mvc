package vn.hoidanit.laptopshop.entity.test;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
public class Rolee implements Serializable { // Thêm Serializable
    private static final long serialVersionUID = 1L; // Thêm UID cho Serializable
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String role_name;
    private String description;

    @OneToMany(mappedBy = "rolee", fetch = FetchType.EAGER)
    List<Auth> auth;

    @OneToMany(mappedBy = "rolee", fetch = FetchType.EAGER)
    List<Role_User> role_Users;
}
